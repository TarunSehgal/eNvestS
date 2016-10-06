package com.envest.services.components.recommendationengine;

import com.envest.services.components.userprofile.EnvestUserProfile;

public abstract class AbstractAction extends AbstractComponent {
	private AbstractComponent nextStep;

	public RecommendationResponse execute(EnvestUserProfile arg) throws Exception {
		
		RecommendationResponse response = this.doExecute(arg);
		if (nextStep != null){
			response.Merge(nextStep.execute(arg));
		}
		return response;
	}

	protected abstract RecommendationResponse doExecute(EnvestUserProfile arg) throws Exception;

	public void setNextStep(AbstractComponent nextStep) {
		this.nextStep = nextStep;
	}

	public AbstractComponent getNextStep() {
		return nextStep;
	}
}
