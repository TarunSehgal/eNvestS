package com.envest.servicegateways.plaid;

import java.util.List;

import com.envest.services.response.AccountDetail;
import com.plaid.client.response.Account;

public interface IPlaidAccountAdapter {
	public List<AccountDetail> convertPlaidAccountsToAccountDetails(List<Account> acc,String bank);
}
