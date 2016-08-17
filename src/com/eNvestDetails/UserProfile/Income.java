package com.eNvestDetails.UserProfile;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

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
					if("10".equals(dto.getId())){//inflow
						if(transaction.getAmount() < 0.0){
							dto.setAmount(dto.getAmount() + transaction.getAmount());
						}
					}else if ("11".equals(dto.getId())){//outflow
						if(transaction.getAmount() > 0.0){
							dto.setAmount(dto.getAmount() +transaction.getAmount());
						}					
					}else if ("15".equals(dto.getId())){//outflow 30 days
						if(transaction.getAmount() > 0.0){
							if(dto.getInflowOutflowStartDate() == null){
								dto.setInflowOutflowStartDate(transaction.getDate()
										.toDate());
							}
							Date startDate = dto.getInflowOutflowStartDate();
							Date transactionDate = transaction.getDate().toDate();
							if(transactionDate.after(DateUtils.addDays(startDate, -30))){
								dto.setAmount(dto.getAmount() +transaction.getAmount());								
							}
						}
																	
					}else if ("16".equals(dto.getId())){//outflow 90 days
						if(transaction.getAmount() > 0.0){
							if(dto.getInflowOutflowStartDate() == null){
								dto.setInflowOutflowStartDate(transaction.getDate()
										.toDate());
							}
							Date startDate = dto.getInflowOutflowStartDate();
							Date transactionDate = transaction.getDate().toDate();
							if(transactionDate.after(DateUtils.addDays(startDate, -90))){
								dto.setAmount(dto.getAmount() +transaction.getAmount());								
							}
						}											
					}else if ("17".equals(dto.getId())){//inflow 30 days
						if(transaction.getAmount() < 0.0){
							if(dto.getInflowOutflowStartDate() == null){
								dto.setInflowOutflowStartDate(transaction.getDate()
										.toDate());
							}
							Date startDate = dto.getInflowOutflowStartDate();
							Date transactionDate = transaction.getDate().toDate();
							if(transactionDate.after(DateUtils.addDays(startDate, -30))){
								dto.setAmount(dto.getAmount() +transaction.getAmount());								
							}
						}											
					}else if ("18".equals(dto.getId())){//inflow 30 days
						if(transaction.getAmount() < 0.0){
							if(dto.getInflowOutflowStartDate() == null){
								dto.setInflowOutflowStartDate(transaction.getDate()
										.toDate());
							}
							Date startDate = dto.getInflowOutflowStartDate();
							Date transactionDate = transaction.getDate().toDate();
							if(transactionDate.after(DateUtils.addDays(startDate, -90))){
								dto.setAmount(dto.getAmount() +transaction.getAmount());								
							}
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
