package com.eNvestDetails.util.Calculation;

public class PayoutResponse {
	
	public double principle=0;
	public double cashFlow=0;
	public double interest=0;
	public double noOfYears=0;
	public double interestRatePercentage=0;
	
	public PayoutResponse(double principle, double cashFlow, double interest, double years, double interestRatePercentage)
	{
		this.principle = principle;
		this.cashFlow = cashFlow;
		this.interest = interest;
		this.noOfYears = years;	
		this.interestRatePercentage = interestRatePercentage;
	}
}
