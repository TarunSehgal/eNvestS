package com.envest.services.components.util.account;

public class DepositoryBankAccount extends Account {
	
	private double minBalanceRequirements;
	private double minBalancePenalty;
	private String routingNumber;

	public DepositoryBankAccount(String bankName, String accountNumber,
			AccountType accountType, double apy, String routingNumber,
			 double minBalanceRequirements,
			double minBalancePenalty) {
		super(bankName, accountNumber, accountType, apy);		
		this.minBalancePenalty = minBalancePenalty;
		this.minBalanceRequirements = minBalanceRequirements;
		this.routingNumber = routingNumber;
	}

	public DepositoryBankAccount() {

	}

	public double getMinBalanceRequirements() {
		return minBalanceRequirements;
	}

	public void setMinBalanceRequirements(double minBalanceRequirements) {
		this.minBalanceRequirements = minBalanceRequirements;
	}

	public double getMinBalancePenalty() {
		return minBalancePenalty;
	}

	public void setMinBalancePenalty(double minBalancePenalty) {
		this.minBalancePenalty = minBalancePenalty;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
}
