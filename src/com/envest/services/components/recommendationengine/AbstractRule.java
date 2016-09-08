package com.envest.services.components.recommendationengine;

import java.util.Map;

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

	public Map<String,Object> execute(Map<String,Object> arg) throws Exception {
		boolean outcome = makeDecision(arg);
		Map<String,Object> object = doWork(arg);
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

	protected abstract boolean makeDecision(Map<String,Object> arg) throws Exception;
	
	protected abstract Map<String,Object> doWork(Map<String,Object> arg) throws Exception;

}
