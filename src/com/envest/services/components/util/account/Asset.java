package com.envest.services.components.util.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class Asset extends Account {
	int routingNumber;
	double totalBalance;
	
	protected Asset(String bankName, int accountNumber, int noOfAccounts, AccountType accountType, double apy, int routingNumber, double totalBalance)
	{
		super(bankName, accountNumber, noOfAccounts, accountType, apy);
		this.routingNumber = routingNumber;
		this.totalBalance = totalBalance;
	}
}
