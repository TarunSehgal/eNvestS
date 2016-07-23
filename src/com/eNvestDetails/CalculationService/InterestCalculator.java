package com.eNvestDetails.CalculationService;

import org.springframework.stereotype.Component;

@Component
public class InterestCalculator {
	public Response CalculateInterest(double principle, String inputTenor, double annualInterestRatePercentage, double years ) throws Exception
	{
		double annualInterestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(annualInterestRate, tenor);
		
		double maturity = principle * Math.pow(1 + effectiveRate, noOfPayments);		
		return new Response(principle, maturity,maturity - principle , years, annualInterestRatePercentage);
	}
}
