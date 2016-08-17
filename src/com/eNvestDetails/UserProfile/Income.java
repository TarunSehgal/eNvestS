package com.eNvestDetails.UserProfile;

import java.util.List;

import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.dto.UserProfileDataDTO;

public class Income extends UserProfileDataElement{

	private List<String >captureElement = null;
	
	public List<String> getCaptureElement() {
		return captureElement;
	}

	public void setCaptureElement(List<String> captureElement) {
		this.captureElement = captureElement;
	}
	
	@Override
	public void calculateDataelement(TransactionDetail transaction, String categoryHierarchy,UserProfileFactory profileFactory) {
		// TODO Auto-generated method stub
		try {
			/*if (!allowedCategory(categoryHierarchy, handleCategory)) {
				return;
			}*/
			for (String captureDTO : captureElement) {
				UserProfileDataDTO dto = getProfileDataMap().get(captureDTO);
				if (null == dto) {
					dto = profileFactory.getProfileDTO(captureDTO);
					getProfileDataMap().put(captureDTO, dto);
				}
				if(isAllowedCategory(transaction.getCategoryId(), dto.getPlaidCategory())){
					if("10".equals(dto.getId())){
						if(transaction.getAmount() < 0.0){
							dto.setAmount(dto.getAmount() + transaction.getAmount());
						}
					}else if ("11".equals(dto.getId())){
						if(transaction.getAmount() > 0.0){
							dto.setAmount(dto.getAmount() +transaction.getAmount());
						}					
					}
				}
							
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<UserProfileDataDTO> returnDataList() {
		// TODO Auto-generated method stub
		return null;
	}

}
