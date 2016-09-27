package com.envest.services.components.util.account;

public class Cashflow {
	double L30DInflow, L90DInflow, L30DOutflow, L90DOutflow, monthyAvgInflow, monthlyAverageOutflow, L1YInterestEarned, L1YBankFees, avgCreditCardPayments;
	
	public Cashflow(double L30DInflow, double L90DInflow, double L30DOutflow, double L90DOutflow, double monthyAvgInflow, double monthlyAverageOutflow, double L1YInterestEarned, double L1YBankFees, double avgCreditCardPayments)
	{
		this.L30DInflow = L30DInflow; 
		this.L90DInflow = L90DInflow; 
		this.L30DOutflow = L30DOutflow; 
		this.L90DOutflow = L90DOutflow; 
		this.monthyAvgInflow = monthyAvgInflow; 
		this.monthlyAverageOutflow = monthlyAverageOutflow; 
		this.L1YInterestEarned = L1YInterestEarned; 
		this.L1YBankFees = L1YBankFees; 
		this.avgCreditCardPayments = avgCreditCardPayments;
			
	}
}
