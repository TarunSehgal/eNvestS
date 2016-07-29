package com.eNvestDetails.Recommendation;

import com.eNvestDetails.RecommendationEngine.AbstractProductRule;
import com.eNvestDetails.util.Product.GoalSeekProduct;
import com.eNvestDetails.util.Product.ProductType;

public abstract class GoalSeekRule extends AbstractProductRule {

	protected boolean makeDecision(Object arg) throws Exception
	{
		return Boolean.parseBoolean(getRuleEnable());
	}
		
	protected void doWork(Object arg) throws Exception
	{
		GoalSeekProduct HighYieldProduct = (GoalSeekProduct) getHighestRateProduct();		
	}

	@Override
	protected ProductType getProductType() {
		return ProductType.GoalSeek;
	}

}
