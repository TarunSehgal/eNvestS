package com.eNvestDetails.TransferService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.TransactionsResponse;

@Component
public class PlaidToEnvestConverter implements IPlaidToEnvestConverter{
	@Autowired
	IPlaidAdapterFactory plaidAdapterFactory;

	@Override
	public UpdateTransactionResult convertTransactionResponse(TransactionsResponse response, String userBank, Map<String, String> categories) {
		
		List<TransactionDetail> transactions = plaidAdapterFactory.getPlaidTransactionAdapter().convertTransactionsToTransactionDetail(response.getTransactions());
		Map<String,UserInfo.Summary> summaries = plaidAdapterFactory.getPlaidTransactionAdapter().convertTransactionsToSummaries(response.getTransactions(), categories);
		List<AccountDetail> accountDetails = plaidAdapterFactory.getPlaidAccountAdapter().convertPlaidAccountsToAccountDetails(response.getAccounts(), userBank);
		// TODO Auto-generated method stub
		return new UpdateTransactionResult(accountDetails,transactions,summaries);
	}

	@Override
	public UserInfo convertInforesponseToUserinfo(InfoResponse response, String bank, String userId) {
		return plaidAdapterFactory.getPlaidInfoAdapter().convertToUserInfo(response, bank, userId);
	}

}
