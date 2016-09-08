package com.envest.servicegateways.plaid;

import java.util.List;
import java.util.Map;

import com.envest.services.response.AccountDetail;
import com.envest.services.response.TransactionDetail;
import com.envest.services.response.UserInfo;

public class UpdateTransactionResult {
public List<AccountDetail> accountDetails;
public List<TransactionDetail> transactionDetails;
public Map<String,UserInfo.Summary> summaryMap;

public UpdateTransactionResult(List<AccountDetail> accountDetails,List<TransactionDetail> transactionDetails,Map<String,UserInfo.Summary> summaryMap)
{
	this.accountDetails = accountDetails;
	this.transactionDetails = transactionDetails;
	this.summaryMap = summaryMap;
}
}
