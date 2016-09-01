package com.eNvestDetails.UserProfile;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.dto.UserProfileDataDTO;

public class Income extends UserProfileDataElement{

	@Override
	protected void extractPrimaryInformation(UserProfileDataDTO userProfile, TransactionDetail transaction){
		switch (userProfile.getId()) {
		
		case "10": // inflow
			addIfNegative(userProfile, getAmount(transaction));
			break;
			
		case "11": // outflow
			addIfPositive(userProfile, getAmount(transaction));
			break;
			
		case "15": // outflow 30 days
			addAmountOfLastDays(transaction, userProfile, 30, false);
			break;
			
		case "16": // outflow 90 days
			addAmountOfLastDays(transaction, userProfile, 90, false);
			break;
			
		case "17": // inflow 30 days
			addAmountOfLastDays(transaction, userProfile, 30, true);
			break;
			
		case "18": // inflow 90 days
			addAmountOfLastDays(transaction, userProfile, 90, true);
			break;
			
		default:						
			break;
		}	
		setDates(userProfile,getTransactionDate(transaction));
	}

	private void addIfNegative(UserProfileDataDTO userProfileDto, double amount){
		if(amount < 0.0){
			addAmount(userProfileDto, amount);;
		}
	}
	
	private void addIfPositive(UserProfileDataDTO userProfileDto, double amount){
		if(amount > 0.0){
			addAmount(userProfileDto, amount);;
		}
	}
	
	private boolean isNegativeAmount(TransactionDetail transaction) {
		return getAmount(transaction) < 0.0;
	}
	
	private boolean isPositiveAmount(TransactionDetail transaction) {
		return getAmount(transaction) > 0.0;
	}
	
	
	
	private Date setAndGetInflowOutflowStartDate(UserProfileDataDTO userProfileDto, TransactionDetail transaction){
		if(userProfileDto.getInflowOutflowStartDate() != null)
			return userProfileDto.getInflowOutflowStartDate();
		
		userProfileDto.setInflowOutflowStartDate(getTransactionDate(transaction));
		return userProfileDto.getInflowOutflowStartDate();		
	}
	
	private boolean isTransactionDateInRange(Date transactionDate, Date startDate, int days){
		return transactionDate.after(DateUtils.addDays(startDate, days * -1));
	}
	
	private void addAmountOfLastDays(TransactionDetail transaction,
			UserProfileDataDTO dto, int days, boolean inflows) {
		if(shouldIncludeTransaction(transaction, inflows)){
			Date startDate = setAndGetInflowOutflowStartDate(dto, transaction);
			Date transactionDate = getTransactionDate(transaction);
			
			if(isTransactionDateInRange(transactionDate, startDate, days)){
				addAmount(dto, getAmount(transaction));																
			}
		}
	}
	
	private boolean shouldIncludeTransaction(TransactionDetail transaction, boolean inflow){
		if(inflow){
			return isNegativeAmount(transaction);
		}
		return isPositiveAmount(transaction);
	}
	
	@Override
	public List<UserProfileDataDTO> returnDataList() {		
		return null;
	}
}
