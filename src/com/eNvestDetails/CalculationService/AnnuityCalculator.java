package com.eNvestDetails.CalculationService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.util.UserServiceUtil;

@Component
public class AnnuityCalculator {
	
	public Response CalculateOrdinaryAnnity(double cashFlow, double years, double interestRate, String inputTenor) throws Exception
	{
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	double maturity = cashFlow * ((Math.pow(1+effectiveRate, noOfPayments)-1)/effectiveRate);
	
	double principle = cashFlow * noOfPayments;
	return new Response(principle, maturity,maturity - principle,years);
	}
	
	public double CalculateOrdinaryAnnityPV(double cashFlow, double years, double interestRate, String inputTenor) throws Exception
	{
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	return cashFlow * ((1-Math.pow(1+effectiveRate, -noOfPayments))/effectiveRate);
	}
	
	public Response CalculateDueAnnity(double cashFlow, double years, double interestRate, String inputTenor) throws Exception
	{
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	double maturity = cashFlow * ((Math.pow(1+effectiveRate, noOfPayments)-1)/effectiveRate)*(1+effectiveRate);
	
	double principle = cashFlow * noOfPayments;
	return new Response(principle, maturity,maturity - principle,years);
	}
	
	public double CalculateDueAnnityPV(double cashFlow, double years, double interestRate, String inputTenor) throws Exception
	{
		Tenor tenor = Validator.validateCoumpoundingTenor(inputTenor);
		double noOfPayments = CalcHelper.convertYearsToNoOfPayments(years, tenor);
		double effectiveRate = CalcHelper.convertInterestRateToEffectiveRate(interestRate, tenor);
		
	return cashFlow * ((1-Math.pow(1+effectiveRate, -noOfPayments))/effectiveRate)*(1+effectiveRate);
	}		
}
