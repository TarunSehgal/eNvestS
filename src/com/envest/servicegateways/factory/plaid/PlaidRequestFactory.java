package com.envest.servicegateways.factory.plaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envest.servicegateways.plaid.IPlaidEnvironment;
import com.plaid.client.http.PlaidHttpRequest;

@Component
class PlaidRequestFactory implements IPlaidRequestFactory {

	@Autowired
	IPlaidEnvironment plaidEnvironment;
	
	public PlaidRequestFactory() {
	}	

	@Override
	public PlaidHttpRequest GetPlaidMFARequest(String mfa, String accessToken) {
		PlaidHttpRequest request = getPlaidRequest("/info/step");
		request.addParameter("client_id", plaidEnvironment.getClientId());
		request.addParameter("secret", plaidEnvironment.getSecretKey());
		request.addParameter("mfa", mfa);
		request.addParameter("access_token", accessToken);

		return request;
	}
	
	@Override
	public PlaidHttpRequest getPlaidRequest(String path)
	{
		return new PlaidHttpRequest(path);
	}
}