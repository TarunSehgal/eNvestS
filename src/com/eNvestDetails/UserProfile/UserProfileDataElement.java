package com.eNvestDetails.UserProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.dto.UserProfileDataDTO;

public abstract class UserProfileDataElement {
	
	private static Map<String,UserProfileDataDTO> profileDataMap = new HashMap<String,UserProfileDataDTO>(10);
		
	public static Map<String, UserProfileDataDTO> getProfileDataMap() {
		return profileDataMap;
	}

	public static void initializeProfileMap() {
		profileDataMap = new HashMap<String,UserProfileDataDTO>(10);
	}

	public abstract void calculateDataelement(TransactionDetail transaction, String categoryHierarchy,UserProfileFactory profileFactory);
	
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
	
	public static List<UserProfileDataDTO> getData(){
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
}
