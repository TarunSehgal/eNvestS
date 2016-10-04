package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.util.Product.GoalSeekProduct;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.components.util.account.UserProfileData;

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
	protected boolean makeDecision(UserProfileData arg) throws Exception {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected UserProfileData doWork(UserProfileData arg) throws Exception {
		
		GoalSeekProduct goalSeekProduct = (GoalSeekProduct) getHighestRateProduct();
		if(arg == null)
		{
			arg = new UserProfileData();
		}
		goalSeekProduct.principle = 25000;
		arg.addRecommendedProduct(goalSeekProduct);
		return arg;
	}

}
