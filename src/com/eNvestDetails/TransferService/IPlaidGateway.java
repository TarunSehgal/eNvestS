package com.eNvestDetails.TransferService;

import com.plaid.client.http.HttpResponseWrapper;
import com.plaid.client.http.PlaidHttpRequest;
import com.plaid.client.request.ConnectOptions;
import com.plaid.client.request.InfoOptions;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.TransactionsResponse;

public interface IPlaidGateway {
	public <R> HttpResponseWrapper<R> executeGetRequest(PlaidHttpRequest input, Class<R> inputClass);
	public <R> HttpResponseWrapper<R> executePostRequest(PlaidHttpRequest input, Class<R> inputClass);	
	public void updateTransactions();	
	public TransactionsResponse addConnectProduct(ConnectOptions options, String accesToken);
	public TransactionsResponse addConnectProduct(ConnectOptions options);
	public InfoResponse getInfoResponse(String userName, String password, String bankName, InfoOptions options);
}
