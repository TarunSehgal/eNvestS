package com.eNvestDetails.util.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.dao.BankDao;
import com.eNvestDetails.dao.ProductDao;
import com.eNvestDetails.dao.UserProductDao;
import com.eNvestDetails.dto.BankDTO;
import com.eNvestDetails.dto.ProductDTO;
import com.eNvestDetails.dto.UserProductDTO;
import com.eNvestDetails.util.Calculation.AnnuityCalculator;
import com.eNvestDetails.util.Calculation.GoalSeekCalculator;
import com.eNvestDetails.util.Calculation.InterestCalculator;
import com.eNvestDetails.util.Calculation.PayoutResponse;
import com.eNvestDetails.util.Calculation.Response;

@Component
public class ProductUtil {

	private List<Product> availableProducts = null;
	private UserProductDao userProductDAO = new UserProductDao();
	private BankDao bankDAO = new BankDao();
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
	
	public SaveProductReponse SaveProduct(String userId, int productId, double noOfYears, double principle, String compoundingTenor)
	{
		SaveProductReponse response = new SaveProductReponse(userId, productId, StatusCode.Success);
		return response;
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
		product.noOfYears = response.noOfYears;
		
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
		product.noOfYears = response.noOfYears;		
		return product;
	}
	
	public int SaveUserProduct(Product product, String userId) throws EnvestException
	{
		UserProductDTO userProductDTO = ProductToDTOConverter.convertProductToDTO(product, userId);
		return UserProductDao.addNewProduct(userProductDTO, message);
	}
	
	public Product GetUserProduct(int productId) throws EnvestException
	{
		UserProductDTO userProductDTO =UserProductDao.getProduct(productId); 
		ProductDTO productDTO = ProductDao.getProducts(userProductDTO.getProductId());
		BankDTO bankDTO = bankDAO.getBankInfo(productDTO.getBankId());
		return UserProductDTOtoProductConverter.getProductFromDTO(userProductDTO, bankDTO, productDTO);
	}
	
	public List<Product> GetAvailableProducts() throws EnvestException
	{
		List<ProductDTO> productDTO = ProductDao.getAllProducts();
		
		List<Product> products = new ArrayList<Product>();
		for(ProductDTO prdDto:productDTO)
		{
			BankDTO bankDTO = bankDAO.getBankInfo(prdDto.getBankId());
			products.add(ProductDTOtoProductConverter.getProductFromDTO(prdDto, bankDTO));
		}
		
		return products;
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
