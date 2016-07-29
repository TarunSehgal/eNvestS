package com.eNvestDetails.Recommendation;

import com.eNvestDetails.RecommendationEngine.AbstractProductRule;
import com.eNvestDetails.util.Product.CDProduct;
import com.eNvestDetails.util.Product.ProductType;

public class CDRule extends AbstractProductRule {


	protected boolean makeDecision(Object arg) throws Exception
	{
		return Boolean.parseBoolean(getRuleEnable());
	}
	
	protected void doWork(Object arg) throws Exception
	{
		CDProduct highestRatecd = (CDProduct) getHighestRateProduct();		
	}

	@Override
	protected ProductType getProductType() {
		return ProductType.CertificateOfDeposit;
	}
}
