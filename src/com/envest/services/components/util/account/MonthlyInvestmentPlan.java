package com.envest.services.components.util.account;

public class MonthlyInvestmentPlan extends Account {
	double monthlyInvestment;
	double maturity;
	String startDate;
	String maturityDate;
	int routingNumber;

protected MonthlyInvestmentPlan(String bankName, int accountNumber, AccountType accountType, double apy, int routingNumber, double monthlyInvestment, double maturity, String maturityDate, String startDate)
{
super(bankName, accountNumber, accountType, apy);
this.monthlyInvestment = monthlyInvestment;
this.maturity = maturity;
this.maturityDate = maturityDate;
this.startDate = startDate;
this.routingNumber = routingNumber;
}
}
