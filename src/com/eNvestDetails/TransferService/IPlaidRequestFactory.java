package com.eNvestDetails.TransferService;

import com.plaid.client.http.PlaidHttpRequest;

public interface IPlaidRequestFactory {
	public PlaidHttpRequest GetPlaidMFARequest(String mfa, String accessToken);
	public PlaidHttpRequest getPlaidRequest(String path);
}