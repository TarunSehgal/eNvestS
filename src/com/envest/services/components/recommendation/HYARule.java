package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.util.Product.HighYieldProduct;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.components.util.account.UserProfileData;

public class HYARule extends AbstractProductRule {

	@Override
	protected ProductType getProductType() {
		return ProductType.HighYieldAccount;
	}

	@Override
	protected boolean makeDecision(UserProfileData arg) throws Exception {
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected UserProfileData doWork(UserProfileData arg) throws Exception {
		HighYieldProduct highestRateProduct = (HighYieldProduct) getHighestRateProduct();
		if(arg == null)
		{
			arg = new UserProfileData();
		}
		highestRateProduct.principle = 10000;
		arg.addRecommendedProduct(highestRateProduct);
		return arg;
	}

}
