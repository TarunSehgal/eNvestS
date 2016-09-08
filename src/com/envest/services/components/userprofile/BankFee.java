package com.envest.services.components.userprofile;

import com.envest.services.response.TransactionDetail;

public class BankFee extends DataElement {
	
	private int overDraft;
	
	private int insufficientFunds;
	
	private int latePayment;
	
	private int foreignTransaction;

	public int getOverDraft() {
		return overDraft;
	}

	public void setOverDraft(int overDraft) {
		this.overDraft = overDraft;
	}

	public int getInsufficientFunds() {
		return insufficientFunds;
	}

	public void setInsufficientFunds(int insufficientFunds) {
		this.insufficientFunds = insufficientFunds;
	}

	public int getLatePayment() {
		return latePayment;
	}

	public void setLatePayment(int latePayment) {
		this.latePayment = latePayment;
	}

	public int getForeignTransaction() {
		return foreignTransaction;
	}

	public void setForeignTransaction(int foreignTransaction) {
		this.foreignTransaction = foreignTransaction;
	}  	
	
	@Override
	public void extractAdditionalInformation(TransactionDetail transaction) {
		String categoryId = transaction.getCategoryId();
		
		switch (categoryId) {
		
		case "10001000":
			overDraft++;
			break;
			
		case "10007000": 
			insufficientFunds++;
			break;
			
		case "10003000": 
			latePayment++;
			break;
			
		case "10005000": 
			foreignTransaction++;
			break;
		
		default:						
			break;
		}	
		
	}

}
