package com.envest.services.components.util.account;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Account {
	public AccountType accountType;
	public String bankName;
	public int accountNumber;
	double apy;
	
	protected Account(String bankName, int accountNumber, AccountType accountType, double apy)
	{
		this.accountType = accountType;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.apy = apy;
	}
}
