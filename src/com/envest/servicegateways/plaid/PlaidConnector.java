package com.envest.servicegateways.plaid;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.envest.services.response.MfaResponseDetail;
import com.envest.services.response.PlaidCategory;
import com.envest.services.response.UserInfo;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.exception.PlaidMfaException;
import com.plaid.client.exception.PlaidServersideException;
import com.plaid.client.http.ApacheHttpClientHttpDelegate;
import com.plaid.client.http.HttpResponseWrapper;
import com.plaid.client.http.PlaidHttpRequest;
import com.plaid.client.request.ConnectOptions;
import com.plaid.client.request.Credentials;
import com.plaid.client.request.GetOptions;
import com.plaid.client.request.InfoOptions;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.MfaResponse;
import com.plaid.client.response.TransactionsResponse;

@Component("plaidConnector")
@Scope("singleton")
public class PlaidConnector implements IPlaidConnector {

	ApacheHttpClientHttpDelegate httpDelegate = null;	
	@Autowired
	private PlaidClient plaidClient;
	@Autowired
	IPlaidToEnvestConverter plaidToEnvestConverter;
	@Autowired
	IPlaidRequestFactory plaidRequestFactory;

private PlaidUserClient plaidUserClient;

public PlaidConnector()
{
	
}

		
	@PostConstruct
	public void Initialize()
	{
		httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());		
	}

	@Override
	public UserInfo getUserInfoDetails(String mfa, String accessToken)
	{
		PlaidHttpRequest request = plaidRequestFactory.GetPlaidMFARequest(mfa, accessToken);
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_TEST, HttpClientBuilder.create().disableContentCompression().build());
	    HttpResponseWrapper<InfoResponse> response= httpDelegate.doPost(request, InfoResponse.class);
	    return plaidToEnvestConverter.convertInforesponseToUserinfo(response.getResponseBody(), null, null);
	}
	
	@Override
	public UpdateTransactionResult updateTransactions(String accessToken, GetOptions options, String bank) {
		UpdateTransactionResult result = null;
		try{
		plaidUserClient = plaidClient.getPlaidClient();
		plaidUserClient.setAccessToken(accessToken);

		TransactionsResponse response = (null == options) ?	plaidUserClient.updateTransactions() :
			plaidUserClient.updateTransactions(options);
				
		result = plaidToEnvestConverter.convertTransactionResponse(response, bank, getCategories());
		}catch(PlaidMfaException e){
			MfaResponse mfa = e.getMfaResponse();
			handleMfaException(mfa, bank);
		}
		
		return result;
	}
	
	@Override
	public UpdateTransactionResult updateTransactions(String accessToken, String bank) {
		return updateTransactions(accessToken,null, bank);
	/*	
		UpdateTransactionResult result = null;
		try{
		plaidUserClient = plaidClient.getPlaidClient();
		plaidUserClient.setAccessToken(accessToken);	
		result = plaidToEnvestConverter.convertTransactionResponse(plaidUserClient.updateTransactions(), bank, getCategories());
		}catch(PlaidMfaException e){
			MfaResponse mfa = e.getMfaResponse();
			handleMfaException(mfa, bank);
		}
		return result;*/
	}

	@Override
	public TransactionsResponse addConnectProduct(ConnectOptions options, String accessToken) {
		plaidUserClient = plaidClient.getPlaidClient();
	    plaidUserClient.setAccessToken(accessToken);
	    return plaidUserClient.addProduct("connect", options);
	}
	
	@Override
	public TransactionsResponse addConnectProduct(String accessToken) {
	    return addConnectProduct(null, accessToken);
	}
	
	@Override
	public TransactionsResponse addConnectProduct(ConnectOptions options) {
		plaidUserClient = plaidClient.getPlaidClient();
	    return plaidUserClient.addProduct("connect", options);
	}

	@Override
	public UserInfo getUserAccountDetails(String userId, String password, String bankName, InfoOptions options) {
		plaidUserClient = plaidClient.getPlaidClient();
		Credentials testCredentials = new Credentials(userId, password);
		InfoResponse response =  plaidUserClient.info(testCredentials, bankName,	options);
		return plaidToEnvestConverter.convertInforesponseToUserinfo(response, bankName, userId);
	}

	@Override
	public void deleteAccount(String accessToken) {
		plaidUserClient = plaidClient.getPlaidClient();
		plaidUserClient.setAccessToken(accessToken);
		plaidUserClient.deleteUser();		
	}
	
	@Override	
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
	
	@Override
	public MfaResponseDetail handleMfaException(MfaResponse mfa, String bank) {
		return MFAHandler.handleMfaException(mfa, bank);
	}
	
	private <R> HttpResponseWrapper<R> createExecutePostRequest(String path, Class<R> inputClass)
	{
		PlaidHttpRequest request = plaidRequestFactory.getPlaidRequest(path);
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_TEST, HttpClientBuilder.create().disableContentCompression().build());
	    return (HttpResponseWrapper<R>) httpDelegate.doPost(request, inputClass);
	}
	
	private <R> HttpResponseWrapper<R> createExecuteGetRequest(String path, Class<R> inputClass)
	{
		PlaidHttpRequest request = plaidRequestFactory.getPlaidRequest(path);
		 httpDelegate =  new ApacheHttpClientHttpDelegate
				 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());
	    return (HttpResponseWrapper<R>) httpDelegate.doGet(request, inputClass);
	}
}
