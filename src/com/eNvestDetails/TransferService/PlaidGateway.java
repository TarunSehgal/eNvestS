package com.eNvestDetails.TransferService;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eNvestDetails.util.IPlaidEnvironment;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.http.ApacheHttpClientHttpDelegate;
import com.plaid.client.http.HttpResponseWrapper;
import com.plaid.client.http.PlaidHttpRequest;
import com.plaid.client.request.ConnectOptions;
import com.plaid.client.request.Credentials;
import com.plaid.client.request.InfoOptions;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.TransactionsResponse;

@Component
@Scope("singleton")
public class PlaidGateway implements IPlaidGateway {

	ApacheHttpClientHttpDelegate httpDelegate = null;	
	private boolean isInitialized = false;
	@Autowired
private PlaidClient plaidClient;
private PlaidUserClient plaidUserClient;

public PlaidGateway()
{
	
}
		
	@Override
	public <R> HttpResponseWrapper<R> executePostRequest(PlaidHttpRequest input, Class<R> inputClass)
	{
		if(!isInitialized)
		{
			Initialize();
		}
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_TEST, HttpClientBuilder.create().disableContentCompression().build());
	    return (HttpResponseWrapper<R>) httpDelegate.doPost(input, inputClass);
	}
	
	@Override
	public <R> HttpResponseWrapper<R> executeGetRequest(PlaidHttpRequest input, Class<R> inputClass)
	{
		if(!isInitialized)
		{
			Initialize();
		}
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());
	    return (HttpResponseWrapper<R>) httpDelegate.doGet(input, inputClass);
	}
	
	private void Initialize()
	{
		httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());
		plaidUserClient = plaidClient.getPlaidClient();
		isInitialized =true;
	}

	@Override
	public void updateTransactions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TransactionsResponse addConnectProduct(ConnectOptions options, String accessToken) {
		if(!isInitialized)
		{
			Initialize();
		}
	    plaidUserClient.setAccessToken(accessToken);
	    return addConnectProduct(options);
	}
	
	@Override
	public TransactionsResponse addConnectProduct(ConnectOptions options) {
		if(!isInitialized)
		{
			Initialize();
		}
	    return plaidUserClient.addProduct("connect", options);
	}

	@Override
	public InfoResponse getInfoResponse(String userName, String password, String bankName, InfoOptions options) {
		if(!isInitialized)
		{
			Initialize();
		}
		Credentials testCredentials = new Credentials(userName, password);
		return plaidUserClient.info(testCredentials, bankName,	options);
	}
}
