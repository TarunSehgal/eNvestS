package com.eNvestDetails.UserProfileData.DataElement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.util.CommonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties( { "allowedCategory" })
@JsonInclude(JsonInclude.Include.NON_NULL)

public class DataElement {
	
	String type;
	String subType;
	
	@JsonFormat(pattern="MM-dd-yyyy")
	Date startDate;
	
	@JsonFormat(pattern="MM-dd-yyyy")
	Date endDate;
	
	double amount;
	String account;
	String id;
	
	
	private CommonUtil commonUtil = new CommonUtil();
	
	private ConfigFactory config = new ConfigFactory();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	List<String> allowedCategory;
	
	public List<String> getAllowedCategory() {
		return allowedCategory;
	}
	public void setAllowedCategory(List<String> allowedCategory) {
		this.allowedCategory = allowedCategory;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	} 
	
	public void calculateDataelement(TransactionDetail transaction, String categoryHierarchy) {
		try {			
			if(isAllowedCategory(transaction.getCategoryId(), allowedCategory)){	
				setDates();
				extractPrimaryInformation(transaction);					
				extractAdditionalInformation(transaction);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isAllowedCategory(String category, List<String> handleCategory){		
		boolean isAllowed = false;
		
		for(String validCategory : handleCategory){
			if(validCategory.equals("All")){
				isAllowed = true;
				break;
			}else if (validCategory.contains("*")){
				String value = validCategory.replace("*", "");
				if(category != null && category.startsWith(value)){
					isAllowed = true;
					break;
				}
			}else if(validCategory.equals(category)){
				isAllowed = true;
				break;
			}			
		}
		return isAllowed;
	}
	
	public void extractPrimaryInformation(TransactionDetail transaction){
		addAmount(getAmount(transaction));
		//setDates(getTransactionDate(transaction));
		
	}
	
	public void setDates(){		
		setStartDateIfNull(new Date());
		setEndDate(new Date(commonUtil.getGte(config.getResultString("transactionMonthRange"))));
	}
	
	public void extractAdditionalInformation(TransactionDetail transaction) {		
	}
	
	public void addAmount(double amt){
		amount = amount + amt;
	}
	
	public void setDates(Date transactionDate){
		setStartDateIfNull(transactionDate);
		endDate = transactionDate;
	}
	
	public void setStartDateIfNull(Date transactionDate){
		if(null == startDate){
			startDate = transactionDate;
		}		
	}
	
	public void setEndDateIfNull(Date transactionDate){
		if(null == endDate){
			endDate = transactionDate;
		}		
	}
	
	public Date getTransactionDate(TransactionDetail transaction) {
		return transaction.getDate().toDate();
	}
	
	public double getAmount(TransactionDetail transaction){
		return transaction.getAmount();
	}

}
