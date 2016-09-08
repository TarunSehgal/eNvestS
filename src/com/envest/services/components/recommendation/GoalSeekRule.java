package com.envest.services.components.recommendation;

import java.util.HashMap;
import java.util.Map;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.util.Product.GoalSeekProduct;
import com.envest.services.components.util.Product.ProductType;

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
		
		GoalSeekProduct goalSeekProduct = (GoalSeekProduct) getHighestRateProduct();
		if(arg == null)
		{
			arg = new HashMap<String, Object>();
		}
		goalSeekProduct.principle = 25000;
		arg.put(getProductType().toString(), goalSeekProduct);
		return arg;
	}

}
