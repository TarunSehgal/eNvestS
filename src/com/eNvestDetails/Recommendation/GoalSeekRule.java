package com.eNvestDetails.Recommendation;

import java.util.HashMap;
import java.util.Map;

import com.eNvestDetails.RecommendationEngine.AbstractProductRule;
import com.eNvestDetails.util.Product.GoalSeekProduct;
import com.eNvestDetails.util.Product.ProductType;

public class GoalSeekRule extends AbstractProductRule {

	protected boolean makeDecision(Object arg) throws Exception
	{
		return Boolean.parseBoolean(getRuleEnable());
	}
		
	@Override
	protected ProductType getProductType() {
		return ProductType.GoalSeek;
	}

	@Override
	protected boolean makeDecision(Map<String, Object> arg) throws Exception {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected Map<String, Object> doWork(Map<String, Object> arg) throws Exception {
		
		GoalSeekProduct HighYieldProduct = (GoalSeekProduct) getHighestRateProduct();
		if(arg == null)
		{
			arg = new HashMap<String, Object>();
		}
		
		arg.put(getProductType().toString(), HighYieldProduct);
		return arg;
	}

}
