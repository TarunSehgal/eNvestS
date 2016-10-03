package com.envest.services.components.recommendationengine;

import com.envest.services.components.util.account.UserProfileData;

public abstract class AbstractAction extends AbstractComponent {
	private AbstractComponent nextStep;

	public UserProfileData execute(UserProfileData arg) throws Exception {
		
		UserProfileData object = this.doExecute(arg);
		if (nextStep != null){
			object = nextStep.execute(arg);
		}
		return object;
	}

	protected abstract UserProfileData doExecute(UserProfileData arg) throws Exception;

	public void setNextStep(AbstractComponent nextStep) {
		this.nextStep = nextStep;
	}

	public AbstractComponent getNextStep() {
		return nextStep;
	}
}
