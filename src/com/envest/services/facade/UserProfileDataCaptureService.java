package com.envest.services.facade;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.envest.services.components.CategoryToProfileElementFactory;
import com.envest.services.components.userprofile.DataElement;
import com.envest.services.response.AccountDetail;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.ProfileResponse;
import com.envest.services.response.TransactionDetail;

@Component
public class UserProfileDataCaptureService {
	
	private CategoryToProfileElementFactory profileDataMapping = new CategoryToProfileElementFactory();
	
	private List<AccountDetail> excludeCreditCardAccounts;
	
	private String excludeAccountTypes = "credit";
	
	private Date profileStartDate;
	
	private Date profileEndDate;
	
	public Date getProfileStartDate() {
		return profileStartDate;
	}

	public void setProfileStartDate(Date profileStartDate) {
		this.profileStartDate = profileStartDate;
	}

	public Date getProfileEndDate() {
		return profileEndDate;
	}

	public void setProfileEndDate(Date profileEndDate) {
		this.profileEndDate = profileEndDate;
	}

	public UserProfileDataCaptureService(){
		
	}
	
	public UserProfileDataCaptureService(List<AccountDetail> excludeCreditCardAccountList){
		excludeCreditCardAccounts = excludeCreditCardAccountList;
		profileStartDate = new Date();		
	}
	
	public void processTransaction(TransactionDetail transaction
			,String categoryHierarchy){
			
		try{
			setProfileEndDate(transaction.getDate().toDate());
			if(isAllowedTransaction(transaction.getAccountId())){
				if(null != categoryHierarchy){
					calculateDataElements(transaction, categoryHierarchy, categoryHierarchy.split(",")[0]);
					calculateDataElements(transaction, categoryHierarchy, "InflowOutflow");										
				}
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public List<DataElement> getData(){
		return profileDataMapping.getProfileData();
	}
	
	public EnvestResponse getProfileResponse(){
		ProfileResponse response = new ProfileResponse();
		response.setStartDate(getProfileStartDate());
		response.setEndDate(getProfileEndDate());
		response.setProfile(getData());
		return response;
	}
	
	public boolean isAllowedTransaction(String accountID){		
		boolean isAllowed = true;
		
		for(AccountDetail acc : excludeCreditCardAccounts){
			if(accountID.equals(acc.getAccountId())){
				isAllowed = excludeAccountTypes.contains(acc.getType()) ? false : true;
				break;
			}
		}
		return isAllowed;
	}
	
	private void calculateDataElements(TransactionDetail transaction
			,String categoryHierarchy, String elementId)
	{
		List<DataElement> dataElementList = profileDataMapping.getDataElementList(elementId);
		if(null != dataElementList){
			for(DataElement bean : dataElementList){
				bean.calculateDataelement(transaction, categoryHierarchy);
			}
		}
	}
	
	private void setEndDateIfNull(Date date){
		if(null == profileEndDate){
			profileEndDate = date;
		}
	}
	

}
