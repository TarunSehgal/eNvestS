package com.eNvestDetails.Recommendation;

import com.eNvestDetails.RecommendationEngine.AbstractProductRule;
import com.eNvestDetails.util.Product.MIProduct;
import com.eNvestDetails.util.Product.ProductType;

public abstract class MipRule extends AbstractProductRule {

	protected boolean makeDecision(Object arg) throws Exception
	{
		return Boolean.parseBoolean(getRuleEnable());
	}
	
	protected void doWork(Object arg) throws Exception
	{
		MIProduct highestRateProduct = (MIProduct) getHighestRateProduct();		
	}

	@Override
	protected ProductType getProductType() {
		return ProductType.MonthlyInvestmentPlan;
	}

}
