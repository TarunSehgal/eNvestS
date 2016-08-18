package com.eNvestDetails.UserProfile;

import java.util.List;

import com.eNvestDetails.dto.UserProfileDataDTO;

public class Payment extends UserProfileDataElement {	

	/*@Override
	public void calculateDataelement(TransactionDetail transaction, String categoryHierarchy,UserProfileFactory profileFactory) {		
		try {			
			for (String captureDTO : captureElement) {
				UserProfileDataDTO dto = getProfileDataMap().get(captureDTO);
				if (null == dto) {
					dto = profileFactory.getProfileDTO(captureDTO);
					getProfileDataMap().put(captureDTO, dto);
				}
				if(isAllowedCategory(transaction.getCategoryId(), dto.getPlaidCategory())){
					dto.setAmount(dto.getAmount() + transaction.getAmount());
				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	@Override
	public List<UserProfileDataDTO> returnDataList() {
		// TODO Auto-generated method stub
		return null;
	}
}
