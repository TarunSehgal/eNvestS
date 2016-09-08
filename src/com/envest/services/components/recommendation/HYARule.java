package com.envest.services.components.recommendation;

import java.util.HashMap;
import java.util.Map;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.util.Product.HighYieldProduct;
import com.envest.services.components.util.Product.ProductType;

public class HYARule extends AbstractProductRule {

	@Override
	protected ProductType getProductType() {
		return ProductType.HighYieldAccount;
	}

	@Override
	protected boolean makeDecision(Map<String, Object> arg) throws Exception {
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected Map<String, Object> doWork(Map<String, Object> arg) throws Exception {
		HighYieldProduct highestRateProduct = (HighYieldProduct) getHighestRateProduct();
		if(arg == null)
		{
			arg = new HashMap<String, Object>();
		}
		highestRateProduct.principle = 10000;
		arg.put(getProductType().toString(), highestRateProduct);
		return arg;
	}

}
