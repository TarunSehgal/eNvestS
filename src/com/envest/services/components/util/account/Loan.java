package com.envest.services.components.util.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class Loan extends Liability {
	
	double balance;
	double loanAmount;
	String startDate;
	String maturityDate;
	double monthlyPayment;
	
	protected Loan(String bankName, int accountNumber, int noOfAccounts, AccountType accountType, double apy,double balance,
	double loanAmount,String startDate,String maturityDate,	double monthlyPayment)
	{
		super(bankName, accountNumber, noOfAccounts, accountType, apy);
		this.balance = balance;
		this.loanAmount = loanAmount;
		this.startDate = startDate;
		this.maturityDate = maturityDate;
		this.monthlyPayment = monthlyPayment;
	}
}
