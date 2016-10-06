package com.envest.services.components.util.account;

public class CertificateOfDepositAccount extends Account {
			double principal;
			double maturity;
			String startDate;
			String maturityDate;
			int routingNumber;

	protected CertificateOfDepositAccount(String bankName, int accountNumber, AccountType accountType, double apy, int routingNumber, double principal, double maturity, String maturityDate, String startDate)
	{
		super(bankName, accountNumber, accountType, apy);
		this.principal = principal;
		this.maturity = maturity;
		this.maturityDate = maturityDate;
		this.startDate = startDate;
		this.routingNumber = routingNumber;
	}
}
