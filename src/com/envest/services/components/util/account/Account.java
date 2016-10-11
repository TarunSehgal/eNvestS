package com.envest.services.components.util.account;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Account {
	private AccountType accountType;
	private String bankName;
	private String accountNumber;
	private double apy;
	private String accountId;
	private String item;
	private double balance;
	
	public Account(String bankName, String accountNumber, AccountType accountType, double apy)
	{
		this.accountType = accountType;
		this.bankName = bankName;
		this.accountNumber = accountNumber;
		this.apy = apy;
	}
	
	public Account(){
		
	}

	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getApy() {
		return apy;
	}

	public void setApy(double apy) {
		this.apy = apy;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

 
}
