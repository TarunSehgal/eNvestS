package com.envest.services.components.recommendationengine;

import java.util.Map;

public abstract class AbstractComponent {

	public abstract Map<String,Object> execute(Map<String,Object> arg) throws Exception;
}
