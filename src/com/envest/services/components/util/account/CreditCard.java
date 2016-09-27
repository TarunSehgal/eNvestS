package com.envest.services.components.util.account;

public class CreditCard extends Account {
	double currentBalance;
	double creditLimit;
	double avgPayment;
	
	protected CreditCard(String bankName, int accountNumber, double apy, double totalBalance,
	double currentBalance,double creditLimit,double avgPayment)
	{
		super(bankName, accountNumber, AccountType.creditcard, apy);
		this.currentBalance = currentBalance;
		this.creditLimit = creditLimit;
		this.avgPayment = avgPayment;
	}
}
