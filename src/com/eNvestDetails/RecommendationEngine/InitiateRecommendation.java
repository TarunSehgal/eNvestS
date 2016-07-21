package com.eNvestDetails.RecommendationEngine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


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
	   
	   public void processRequest(Object arg) throws Exception {
		   AbstractComponent  executeRule= null;
		   for(String rule :rules){
			   executeRule = (AbstractComponent) context.getBean(rule);
			   executeRule.execute(arg);
		   }
	      //firstStep.execute(arg);
	   }
	   
}
