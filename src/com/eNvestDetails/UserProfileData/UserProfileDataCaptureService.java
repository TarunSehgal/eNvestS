package com.eNvestDetails.UserProfileData;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.UserProfileData.DataElement.DataElement;

@Component
public class UserProfileDataCaptureService {
	
	private CategoryToProfileElementFactory profileDataMapping = new CategoryToProfileElementFactory();
	
	private List<AccountDetail> excludeCreditCardAccounts;
	
	private String excludeAccountTypes = "credit";
	
	public UserProfileDataCaptureService(){
		
	}
	
	public UserProfileDataCaptureService(List<AccountDetail> excludeCreditCardAccountList){
		excludeCreditCardAccounts = excludeCreditCardAccountList;
	}
	
	public void processTransaction(TransactionDetail transaction
			,String categoryHierarchy){
			
		try{
			if(isAllowedTransaction(transaction.getAccountId())){
				if(null != categoryHierarchy){
					String[] split = categoryHierarchy.split(",");
					List<DataElement> dataElementList = profileDataMapping.
							getDataElementList(split[0]);
					if(null != dataElementList){
						for(DataElement bean : dataElementList){
							bean.calculateDataelement(transaction, categoryHierarchy);
						}
					}
					dataElementList = profileDataMapping.
							getDataElementList("InflowOutflow");
					if(null != dataElementList){
						for(DataElement bean : dataElementList){
							bean.calculateDataelement(transaction, categoryHierarchy);
						}
					}					
				}
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public List<DataElement> getData(){
		return profileDataMapping.getProfileData();
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
	
	

}
