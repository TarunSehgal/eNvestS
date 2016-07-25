package com.eNvestDetails.util.Calculation;

import org.springframework.stereotype.Component;

@Component
public class GoalSeekCalculator {

	public PayoutResponse getAnnuityDueRequiredCashFlow(double targetValue, double years, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double interestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	double payment = targetValue / ((Math.pow(1+effectiveRate, noOfPayments)-1)/effectiveRate);
	
	double interest = targetValue - payment*noOfPayments;
return new PayoutResponse(0, payment, interest, years, annualInterestRatePercentage );
	}
	
	public Response getAnnuityDueRequiredYears(double targetValue, double cashFlow, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double interestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
		double periods = Math.log((targetValue *effectiveRate / cashFlow) +1)/Math.log(1+effectiveRate);
		double noOfYears = CalcHelper.convertNoOfPaymentsToYears(periods, tenor);
		double principle = cashFlow * periods;
	return new Response(principle, targetValue,targetValue - principle, noOfYears, annualInterestRatePercentage);
	}
}
