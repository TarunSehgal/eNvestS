package com.envest.services.components.recommendation;

import java.util.Map;

import org.apache.log4j.Logger;

import com.envest.services.components.recommendationengine.AbstractRule;
import com.envest.services.components.util.account.UserProfileData;

public class TestOppurtunity extends AbstractRule {
	
	private static Logger log = Logger.getLogger(TestOppurtunity.class.getName()); 

	protected boolean makeDecision(UserProfileData arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}

	
	protected UserProfileData doWork(UserProfileData arg) throws Exception {
		log.info("inside doWork method in testoppurtunity");
		return arg;
	}
	
}
