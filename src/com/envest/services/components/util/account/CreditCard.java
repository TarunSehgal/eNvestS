package com.envest.services.components.util.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CreditCard extends Liability {
	double totalBalance;
	double currentBalance;
	double creditLimit;
	double avgPayment;
	
	protected CreditCard(String bankName, int accountNumber, int noOfAccounts, double apy, double totalBalance,
	double currentBalance,double creditLimit,double avgPayment)
	{
		super(bankName, accountNumber, noOfAccounts, AccountType.creditcard, apy);
		this.totalBalance = totalBalance;
		this.currentBalance = currentBalance;
		this.creditLimit = creditLimit;
		this.avgPayment = avgPayment;
	}

	@Override
	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		return null;
	}
}
