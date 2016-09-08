package com.envest.servicegateways.plaid;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.envest.services.components.config.ConfigFactory;

@Component("tplaidEnvironment")
@Scope("singleton")
class PlaidEnvironment implements IPlaidEnvironment {
	String environment;
	String clientId;
	String secretKey;
	@Autowired
	private ConfigFactory config = null;
	
	public PlaidEnvironment() {
	}

	@Override
	public String getEnvironment() {
		return environment;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String getSecretKey() {
		return secretKey;
	}
	
	@PostConstruct
	public void Initialize()
	{
		environment = config.getResultString("env");
		clientId = config.getResultString("clientid");
		secretKey = config.getResultString("key");
	}
}