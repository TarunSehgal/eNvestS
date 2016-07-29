package com.eNvestDetails.RecommendationEngine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.util.Product.Product;
import com.eNvestDetails.util.Product.ProductType;
import com.eNvestDetails.util.Product.ProductUtil;

public abstract class AbstractProductRule extends AbstractRule {

	@Autowired
	protected ProductUtil productUtil = null;
	protected abstract ProductType getProductType();
	protected Product getHighestRateProduct()
	{
		List<Product> products = productUtil.GetAvailableProducts(getProductType());
		double interestRate = 0;
		Product selectedProduct=null;
		for(Product prd:products)
		{
			if(prd.interestRate>interestRate)
			{
				selectedProduct = prd;
			}
		}
		return selectedProduct;
	}
}
