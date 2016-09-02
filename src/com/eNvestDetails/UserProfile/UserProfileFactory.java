package com.eNvestDetails.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.eNvestDetails.DAL.Dto.UserProfileDataDTO;
import com.eNvestDetails.Response.TransactionDetail;

@Component
public class UserProfileFactory {

	private static final Logger LOGGER = Logger.getLogger(UserProfileFactory.class.getName());
	
	private  Map<String, UserProfileDataDTO> data = null;

	public UserProfileFactory(){
		getBeanFromFactory("profileDataList");
	}

	private void  getBeanFromFactory(String beanId) {

		try{
			ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath*:categoryToProfileIdMapping.xml");
			if (ctx != null) {
				data = (Map<String,UserProfileDataDTO>)ctx.getBean(beanId);
			}
		}catch(Exception ex){
			LOGGER.error("Exception occurred in getBeanFromFactory(String beanId) " , ex);
		}
	}
	
	public UserProfileDataDTO getProfileDTO(String value){
		UserProfileDataDTO dto = data.get(value);
		return dto;//UserProfileDataDTO(dto.getId(),dto.getType(),dto.getSubType(),dto.getAddAlways(),dto.getAddCounter());
	}
	
	public void setProfileData(String category,TransactionDetail transaction){
		try{
			UserProfileDataDTO dto = data.get(category);
			if(null != dto){
				if(null!= dto.getAddAlways() && "Y".equals(dto.getAddAlways())){
					dto.setAmount(transaction.getAmount());
					dto.setAccountId(transaction.getAccountId());
				}else if("N".equals(dto.getAddAlways()) && dto.getAddCounter() > 0){
					dto.setAmount(transaction.getAmount());
					dto.setAccountId(transaction.getAccountId());
				}	
				setTotalInflowOutflow(transaction);
			}
		}catch(Exception e){
			LOGGER.error("error occured while setting profile data: ",e);
		}
	}
	
	private void setTotalInflowOutflow(TransactionDetail transaction){
		if(transaction.getAmount() < 0.0){
			UserProfileDataDTO inflowDTO = data.get("Inflow");
			if(null != inflowDTO){
				inflowDTO.setAmount(transaction.getAmount());
			}
		}else{
			UserProfileDataDTO outFlowDTO = data.get("Outflow");
			if(null != outFlowDTO){
				outFlowDTO.setAmount(transaction.getAmount());
			}
		}		
	}
	
	public List<UserProfileDataDTO> getProfileData(){
		return  new ArrayList<UserProfileDataDTO>(data.values());			
	}


}
