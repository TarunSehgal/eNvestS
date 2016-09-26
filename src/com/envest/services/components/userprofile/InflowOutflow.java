/*package com.envest.services.components.userprofile;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.envest.services.response.TransactionDetail;

public class InflowOutflow extends DataElement{

	@Override
	public void extractPrimaryInformation(TransactionDetail transaction){		
		switch (getId()) {
		
		case "7": // outflow
			addIfNegative(getAmount(transaction));
			//setDates(getTransactionDate(transaction));
			break;
			
		case "4": // inflow
			addIfPositive(getAmount(transaction));
			//setDates(getTransactionDate(transaction));
			break;
			
		case "8": // outflow 30 days
			addAmountOfLastDays(transaction, 30, false);
			
			break;
			
		case "9": // outflow 90 days
			addAmountOfLastDays(transaction, 90, false);
			
			break;
			
		case "5": // inflow 30 days
			addAmountOfLastDays(transaction, 30, true);
			
			break;
			
		case "6": // inflow 90 days
			addAmountOfLastDays(transaction, 90, true);			
			break;
			
		default:						
			break;
		}	
		
	}

	private void addIfNegative(double amount){
		if(amount < 0.0){
			addAmount(amount);;
		}
	}
	
	private void addIfPositive(double amount){
		if(amount > 0.0){
			addAmount(amount);;
		}
	}
	
	private boolean isNegativeAmount(TransactionDetail transaction) {
		return getAmount(transaction) < 0.0;
	}
	
	private boolean isPositiveAmount(TransactionDetail transaction) {
		return getAmount(transaction) > 0.0;
	}
	
	
	
	private Date setAndGetInflowOutflowStartDate(TransactionDetail transaction){
		if(getStartDate() != null){
			return getStartDate();
		}	
		setStartDate(getTransactionDate(transaction));
		return getStartDate();		
	}
	
	private boolean isTransactionDateInRange(Date transactionDate, Date startDate, int days){
		return transactionDate.after(DateUtils.addDays(startDate, days * -1));
	}
	
	private void addAmountOfLastDays(TransactionDetail transaction,
			int days, boolean inflows) {
		if(shouldIncludeTransaction(transaction, inflows)){
			Date startDate = setAndGetInflowOutflowStartDate(transaction);
			Date transactionDate = getTransactionDate(transaction);
			
			if(isTransactionDateInRange(transactionDate, startDate, days)){
				addAmount(getAmount(transaction));			
				//setDates(getTransactionDate(transaction));
			}
			
		}
	}
	
	private boolean shouldIncludeTransaction(TransactionDetail transaction, boolean inflow){
		if(!inflow){
			return isNegativeAmount(transaction);
		}
		return isPositiveAmount(transaction);
	}
	
}
*/