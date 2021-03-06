package com.envest.services.components.util.Product;

public class MIProduct extends Product {
public double monthlyCashFlow;
public double maturityYears;
public double minimumTenureYears;
public double maxTenureYears;
public String compoundingTenor;
public double preMatureClosureCharges;
public double emiDefaultCharges;
public double valueAtMaturity;
public double interestEarned;
public MIProduct(String bankName, double interestRate, int userProductId,int id)
{
	super(bankName, interestRate,userProductId, id, ProductType.MonthlyInvestmentPlan);
}
}
