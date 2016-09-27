package com.envest.services.components.util.account;

public class BankAccount extends Account {
			double balance;
			double minBalanceRequirements;
			double minBalancePenalty;
			int routingNumber;
	protected BankAccount(String bankName, int accountNumber, AccountType accountType, double apy, int routingNumber, double balance, double minBalanceRequirements, double minBalancePenalty)
	{
		super(bankName, accountNumber, accountType, apy);
		this.balance = balance;
		this.minBalancePenalty = minBalancePenalty;
		this.minBalanceRequirements = minBalanceRequirements;
		this.routingNumber = routingNumber;
	}
}
