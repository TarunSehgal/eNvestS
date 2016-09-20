package com.envest.servicegateways.adapter.plaid;

import java.util.List;
import java.util.Map;

import com.envest.services.response.TransactionDetail;
import com.envest.services.response.UserInfo;
import com.plaid.client.response.Transaction;

public interface IPlaidTransactionAdapter {
	public List<TransactionDetail> convertTransactionsToTransactionDetail(List<Transaction> transactions);
	public Map<String,UserInfo.Summary> convertTransactionsToSummaries(List<Transaction> transactions,Map<String, String> categories);
}
