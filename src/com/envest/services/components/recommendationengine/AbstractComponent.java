package com.envest.services.components.recommendationengine;

import com.envest.services.components.userprofile.EnvestUserProfile;

public abstract class AbstractComponent {

	public abstract RecommendationResponse execute(EnvestUserProfile arg) throws Exception;
}
