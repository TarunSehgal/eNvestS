package com.eNvestDetails.Products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.CalculationService.AnnuityCalculator;
import com.eNvestDetails.CalculationService.GoalSeekCalculator;
import com.eNvestDetails.CalculationService.InterestCalculator;
import com.eNvestDetails.CalculationService.Response;

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
	
	public Product getProduct(int productId, double maturityDate, double notional, String compoundingTenor) throws Exception
	{
		Product prd = getProduct(productId);
		if(prd != null)
		{
			switch(prd.productType)
			{
				case CertificateOfDeposit:
				return getCD((CDProduct) prd,maturityDate,notional,compoundingTenor);
				case HighYieldAccount:
					return getHYA((HighYieldProduct) prd,maturityDate,notional,compoundingTenor);
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
		for(int i=1; i<=maturityYears; i++)
		{
			Response response = interestCalculator.CalculateInterest(principle, compoundingTenor, product.interestRate, i);
			product.maturityLadder.add(new TenorMaturityResponse(i,response.maturity, response.interest, response.principle));
			principle = response.maturity;
		}
		return product;
	}
	
	public List<Product> GetAvailableProducts()
	{
		List<Product> products = new ArrayList<Product>();
		products.add(new CDProduct("bankA", 1.5, 1001));
		products.add(new CDProduct("bankB", 1.3, 1002));
		
		products.add(new HighYieldProduct("bankA", 1.8, 2001));
		products.add(new HighYieldProduct("bankB", 1.6, 2002));
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
