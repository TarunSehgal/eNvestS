package com.envest.services.response;

public class AssetLiabilityAccount {
	
	private String type;
	private int totalAccounts;
	private Double totalBalance;
	private AccountDetail accounts;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTotalAccounts() {
		return totalAccounts;
	}
	public void setTotalAccounts(int totalAccounts) {
		this.totalAccounts = totalAccounts;
	}
	public Double getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}
	public AccountDetail getAccounts() {
		return accounts;
	}
	public void setAccounts(AccountDetail accounts) {
		this.accounts = accounts;
	}

}
