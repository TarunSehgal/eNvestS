package com.envest.services.components.recommendationengine;

import java.util.Map;

import com.envest.services.components.util.account.UserProfileData;

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

	public UserProfileData execute(UserProfileData arg) throws Exception {
		boolean outcome = makeDecision(arg);
		UserProfileData object = doWork(arg);
		if (null != nextStep && outcome){
			object = nextStep.execute(arg);
		}
		return object;
	}

	public AbstractComponent getOppurtunityRule() {
		return oppurtunityRule;
	}

	public void setOppurtunityRule(AbstractComponent oppurtunityRule) {
		this.oppurtunityRule = oppurtunityRule;
	}

	protected abstract boolean makeDecision(UserProfileData arg) throws Exception;
	
	protected abstract UserProfileData doWork(UserProfileData arg) throws Exception;

}
