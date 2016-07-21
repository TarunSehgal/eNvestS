package com.eNvestDetails.CalculationService;

public class Response {
	
	public double principle=0;
	public double maturity=0;
	public double interest=0;
	public double noOfYears=0;
	
	public Response(double principle, double maturity, double interest, double years)
	{
		this.principle = principle;
		this.maturity = maturity;
		this.interest = interest;
		this.noOfYears = years;		
	}
}
