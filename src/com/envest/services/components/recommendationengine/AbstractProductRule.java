package com.envest.services.components.recommendationengine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.envest.services.components.util.Product.Product;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.components.util.Product.ProductUtil;

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
