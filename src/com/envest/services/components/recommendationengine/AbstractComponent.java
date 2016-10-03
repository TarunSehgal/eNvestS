package com.envest.services.components.recommendationengine;

import java.util.Map;

import com.envest.services.components.util.account.UserProfileData;

public abstract class AbstractComponent {

	public abstract UserProfileData execute(UserProfileData arg) throws Exception;
}
