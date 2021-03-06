package com.envest.services.components.recommendation;

import org.apache.log4j.Logger;

import com.envest.services.components.recommendationengine.AbstractRule;
import com.envest.services.components.recommendationengine.RecommendationResponse;
import com.envest.services.response.EnvestUserProfile;

public class TestOppurtunity extends AbstractRule {
	
	private static Logger log = Logger.getLogger(TestOppurtunity.class.getName()); 

	protected boolean makeDecision(EnvestUserProfile arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}

	
	protected RecommendationResponse doWork(EnvestUserProfile arg) throws Exception {
		log.info("inside doWork method in testoppurtunity");
		RecommendationResponse response = new RecommendationResponse();
		return response;
	}
	
}
