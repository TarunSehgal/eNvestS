package com.eNvestDetails.TransferService;

import java.util.List;

import com.eNvestDetails.Response.AccountDetail;
import com.plaid.client.response.Account;

public interface IPlaidAccountAdapter {
	public List<AccountDetail> convertPlaidAccountsToAccountDetails(List<Account> acc,String bank);
}
