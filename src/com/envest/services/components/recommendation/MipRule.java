package com.envest.services.components.recommendation;

import java.util.HashMap;
import java.util.Map;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.util.Product.MIProduct;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.components.util.account.UserProfileData;

public class MipRule extends AbstractProductRule {
	
	@Override
	protected ProductType getProductType() {
		return ProductType.MonthlyInvestmentPlan;
	}

	@Override
	protected boolean makeDecision(UserProfileData arg) throws Exception {
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected UserProfileData doWork(UserProfileData arg) throws Exception {
		MIProduct highestRateProduct = (MIProduct) getHighestRateProduct();	
		if(arg == null)
		{
			arg = new UserProfileData();
		}
		highestRateProduct.monthlyCashFlow = 500;
		arg.addAsset(highestRateProduct);
		return arg;
	}

}
