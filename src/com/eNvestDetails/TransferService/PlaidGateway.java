package com.eNvestDetails.TransferService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Response.PlaidCategory;
import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.http.ApacheHttpClientHttpDelegate;
import com.plaid.client.http.HttpResponseWrapper;
import com.plaid.client.http.PlaidHttpRequest;
import com.plaid.client.request.ConnectOptions;
import com.plaid.client.request.Credentials;
import com.plaid.client.request.GetOptions;
import com.plaid.client.request.InfoOptions;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.TransactionsResponse;

@Component
@Scope("singleton")
public class PlaidGateway implements IPlaidGateway {

	ApacheHttpClientHttpDelegate httpDelegate = null;	
	@Autowired
	private PlaidClient plaidClient;
	@Autowired
	IPlaidToEnvestConverter plaidToEnvestConverter;
	@Autowired
	IPlaidRequestFactory plaidRequestFactory;

private PlaidUserClient plaidUserClient;

public PlaidGateway()
{
	
}

		
	@Override
	public <R> HttpResponseWrapper<R> createExecutePostRequest(String path, Class<R> inputClass)
	{
		PlaidHttpRequest request = plaidRequestFactory.getPlaidRequest(path);
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_TEST, HttpClientBuilder.create().disableContentCompression().build());
	    return (HttpResponseWrapper<R>) httpDelegate.doPost(request, inputClass);
	}
	
	@Override
	public UserInfo createExecuteMFARequest(String mfa, String accessToken)
	{
		PlaidHttpRequest request = plaidRequestFactory.GetPlaidMFARequest(mfa, accessToken);
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_TEST, HttpClientBuilder.create().disableContentCompression().build());
	    HttpResponseWrapper<InfoResponse> response= httpDelegate.doPost(request, InfoResponse.class);
	    return plaidToEnvestConverter.convertInforesponseToUserinfo(response.getResponseBody(), null, null);
	}
	
	@Override
	public <R> HttpResponseWrapper<R> createExecuteGetRequest(String path, Class<R> inputClass)
	{
		PlaidHttpRequest request = plaidRequestFactory.getPlaidRequest(path);
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());
	    return (HttpResponseWrapper<R>) httpDelegate.doGet(request, inputClass);
	}
	
	@PostConstruct
	public void Initialize()
	{
		httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());		
	}

	@Override
	public UpdateTransactionResult updateTransactions(String accessToken, GetOptions options, String bank) {
		plaidUserClient = plaidClient.getPlaidClient();
		plaidUserClient.setAccessToken(accessToken);
		return plaidToEnvestConverter.convertTransactionResponse(plaidUserClient.updateTransactions(options), bank, getCategories());
	}
	
	@Override
	public UpdateTransactionResult updateTransactions(String accessToken, String bank) {
		plaidUserClient = plaidClient.getPlaidClient();
		plaidUserClient.setAccessToken(accessToken);	
		return plaidToEnvestConverter.convertTransactionResponse(plaidUserClient.updateTransactions(), bank, getCategories());
	}

	@Override
	public TransactionsResponse addConnectProduct(ConnectOptions options, String accessToken) {
		plaidUserClient = plaidClient.getPlaidClient();
	    plaidUserClient.setAccessToken(accessToken);
	    return plaidUserClient.addProduct("connect", options);
	}
	
	@Override
	public TransactionsResponse addConnectProduct(ConnectOptions options) {
		plaidUserClient = plaidClient.getPlaidClient();
	    return plaidUserClient.addProduct("connect", options);
	}

	@Override
	public UserInfo getInfoResponse(String userId, String password, String bankName, InfoOptions options) {
		plaidUserClient = plaidClient.getPlaidClient();
		Credentials testCredentials = new Credentials(userId, password);
		InfoResponse response =  plaidUserClient.info(testCredentials, bankName,	options);
		return plaidToEnvestConverter.convertInforesponseToUserinfo(response, bankName, userId);
	}


	@Override
	public PlaidUserClient getPlaidClient() {
		return plaidClient.getPlaidClient();
	}


	@Override
	public void deleteAccount(String accessToken) {
		plaidUserClient = plaidClient.getPlaidClient();
		plaidUserClient.setAccessToken(accessToken);
		plaidUserClient.deleteUser();		
	}
	
	public Map<String,String> getCategories(){
		Map<String,String> cmap = new HashMap<String,String>(1000);
		try{
		    HttpResponseWrapper<PlaidCategory[]> response = createExecuteGetRequest("/categories", PlaidCategory[].class);
			for(PlaidCategory category : response.getResponseBody()){
				String path = "";
				for(String hierarchy: category.getHierarchy()){
					path = path +hierarchy +",";
				}
				cmap.put(category.getId(), path);	
			}
		}catch (Exception e){
		}
		return cmap;
	}
}
