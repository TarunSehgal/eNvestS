package com.envest.services.components.recommendationengine;

import java.util.Map;

import com.envest.services.components.userprofile.EnvestUserProfile;

public abstract class AbstractRule extends AbstractComponent {

	private AbstractComponent oppurtunityRule;
	private AbstractComponent nextStep;
	
	public AbstractComponent getNextStep() {
		return nextStep;
	}

	public void setNextStep(AbstractComponent nextStep) {
		this.nextStep = nextStep;
	}

	private String ruleEnable = null;

	public String getRuleEnable() {
		return ruleEnable;
	}

	public void setRuleEnable(String ruleEnable) {
		this.ruleEnable = ruleEnable;
	}

	public RecommendationResponse execute(EnvestUserProfile arg) throws Exception {
		boolean outcome = makeDecision(arg);
		RecommendationResponse response = doWork(arg);
		if (null != nextStep && outcome){
			response.Merge(nextStep.execute(arg));
		}
		return response;		
	}

	public AbstractComponent getOppurtunityRule() {
		return oppurtunityRule;
	}

	public void setOppurtunityRule(AbstractComponent oppurtunityRule) {
		this.oppurtunityRule = oppurtunityRule;
	}

	protected abstract boolean makeDecision(EnvestUserProfile arg) throws Exception;
	
	protected abstract RecommendationResponse doWork(EnvestUserProfile arg) throws Exception;

}
