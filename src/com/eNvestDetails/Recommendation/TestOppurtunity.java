package com.eNvestDetails.Recommendation;

import java.util.Map;

import org.apache.log4j.Logger;

import com.eNvestDetails.RecommendationEngine.AbstractRule;

public class TestOppurtunity extends AbstractRule {
	
	private static Logger log = Logger.getLogger(TestOppurtunity.class.getName()); 

	protected boolean makeDecision(Map<String,Object> arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}

	
	protected Map<String,Object> doWork(Map<String,Object> arg) throws Exception {
		log.info("inside doWork method in testoppurtunity");
		return arg;
	}
	
}
