package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.recommendationengine.RecommendationResponse;
import com.envest.services.components.userprofile.EnvestUserProfile;
import com.envest.services.components.util.Product.CDProduct;
import com.envest.services.components.util.Product.ProductType;

public class CDRule extends AbstractProductRule {

	@Override
	protected ProductType getProductType() {
		return ProductType.CertificateOfDeposit;
	}

	@Override
	protected RecommendationResponse doWork(EnvestUserProfile arg) throws Exception {
		
		CDProduct highestRatecd = (CDProduct) getHighestRateProduct();
		RecommendationResponse response = new RecommendationResponse();
		highestRatecd.principle = 10000;
		response.addRecommendedProduct(highestRatecd);
		return response;
	}

	@Override
	protected boolean makeDecision(EnvestUserProfile arg) throws Exception {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(getRuleEnable());
	}
}
