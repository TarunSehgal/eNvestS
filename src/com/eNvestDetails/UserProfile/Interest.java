package com.eNvestDetails.UserProfile;

import java.util.List;

import com.eNvestDetails.DAL.DTO.UserProfileDataDTO;
import com.eNvestDetails.Response.TransactionDetail;

public class Interest extends UserProfileDataElement {
	
	@Override
	protected void extractAdditionalInformation(UserProfileDataDTO userProfileDTO, TransactionDetail transaction) {	
		userProfileDTO.setInterestAsOf(transaction.getDate().toDate());
	}

	@Override
	public List<UserProfileDataDTO> returnDataList() {
		// TODO Auto-generated method stub
		return null;
	}	
}
