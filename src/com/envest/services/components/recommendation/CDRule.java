package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.util.Product.CDProduct;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.components.util.account.UserProfileData;

public class CDRule extends AbstractProductRule {

	@Override
	protected ProductType getProductType() {
		return ProductType.CertificateOfDeposit;
	}

	@Override
	protected UserProfileData doWork(UserProfileData arg) throws Exception {
		
		CDProduct highestRatecd = (CDProduct) getHighestRateProduct();
		if(arg == null)
		{
			arg = new UserProfileData();
		}
		highestRatecd.principle = 10000;
		arg.addAsset(highestRatecd);
		return arg;
	}

	@Override
	protected boolean makeDecision(UserProfileData arg) throws Exception {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(getRuleEnable());
	}
}
