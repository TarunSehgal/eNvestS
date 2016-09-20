package com.envest.servicegateways.factory.plaid;

import com.plaid.client.http.PlaidHttpRequest;

public interface IPlaidRequestFactory {
	public PlaidHttpRequest GetPlaidMFARequest(String mfa, String accessToken);
	public PlaidHttpRequest getPlaidRequest(String path);
}