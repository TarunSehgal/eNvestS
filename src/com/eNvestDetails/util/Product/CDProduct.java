package com.eNvestDetails.util.Product;

public class CDProduct extends Product{
	public double minimumAmount;
	public double principle;
	public double valueAtMaturity;
	public double maturityYears;
	public double interestEarned;
	public double interestRate;
	public String compoundingTenor;
public CDProduct(String bankName, double interestRate,int userProductId, int id, double maturityYears)
{
	super(bankName, interestRate,userProductId, id, ProductType.CertificateOfDeposit);
	this.maturityYears = maturityYears;
}
}
