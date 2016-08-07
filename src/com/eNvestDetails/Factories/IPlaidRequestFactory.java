package com.eNvestDetails.Factories;

import com.plaid.client.http.PlaidHttpRequest;

public interface IPlaidRequestFactory {
	PlaidHttpRequest GetPlaidMFARequest(String mfa, String accessToken);
}