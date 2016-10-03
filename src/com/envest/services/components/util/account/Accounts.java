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

	Map<ProductType, List<Product>> accounts = new HashMap<ProductType, List<Product>>();

	public int getNoOfAccounts()
	{
		return accounts.size();
	}

	public void addAccount(Product account)
	{
		if(accounts.containsKey(account.productType))
		{
		accounts.get(account.productType).add(account);
		}else
		{
			accounts.put(account.productType, new ArrayList<Product>());
			accounts.get(account.productType).add(account);
		}
	}
	
	public List<Product> getAccounts(ProductType type)
	{
		if(accounts.containsKey(type))
		{
		return accounts.get(type);
		}
		return null;
	}
	
	public Collection<List<Product>> getAllAccounts()
	{
		return accounts.values();
	}
}
