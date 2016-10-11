package com.envest.services.components.util.account;

import java.util.List;

public class AccountList {
	private AccountType accountType;
	private int noOfAccounts;
	private double totalBalance;
	
	List<Account> accounts = null;

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public int getNoOfAccounts() {
		return noOfAccounts;
	}

	public void setNoOfAccounts(int noOfAccounts) {
		this.noOfAccounts = noOfAccounts;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	

}
