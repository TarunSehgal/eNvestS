package com.envest.services.components.recommendation;

import com.envest.services.components.recommendationengine.AbstractProductRule;
import com.envest.services.components.recommendationengine.RecommendationResponse;
import com.envest.services.components.util.Product.CDProduct;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.components.util.account.UserProfileData;

public class CDRule extends AbstractProductRule {

	@Override
	protected ProductType getProductType() {
		return ProductType.CertificateOfDeposit;
	}

	@Override
	protected RecommendationResponse doWork(UserProfileData arg) throws Exception {
		
		CDProduct highestRatecd = (CDProduct) getHighestRateProduct();
		RecommendationResponse response = new RecommendationResponse();
		highestRatecd.principle = 10000;
		response.addRecommendedProduct(highestRatecd);
		return response;
	}

	@Override
	protected boolean makeDecision(UserProfileData arg) throws Exception {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(getRuleEnable());
	}
}
