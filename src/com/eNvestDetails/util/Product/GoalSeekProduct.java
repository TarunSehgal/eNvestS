package com.eNvestDetails.util.Product;

public class GoalSeekProduct extends Product {
	public double principle;
	public double noOfYears;
	public double cashFlow;
	public double interestRate;
	public String compoundingTenor;
	public GoalSeekProduct(String bankName, double interestRate, int id)
	{
		super(bankName, interestRate, id, ProductType.GoalSeek);
	}
}
