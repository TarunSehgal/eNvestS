package com.eNvestDetails.util.Product;

public class MIProduct extends Product {
public double monthlyCashFlow;
public double noOfYears;
public double minimumTenureYears;
public double maxTenureYears;
public double interestRate;
public String compoundingTenor;
public double preMatureClosureCharges;
public double emiDefaultCharges;
public double valueAtMaturity;
public double interestEarned;
public MIProduct(String bankName, double interestRate, int id)
{
	productType = ProductType.MonthlyInvestmentPlan;
	productId = id;
	this.bankName = bankName;
	this.interestRate = interestRate;
}
}
