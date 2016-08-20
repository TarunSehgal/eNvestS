package com.eNvestDetails.TransferService;

import java.util.List;
import java.util.Map;

import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;

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
