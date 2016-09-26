package com.envest.services.components.util.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AutoLoan extends Loan {
	
	protected AutoLoan(String bankName, int accountNumber, int noOfAccounts, double apy,double balance,
	double loanAmount,String startDate,String maturityDate,	double monthlyPayment)
	{
		super(bankName, accountNumber, noOfAccounts,  AccountType.autoLoan, apy, balance, loanAmount, startDate, maturityDate, monthlyPayment);
	}

	@Override
	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		return null;
	}
}
