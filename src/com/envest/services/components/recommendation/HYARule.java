package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.recommendationengine.RecommendationResponse;
import com.envest.services.components.userprofile.EnvestUserProfile;
import com.envest.services.components.util.Product.HighYieldProduct;
import com.envest.services.components.util.Product.ProductType;

public class HYARule extends AbstractProductRule {

	@Override
	protected ProductType getProductType() {
		return ProductType.HighYieldAccount;
	}

	@Override
	protected boolean makeDecision(EnvestUserProfile arg) throws Exception {
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected RecommendationResponse doWork(EnvestUserProfile arg) throws Exception {
		HighYieldProduct highestRateProduct = (HighYieldProduct) getHighestRateProduct();
		RecommendationResponse response = new RecommendationResponse();
		highestRateProduct.principle = 10000;
		response.addRecommendedProduct(highestRateProduct);
		return response;
	}

}
