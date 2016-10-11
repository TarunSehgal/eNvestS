package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.recommendationengine.RecommendationResponse;
import com.envest.services.components.util.Product.GoalSeekProduct;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.response.EnvestUserProfile;

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
	protected boolean makeDecision(EnvestUserProfile arg) throws Exception {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected RecommendationResponse doWork(EnvestUserProfile arg) throws Exception {
		
		GoalSeekProduct goalSeekProduct = (GoalSeekProduct) getHighestRateProduct();
		RecommendationResponse response = new RecommendationResponse();
		goalSeekProduct.principle = 25000;
		response.addRecommendedProduct(goalSeekProduct);
		return response;
	}

}
