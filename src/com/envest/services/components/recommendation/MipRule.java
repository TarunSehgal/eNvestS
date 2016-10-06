package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.recommendationengine.RecommendationResponse;
import com.envest.services.components.userprofile.EnvestUserProfile;
import com.envest.services.components.util.Product.MIProduct;
import com.envest.services.components.util.Product.ProductType;

public class MipRule extends AbstractProductRule {
	
	@Override
	protected ProductType getProductType() {
		return ProductType.MonthlyInvestmentPlan;
	}

	@Override
	protected boolean makeDecision(EnvestUserProfile arg) throws Exception {
		return Boolean.parseBoolean(getRuleEnable());
	}

	@Override
	protected RecommendationResponse doWork(EnvestUserProfile arg) throws Exception {
		MIProduct highestRateProduct = (MIProduct) getHighestRateProduct();	
		RecommendationResponse response = new RecommendationResponse();
		highestRateProduct.monthlyCashFlow = 500;
		response.addRecommendedProduct(highestRateProduct);
		return response;
	}

}
