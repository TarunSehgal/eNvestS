package com.eNvestDetails.UserProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.DAL.DTO.UserProfileDataDTO;
import com.eNvestDetails.Response.TransactionDetail;

public abstract class UserProfileDataElement {
	
	private Map<String,UserProfileDataDTO> profileDataMap = new HashMap<String,UserProfileDataDTO>(10);
	
	@Autowired
	private UserProfileFactory profileFactory = new UserProfileFactory();
	
	public Map<String, UserProfileDataDTO> getProfileDataMap() {
		return profileDataMap;
	}

	public void initializeProfileMap() {
		profileDataMap = new HashMap<String,UserProfileDataDTO>(10);
	}
	
	protected List<String> captureElement = null;
	
	public List<String> getCaptureElement() {
		return captureElement;
	}

	public void setCaptureElement(List<String> captureElement) {
		this.captureElement = captureElement;
	}		


	public void calculateDataelement(TransactionDetail transaction, String categoryHierarchy) {
		try {			
			for (String captureDTO : captureElement) {
				UserProfileDataDTO userProfileDto = getAndInsertUserProfile(profileFactory, captureDTO);
				
				if(isAllowedCategory(transaction.getCategoryId(), userProfileDto.getPlaidCategory())){					
					extractPrimaryInformation(userProfileDto, transaction);					
					extractAdditionalInformation(userProfileDto, transaction);
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserProfileDataDTO> getData(){
		return  new ArrayList<UserProfileDataDTO>(profileDataMap.values());	
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
	
	private UserProfileDataDTO getAndInsertUserProfile(UserProfileFactory profileFactory, String captureDto){
		UserProfileDataDTO userProfile = getProfileDataMap().get(captureDto);
		if (userProfile == null) {
			userProfile = profileFactory.getProfileDTO(captureDto);
			getProfileDataMap().put(captureDto, userProfile);
		}
		return userProfile;
	}
	
	protected void extractPrimaryInformation(UserProfileDataDTO userProfileDto, TransactionDetail transaction){
		addAmount(userProfileDto, getAmount(transaction));
		setDates(userProfileDto,getTransactionDate(transaction));
	}
	
	protected void extractAdditionalInformation(UserProfileDataDTO userProfileDto, TransactionDetail transaction) {		
	}
	
	protected void addAmount(UserProfileDataDTO userProfileDto, double amount){
		userProfileDto.setAmount(userProfileDto.getAmount() + amount);
	}
	
	public void setDates(UserProfileDataDTO userProfileDto, Date transactionDate){
		userProfileDto.setEndDate(transactionDate);
		if(null == userProfileDto.getStartDate()){
			userProfileDto.setStartDate(transactionDate);
		}
	}
	
	public Date getTransactionDate(TransactionDetail transaction) {
		return transaction.getDate().toDate();
	}
	
	protected double getAmount(TransactionDetail transaction){
		return transaction.getAmount();
	}
	
	public abstract List<UserProfileDataDTO> returnDataList();
	
	public boolean allowedCategory(String category, List<String> handleCategory){
		boolean isAllowed = false;
		for(String validCategory : handleCategory){
			if(validCategory.equals("All")){
				isAllowed = true;
				break;
			}
			if(null != category && category.contains(validCategory)){
				isAllowed = true;
			}
		}
		return isAllowed;
	}	
}
