package com.eNvestDetails.CalculationService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.util.UserServiceUtil;

@Component
public class AnnuityCalculator {
	
	public Response CalculateOrdinaryAnnity(double cashFlow, double years, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double interestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	double maturity = cashFlow * ((Math.pow(1+effectiveRate, noOfPayments)-1)/effectiveRate);
	
	double principle = cashFlow * noOfPayments;
	return new Response(principle, maturity,maturity - principle,years, annualInterestRatePercentage);
	}
	
	public double CalculateOrdinaryAnnityPV(double cashFlow, double years, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double interestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	return cashFlow * ((1-Math.pow(1+effectiveRate, -noOfPayments))/effectiveRate);
	}
	
	public Response CalculateDueAnnity(double cashFlow, double years, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double interestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	double maturity = cashFlow * ((Math.pow(1+effectiveRate, noOfPayments)-1)/effectiveRate)*(1+effectiveRate);
	
	double principle = cashFlow * noOfPayments;
	return new Response(principle, maturity,maturity - principle,years, annualInterestRatePercentage);
	}
	
	public double CalculateDueAnnityPV(double cashFlow, double years, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double interestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	return cashFlow * ((1-Math.pow(1+effectiveRate, -noOfPayments))/effectiveRate)*(1+effectiveRate);
	}	
	
	public PayoutResponse getAnnuityPayout(double initialAmount,double years, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double annualInterestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(annualInterestRate, tenor);
		double payment = 
		         (initialAmount*effectiveRate *Math.pow(1+effectiveRate, noOfPayments)) / 
		            (Math.pow(1+effectiveRate, noOfPayments)-1);
		double interest = payment*noOfPayments - initialAmount;
	return new PayoutResponse(initialAmount, payment, interest, years, annualInterestRatePercentage );
	}
	
	public PayoutResponse getAnnuityPayoutYears(double initialAmount,double payout, double annualInterestRatePercentage, String inputTenor) throws Exception
	{
		double annualInterestRate = annualInterestRatePercentage/100;
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(annualInterestRate, tenor);
		double noOfPayments = Math.log(1/(1-(initialAmount*effectiveRate/payout)))/Math.log(1+effectiveRate);
		         
		double years = CalcHelper.convertNoOfPaymentsToYears(noOfPayments, tenor);
		double interest = payout*noOfPayments - initialAmount;
	return new PayoutResponse(initialAmount, payout, interest, years, annualInterestRatePercentage );
	}
}
