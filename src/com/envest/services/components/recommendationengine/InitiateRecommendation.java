package com.envest.services.components.recommendationengine;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.envest.services.components.util.account.UserProfileData;


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
	   
	   public UserProfileData processRequest(UserProfileData arg) throws Exception {
		   AbstractComponent  executeRule= null;
		  // Map<String,Object> returnObject 
		   for(String rule :rules){
			   executeRule = (AbstractComponent) context.getBean(rule);
			   arg = executeRule.execute(arg);
		   }
	      //firstStep.execute(arg);
		   return arg;
	   }
	   
}
