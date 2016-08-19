package com.eNvestDetails.TransferService;

import com.plaid.client.PlaidUserClient;
import com.plaid.client.http.HttpResponseWrapper;
import com.plaid.client.http.PlaidHttpRequest;
import com.plaid.client.request.ConnectOptions;
import com.plaid.client.request.GetOptions;
import com.plaid.client.request.InfoOptions;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.TransactionsResponse;

public interface IPlaidGateway {
	public <R> HttpResponseWrapper<R> createExecuteGetRequest(String path, Class<R> inputClass);
	public <R> HttpResponseWrapper<R> createExecutePostRequest(String path, Class<R> inputClass);	
	public <R> HttpResponseWrapper<R> createExecuteMFARequest(String mfa, String accessToken, Class<R> inputClass);
	public UpdateTransactionResult updateTransactions(String acessToken);
	public UpdateTransactionResult updateTransactions(String accessToken, GetOptions options);
	public TransactionsResponse addConnectProduct(ConnectOptions options, String accesToken);
	public TransactionsResponse addConnectProduct(ConnectOptions options);
	public InfoResponse getInfoResponse(String userName, String password, String bankName, InfoOptions options);
	public PlaidUserClient getPlaidClient();
	public void deleteAccount(String accessToken);
}
