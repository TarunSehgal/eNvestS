package com.envest.services.components.util.Product;

import java.util.ArrayList;
import java.util.List;

public class HighYieldProduct extends Product {
	public double principle;
	public String compoundingTenor;
	public List<TenorMaturityResponse> maturityLadder = new ArrayList<TenorMaturityResponse>();
public HighYieldProduct(String bankName, double interestRate,int userProductId, int id)
{
	super(bankName, interestRate,userProductId, id, ProductType.HighYieldAccount);
}
}
