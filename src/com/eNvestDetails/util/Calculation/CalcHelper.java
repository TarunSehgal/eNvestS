package com.eNvestDetails.util.Calculation;

public class CalcHelper {
	
	public static double convertYearsToNoOfPayments(double years, Tenor tenor) throws Exception
	{
		switch(tenor.tenorType)
		{
		case Annual:
			return years * tenor.numericValue;
		case Monthly:
			return years * 12 /tenor.numericValue;
		case Daily:
			return years * 365/tenor.numericValue; //daycount need to be considered here
			default:
				throw new Exception("Tenor type not valid - "+tenor.tenorType);
		}
	}
	
	public static double convertNoOfPaymentsToYears(double noOfpayments, Tenor tenor) throws Exception
	{
		switch(tenor.tenorType)
		{
		case Annual:
			return noOfpayments / tenor.numericValue;
		case Monthly:
			return noOfpayments * tenor.numericValue/12;
		case Daily:
			return noOfpayments * tenor.numericValue/365; //daycount need to be considered here
			default:
				throw new Exception("Tenor type not valid - "+tenor.tenorType);
		}
	}
	
	public static double convertInterestRateToEffectiveRate(double interestRate, Tenor tenor) throws Exception
	{
		switch(tenor.tenorType)
		{
		case Annual:
			return interestRate / tenor.numericValue;
		case Monthly:
			return interestRate * tenor.numericValue/12;
		case Daily:
			return interestRate * tenor.numericValue/365; //daycount need to be considered here
		default:
			throw new Exception("Tenor type not valid - "+tenor.tenorType);
		}
	}

}
