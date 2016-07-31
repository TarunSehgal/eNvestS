package com.eNvestDetails.util.Product;

import java.util.ArrayList;
import java.util.List;

public class HighYieldProduct extends Product {
	public double principle;
	public double interestRate;
	public String compoundingTenor;
	public List<TenorMaturityResponse> maturityLadder = new ArrayList<TenorMaturityResponse>();
public HighYieldProduct(String bankName, double interestRate, int id)
{
	super(bankName, interestRate, id, ProductType.GoalSeek);
}
}
