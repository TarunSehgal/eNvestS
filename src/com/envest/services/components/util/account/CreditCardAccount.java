package com.envest.services.components.util.account;

public class CreditCardAccount extends Account {
	double currentBalance;
	double creditLimit;
	double avgPayment;
	
	public CreditCardAccount(){
		
	}
	
	public CreditCardAccount(String bankName, String accountNumber, double apy, double totalBalance,
	double currentBalance,double creditLimit,double avgPayment)
	{
		super(bankName, accountNumber, AccountType.creditcard, apy);
		this.currentBalance = currentBalance;
		this.creditLimit = creditLimit;
		this.avgPayment = avgPayment;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public double getAvgPayment() {
		return avgPayment;
	}

	public void setAvgPayment(double avgPayment) {
		this.avgPayment = avgPayment;
	}
	
	
}
