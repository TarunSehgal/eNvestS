package com.envest.services.components.recommendationengine;

import com.envest.services.components.util.account.UserProfileData;

public abstract class AbstractAction extends AbstractComponent {
	private AbstractComponent nextStep;

	public RecommendationResponse execute(UserProfileData arg) throws Exception {
		
		RecommendationResponse response = this.doExecute(arg);
		if (nextStep != null){
			response.Merge(nextStep.execute(arg));
		}
		return response;
	}

	protected abstract RecommendationResponse doExecute(UserProfileData arg) throws Exception;

	public void setNextStep(AbstractComponent nextStep) {
		this.nextStep = nextStep;
	}

	public AbstractComponent getNextStep() {
		return nextStep;
	}
}
