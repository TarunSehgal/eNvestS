package com.envest.services.components.util.Product;

public class GoalSeekProduct extends Product {
	public double principle;
	public double maturityYears;
	public double cashFlow;
	public String compoundingTenor;
	public GoalSeekProduct(String bankName, double interestRate,int userProductId, int id)
	{
		super(bankName, interestRate,userProductId, id, ProductType.GoalSeek);
	}
}
