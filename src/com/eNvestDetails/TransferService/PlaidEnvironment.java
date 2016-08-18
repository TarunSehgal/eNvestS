package com.eNvestDetails.TransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.ConfigFactory;

@Component
@Scope("singleton")
public class PlaidEnvironment implements IPlaidEnvironment {
	String environment;
	String clientId;
	String secretKey;
	@Autowired
	private ConfigFactory config = null;
	private static boolean isInitialized = false;
	
	public PlaidEnvironment() {
	}

	@Override
	public String getEnvironment() {
		if(!isInitialized)
		{
			Initialize();
		}
		return environment;
	}

	@Override
	public String getClientId() {
		if(!isInitialized)
		{
			Initialize();
		}
		return clientId;
	}

	@Override
	public String getSecretKey() {
		if(!isInitialized)
		{
			Initialize();
		}
		return secretKey;
	}
	
	private void Initialize()
	{
		environment = config.getResultString("env");
		clientId = config.getResultString("clientid");
		secretKey = config.getResultString("key");
	}
}