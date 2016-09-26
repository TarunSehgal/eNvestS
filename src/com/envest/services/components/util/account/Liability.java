package com.envest.services.components.util.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class Liability extends Account {
	protected Liability(String bankName, int accountNumber, int noOfAccounts, AccountType accountType, double apy)
	{
		super(bankName, accountNumber, noOfAccounts, accountType, apy);
	}
}
