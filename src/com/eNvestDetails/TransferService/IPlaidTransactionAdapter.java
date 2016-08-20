package com.eNvestDetails.TransferService;

import java.util.List;
import java.util.Map;

import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.response.Transaction;

public interface IPlaidTransactionAdapter {
	public List<TransactionDetail> convertTransactionsToTransactionDetail(List<Transaction> transactions);
	public Map<String,UserInfo.Summary> convertTransactionsToSummaries(List<Transaction> transactions,Map<String, String> categories);
}
