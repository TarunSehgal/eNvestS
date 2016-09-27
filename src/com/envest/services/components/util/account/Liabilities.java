package com.envest.services.components.util.account;

import java.util.ArrayList;
import java.util.List;

public class Liabilities {
	AccountType type;
	int noOfAccounts;
	double totalBalance;

	List<Account> accounts = new ArrayList<Account>();

	public int getNoOfAccounts()
	{
		return accounts.size();
	}

	public void addAccount(Account account)
	{
		accounts.add(account);
	}
}
