package com.envest.services.components.recommendationengine;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.envest.services.components.userprofile.EnvestUserProfile;


public class InitiateRecommendation {
	private AbstractComponent firstStep;
	
	@Autowired
	private ApplicationContext context;

	private List<String> rules;
	   
	   public List<String> getRules() {
		return rules;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
	}

	public void setFirstStep(AbstractComponent firstStep) {
	      this.firstStep = firstStep;
	   }
	   
	   public RecommendationResponse processRequest(EnvestUserProfile arg) throws Exception {
		   AbstractComponent  executeRule= null;
		   RecommendationResponse response = new RecommendationResponse();
		   for(String rule :rules){
			   executeRule = (AbstractComponent) context.getBean(rule);
			   response.Merge(executeRule.execute(arg));
		   }
	      //firstStep.execute(arg);
		   return response;
	   }
	   
}
