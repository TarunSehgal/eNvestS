package com.eNvestDetails.Products;

public class CDProduct extends Product{
	public double minimumAmount;
	public double principle;
	public double valueAtMaturity;
	public double maturityYears;
	public double interestEarned;
	public double interestRate;
	public String compoundingTenor;
public CDProduct(String bankName, double interestRate, int id)
{
	productType = ProductType.CertificateOfDeposit;
	this.productId = id;
	this.bankName = bankName;
	this.interestRate = interestRate;
}
}
