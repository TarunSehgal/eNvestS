package com.eNvestDetails.CalculationService;

import org.springframework.stereotype.Component;

@Component
public class GoalSeekCalculator {

	public double getAnnuityDueRequiredCashFlow(double targetValue, double years, double interestRate, String inputTenor) throws Exception
	{
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	return targetValue / ((Math.pow(1+effectiveRate, noOfPayments)-1)/effectiveRate);
	}
	
	public Response getAnnuityDueRequiredYears(double targetValue, double cashFlow, double interestRate, String inputTenor) throws Exception
	{
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
		double periods = Math.log((targetValue *effectiveRate / cashFlow) +1)/Math.log(1+effectiveRate);
		double noOfYears = CalcHelper.convertNoOfPaymentsToYears(periods, tenor);
		double principle = cashFlow * periods;
	return new Response(principle, targetValue,targetValue - principle, noOfYears);
	}
	
	public double getAnnuityPayout(double initialAmount,double years, double interestRate, String inputTenor) throws Exception
	{
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		double payment = 
		         (initialAmount*effectiveRate) / 
		            (1-Math.pow(1+effectiveRate, -noOfPayments));
	return payment;
	}
}
