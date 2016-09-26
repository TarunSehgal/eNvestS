package com.envest.services.components.util.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Account {
	public AccountType accountType;
	public int noOfAccounts;
	public String bankName;
	public int accountNumber;
	double apy;
	
	protected Account(String bankName, int accountNumber, int noOfAccounts, AccountType accountType, double apy)
	{
		this.accountType = accountType;
		this.noOfAccounts = noOfAccounts;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.apy = apy;
	}
	
	public abstract List<Account> getAccounts();
}
