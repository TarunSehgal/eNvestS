package com.envest.services.components.util.account;

public class LoanAccount extends Account {
	
	double balance;
	double loanAmount;
	String startDate;
	String maturityDate;
	double monthlyPayment;
	
	protected LoanAccount(String bankName, String accountNumber, AccountType accountType, double apy,double balance,
	double loanAmount,String startDate,String maturityDate,	double monthlyPayment)
	{
		super(bankName, accountNumber, accountType, apy);
		this.balance = balance;
		this.loanAmount = loanAmount;
		this.startDate = startDate;
		this.maturityDate = maturityDate;
		this.monthlyPayment = monthlyPayment;
	}
}
