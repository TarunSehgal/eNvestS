package com.eNvestDetails.Factories;

import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.util.IPlaidEnvironment;
import com.eNvestDetails.util.PlaidEnvironment;
import com.plaid.client.http.PlaidHttpRequest;

public class PlaidRequestFactory implements IPlaidRequestFactory {

	@Autowired
	IPlaidEnvironment plaidEnvironment;
	
	public PlaidRequestFactory() {
	}	

	@Override
	public PlaidHttpRequest GetPlaidMFARequest(String mfa, String accessToken) {
		PlaidHttpRequest request = new PlaidHttpRequest("/info/step");
		request.addParameter("client_id", plaidEnvironment.getClientId());
		request.addParameter("secret", plaidEnvironment.getSecretKey());
		request.addParameter("mfa", mfa);
		request.addParameter("access_token", accessToken);

		return request;
	}
}