package com.eNvestDetails.util.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.util.Calculation.AnnuityCalculator;
import com.eNvestDetails.util.Calculation.GoalSeekCalculator;
import com.eNvestDetails.util.Calculation.InterestCalculator;
import com.eNvestDetails.util.Calculation.PayoutResponse;
import com.eNvestDetails.util.Calculation.Response;

@Component
public class ProductUtil {

	private List<Product> availableProducts = null;
	@Autowired
	private InterestCalculator interestCalculator = null;
	
	@Autowired
	private AnnuityCalculator annuityCalculator = null;
	
	@Autowired
	private GoalSeekCalculator goalSeekCalculator = null;
	
	public ProductUtil()
	{
		availableProducts = GetAvailableProducts();
	}
	
	public Product getProduct(int productId, double maturityDate, double principle, String compoundingTenor) throws Exception
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
	
	public List<Product> GetAvailableProducts()
	{
		List<Product> products = new ArrayList<Product>();
		products.add(new CDProduct("bankA", 1.5, 1001,5));
		products.add(new CDProduct("bankB", 1.3, 1002,6));
		
		products.add(new HighYieldProduct("bankA", 1.8, 2001));
		products.add(new HighYieldProduct("bankB", 1.6, 2002));
		
		products.add(new MIProduct("bankA", 1.88, 3001));
		products.add(new MIProduct("bankB", 1.63, 3002));
		products.add(new GoalSeekProduct("bankA", 1.85, 4001));
		products.add(new GoalSeekProduct("bankB", 1.69, 4002));
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
