package com.eNvestDetails.RecommendationEngine;

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

	public void execute(Object arg) throws Exception {
		boolean outcome = makeDecision(arg);
		if (null != nextStep && outcome){
			nextStep.execute(arg);
		}
			
	}

	public AbstractComponent getOppurtunityRule() {
		return oppurtunityRule;
	}

	public void setOppurtunityRule(AbstractComponent oppurtunityRule) {
		this.oppurtunityRule = oppurtunityRule;
	}

	protected abstract boolean makeDecision(Object arg) throws Exception;
	
	protected abstract void doWork(Object arg) throws Exception;

}
