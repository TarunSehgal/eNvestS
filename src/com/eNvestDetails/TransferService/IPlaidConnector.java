package com.eNvestDetails.TransferService;

import java.util.Map;

import com.eNvestDetails.Response.MfaResponseDetail;
import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.request.ConnectOptions;
import com.plaid.client.request.GetOptions;
import com.plaid.client.request.InfoOptions;
import com.plaid.client.response.MfaResponse;
import com.plaid.client.response.TransactionsResponse;

public interface IPlaidConnector {
	/*public <R> HttpResponseWrapper<R> createExecuteGetRequest(String path, Class<R> inputClass);
	public <R> HttpResponseWrapper<R> createExecutePostRequest(String path, Class<R> inputClass);	*/
	public Map<String,String> getCategories();
	public UserInfo executeMFARequest(String mfa, String accessToken);
	public UpdateTransactionResult updateTransactions(String acessToken, String bank);
	public UpdateTransactionResult updateTransactions(String accessToken, GetOptions options, String bank);
	public TransactionsResponse addConnectProduct(ConnectOptions options, String accesToken);
	public TransactionsResponse addConnectProduct(ConnectOptions options);
	public UserInfo getInfoResponse(String userName, String password, String bankName, InfoOptions options);
	public void deleteAccount(String accessToken);
	public MfaResponseDetail handleMfaException(MfaResponse mfa,String bank);
}
