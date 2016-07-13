package com.eNvestDetails.CalculationService;

public class InterestCalculator {
	public double CalculateInterest(double principle, int tenor, double annualInterestRate, double years )
	{
		double amount = principle * Math.pow(1 + (annualInterestRate / tenor), tenor * years);		
		return amount - principle;
	}
	
	public double CalculateMaturity(double principle, int tenor, double annualInterestRate, double years )
	{
		return principle * Math.pow(1 + (annualInterestRate / tenor), tenor * years);		
	}
}
