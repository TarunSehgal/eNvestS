package com.envest.services.components.util.Product;

public class TenorMaturityResponse {
	public int maturityYear;
	public double maturityValue;
	public double interestEarned;
	public double principle;
	public TenorMaturityResponse(int maturityYear, double maturityValue, double interestEarned, double principle)
	{
		this.maturityValue = maturityValue;
		this.maturityYear = maturityYear;
		this.interestEarned = interestEarned;
		this.principle = principle;
	}
}
