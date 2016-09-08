package com.envest.services.components.recommendation;

import java.util.HashMap;
import java.util.Map;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.util.Product.CDProduct;
import com.envest.services.components.util.Product.ProductType;

public class CDRule extends AbstractProductRule {

	@Override
	protected ProductType getProductType() {
		return ProductType.CertificateOfDeposit;
	}

	@Override
	protected Map<String, Object> doWork(Map<String, Object> arg) throws Exception {
		
		CDProduct highestRatecd = (CDProduct) getHighestRateProduct();
		if(arg == null)
		{
			arg = new HashMap<String, Object>();
		}
		highestRatecd.principle = 10000;
		arg.put(getProductType().toString(), highestRatecd);
		return arg;
	}

	@Override
	protected boolean makeDecision(Map<String, Object> arg) throws Exception {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(getRuleEnable());
	}
}
