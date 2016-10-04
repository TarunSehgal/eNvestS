package com.envest.services.components.util.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.envest.services.components.util.Product.Product;
import com.envest.services.components.util.Product.ProductType;

public class Accounts {
	ProductType type;
	int noOfAccounts;
	double totalBalance;

	Map<AccountType, List<Account>> accounts = new HashMap<AccountType, List<Account>>();

	public int getNoOfAccounts()
	{
		return accounts.size();
	}

	public void addAccount(Account account)
	{
		if(accounts.containsKey(account.accountType))
		{
		accounts.get(account.accountType).add(account);
		}else
		{
			accounts.put(account.accountType, new ArrayList<Account>());
			accounts.get(account.accountType).add(account);
		}
	}
	
	public List<Account> getAccounts(AccountType type)
	{
		if(accounts.containsKey(type))
		{
		return accounts.get(type);
		}
		return null;
	}
	
	public Collection<List<Account>> getAllAccounts()
	{
		return accounts.values();
	}
}
