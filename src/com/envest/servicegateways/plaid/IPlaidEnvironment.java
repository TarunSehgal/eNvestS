package com.envest.servicegateways.plaid;

public interface IPlaidEnvironment {
	String getEnvironment();
	String getClientId();
	String getSecretKey();
}
