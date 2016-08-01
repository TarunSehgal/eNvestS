package com.eNvestDetails.Recommendation;

import java.util.HashMap;
import java.util.Map;

import com.eNvestDetails.RecommendationEngine.AbstractProductRule;
import com.eNvestDetails.util.Product.CDProduct;
import com.eNvestDetails.util.Product.ProductType;

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
