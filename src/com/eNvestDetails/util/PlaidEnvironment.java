package com.eNvestDetails.util;

public class PlaidEnvironment implements IPlaidEnvironment {
	String environment;
	String clientId;
	String secretKey;
	
	public PlaidEnvironment(String environment, String clientId,
			String secretKey) {
		super();
		this.environment = environment;
		this.clientId = clientId;
		this.secretKey = secretKey;
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
}