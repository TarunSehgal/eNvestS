package com.eNvestDetails.CalculationService;

import org.springframework.stereotype.Component;

@Component
public class InterestCalculator {
	public Response CalculateInterest(double principle, int tenor, double annualInterestRatePercentage, double years )
	{
		double annualInterestRate = annualInterestRatePercentage/100;
		double maturity = principle * Math.pow(1 + (annualInterestRate / tenor), tenor * years);		
		return new Response(principle, maturity,maturity - principle , years, annualInterestRatePercentage);
	}
}
