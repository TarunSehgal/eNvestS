package com.envest.services.components.recommendationengine;

import java.util.Map;

public abstract class AbstractAction extends AbstractComponent {
	private AbstractComponent nextStep;

	public Map<String,Object> execute(Map<String,Object> arg) throws Exception {
		
		Map<String,Object> object = this.doExecute(arg);
		if (nextStep != null){
			object = nextStep.execute(arg);
		}
		return object;
	}

	protected abstract Map<String,Object> doExecute(Map<String,Object> arg) throws Exception;

	public void setNextStep(AbstractComponent nextStep) {
		this.nextStep = nextStep;
	}

	public AbstractComponent getNextStep() {
		return nextStep;
	}
}
