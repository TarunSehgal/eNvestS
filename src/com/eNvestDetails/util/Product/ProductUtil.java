package com.eNvestDetails.util.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.BankDTO;
import com.eNvestDetails.DAL.BankDaoService;
import com.eNvestDetails.DAL.ProductDaoService;
import com.eNvestDetails.DAL.ProductDTO;
import com.eNvestDetails.DAL.UserProductDTO;
import com.eNvestDetails.DAL.UserProductDaoService;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.RecommendationEngine.InitiateRecommendation;
import com.eNvestDetails.util.Calculation.AnnuityCalculator;
import com.eNvestDetails.util.Calculation.GoalSeekCalculator;
import com.eNvestDetails.util.Calculation.InterestCalculator;
import com.eNvestDetails.util.Calculation.PayoutResponse;
import com.eNvestDetails.util.Calculation.Response;

@Component
public class ProductUtil {

	private List<Product> availableProducts = null;
		
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	@Autowired
	private BankDaoService bankDaoService;
	
	@Autowired
	private ProductDaoService productDaoService;
	
	@Autowired
	UserProductDaoService userProductDaoService;
	
	@Autowired
	private ErrorMessageFactory errorFactory = null;
	
	@Autowired
	private InterestCalculator interestCalculator = null;
	
	@Autowired
	private AnnuityCalculator annuityCalculator = null;
	
	@Autowired
	private GoalSeekCalculator goalSeekCalculator = null;
	
	@Autowired
	private MessageFactory message = null;
	
	public ProductUtil() throws EnvestException
	{		
	}
	
	@PostConstruct
	public void Initialize() throws EnvestException
	{
		availableProducts = GetAvailableProducts();
	}
	public Product recalculateProduct(int productId, double maturityDate, double principle, String compoundingTenor) throws Exception
	{
		Product prd = getProduct(productId);
		if(prd != null)
		{
			switch(prd.productType)
			{
				case CertificateOfDeposit:
				return getCD((CDProduct) prd,maturityDate,principle,compoundingTenor);
				case HighYieldAccount:
					return getHYA((HighYieldProduct) prd,maturityDate,principle,compoundingTenor);
				case MonthlyInvestmentPlan:
					return getMip((MIProduct) prd,maturityDate,principle);
				case GoalSeek:
					return getGoalSeek((GoalSeekProduct) prd,maturityDate,principle,compoundingTenor);
			default:
				break;
			}
		}		
		return null;
	}
	
	private Product getCD(CDProduct product, double maturityDate, double notional, String compoundingTenor) throws Exception
	{
		Response response = interestCalculator.CalculateInterest(notional, compoundingTenor, product.interestRate, maturityDate);
		product.compoundingTenor = compoundingTenor;
		product.interestEarned = response.interest;
		product.principle = response.principle;		
		product.valueAtMaturity = response.maturity;
		product.maturityYears = response.noOfYears;
		return product;
	}
	
	private Product getHYA(HighYieldProduct product, double maturityYears, double notional, String compoundingTenor) throws Exception
	{
		double principle = notional;
		product.maturityLadder.clear();
		product.principle = notional;
		product.compoundingTenor = compoundingTenor;
		for(int i=1; i<=5; i=i+4)
		{
			Response response = interestCalculator.CalculateInterest(principle, compoundingTenor, product.interestRate, i);
			product.maturityLadder.add(new TenorMaturityResponse(i,response.maturity, response.interest, response.principle));
			principle = response.maturity;
		}
		return product;
	}
	
	private Product getMip(MIProduct product, double noOfYears, double cashFlow) throws Exception
	{
		Response response = annuityCalculator.CalculateDueAnnity(cashFlow, noOfYears, product.interestRate, "1m");
		product.compoundingTenor = "1m";
		product.interestEarned = response.interest;
		product.monthlyCashFlow = cashFlow;		
		product.valueAtMaturity = response.maturity;
		product.maturityYears = response.noOfYears;
		
		product.minimumTenureYears = 1;
		product.maxTenureYears = 10;
		product.emiDefaultCharges = 1;
		product.preMatureClosureCharges = 10;
		
		return product;
	} 
	
	private Product getGoalSeek(GoalSeekProduct product, double noOfYears, double principle, String compoundingTenor) throws Exception
	{
		PayoutResponse response = goalSeekCalculator.getAnnuityDueRequiredCashFlow(principle, noOfYears, product.interestRate, compoundingTenor);
		product.compoundingTenor = compoundingTenor;
		product.cashFlow = response.cashFlow;
		product.principle = response.principle;		
		product.maturityYears = response.noOfYears;		
		return product;
	}
	
	public int SaveUserProduct(int productId, double principle,double valueAtMaturity,double interestRate, Long userKey) throws EnvestException
	{
		UserProductDTO userProductDTO = ProductToDTOConverter.convertProductToDTO(productId,principle,interestRate,valueAtMaturity  , userKey);
		return userProductDaoService.addNewProduct(userProductDTO, message, errorFactory);
	}
	
	public List<Product> GetUserProduct(Long userKey) throws EnvestException
	{
		List<Product> availableUserProducts = new ArrayList<Product>();
		List<UserProductDTO> userProductDTO =userProductDaoService.getAllProduct(userKey, errorFactory); 
		if(userProductDTO != null && userProductDTO.size()>0)
		{
			for(UserProductDTO dto:userProductDTO)
			{
				ProductDTO productDTO = productDaoService.getProducts(dto.getProductId());
				BankDTO bankDTO = bankDaoService.getBankInfo(productDTO.getBankId(), errorFactory);
				availableUserProducts.add(UserProductDTOtoProductConverter.getProductFromDTO(dto, bankDTO, productDTO));
			}
		}

		return availableUserProducts;
	}
	
	public List<Product> GetAvailableProducts() throws EnvestException
	{
		List<ProductDTO> productDTO = productDaoService.getAllProducts();
		
		List<Product> products = new ArrayList<Product>();
		for(ProductDTO prdDto:productDTO)
		{
			BankDTO bankDTO = bankDaoService.getBankInfo(prdDto.getBankId(), errorFactory);
			products.add(ProductDTOtoProductConverter.getProductFromDTO(prdDto, bankDTO));
		}
		
		return products;
	}
	
	public List<Product> GetRecommendedProducts() throws Exception
	{
		
		//InitiateRecommendation recommendationEngine = new InitiateRecommendation();
		Map<String, Object> arg = new HashMap<String, Object>();
		
		Map<String, Object> resultArg = recommendationEngine.processRequest(arg);
		List<Product> recommendedProducts = new ArrayList<Product>();
		if(resultArg != null && resultArg.size()>0)
		{
			for(ProductType val:ProductType.values())
			{
				if(resultArg.containsKey(val.toString()))
				{
					recommendedProducts.add((Product) resultArg.get(val.toString()));
				}
			}
		}
		return recommendedProducts;
	}
	
	public List<Product> GetAvailableProducts(ProductType productType)
	{
		List<Product> products = new ArrayList<Product>();
		for(Product prd:availableProducts)
		{
			if(prd.productType.equals(productType))
			{
				products.add(prd);
			}
		}
		
		return products;
	}
	private Product getProduct(int productId)
	{
		for(int i=0; i< availableProducts.size(); i++)
		{
			if(availableProducts.get(i).productId == productId)
				return availableProducts.get(i);
		}
		return null;
	}
}
