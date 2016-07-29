package com.eNvestDetails.Recommendation;

import com.eNvestDetails.RecommendationEngine.AbstractProductRule;
import com.eNvestDetails.util.Product.HighYieldProduct;
import com.eNvestDetails.util.Product.ProductType;

public abstract class HYARule extends AbstractProductRule {

	protected boolean makeDecision(Object arg) throws Exception
	{
		return Boolean.parseBoolean(getRuleEnable());
	}
	
	protected void doWork(Object arg) throws Exception
	{
		HighYieldProduct highestRateProduct = (HighYieldProduct) getHighestRateProduct();		
	}

	@Override
	protected ProductType getProductType() {
		return ProductType.HighYieldAccount;
	}

}
