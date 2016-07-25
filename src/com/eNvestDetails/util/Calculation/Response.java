package com.eNvestDetails.util.Calculation;

public class Response {
	
	public double principle=0;
	public double maturity=0;
	public double interest=0;
	public double noOfYears=0;
	public double interestRatePercentage=0;
	
	public Response(double principle, double maturity, double interest, double years, double interestRatePercentage)
	{
		this.principle = principle;
		this.maturity = maturity;
		this.interest = interest;
		this.noOfYears = years;	
		this.interestRatePercentage = interestRatePercentage;
	}
}
