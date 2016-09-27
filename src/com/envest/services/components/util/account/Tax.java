package com.envest.services.components.util.account;

public class Tax {
	double taxRefundAmount, taxPaidLastYear;
	String refundDate;
			
			public Tax(double taxRefundAmount,double taxPaidLastYear, String refundDate)
			{
	this.taxRefundAmount = taxRefundAmount;
	this.taxPaidLastYear = taxPaidLastYear;
	this.refundDate = refundDate;
			}
}
