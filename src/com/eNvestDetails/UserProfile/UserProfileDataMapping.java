package com.eNvestDetails.UserProfile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.eNvestDetails.DAL.DTO.UserProfileDataDTO;

@Component
public class UserProfileDataMapping {

	private static final Logger LOGGER = Logger.getLogger(UserProfileDataMapping.class.getName());
	
	private  Map<String, List<UserProfileDataElement>> data = null;

	public UserProfileDataMapping(){
		getBeanFromFactory("userProfileDataMapping");
	}

	private void  getBeanFromFactory(String beanId) {

		try{
			ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath*:userProfileDataMapping.xml");
			if (ctx != null) {
				data = (Map<String, List<UserProfileDataElement>>)ctx.getBean(beanId);
			}
		}catch(Exception ex){
			LOGGER.error("Exception occurred in getBeanFromFactory(String beanId) " , ex);
		}
	}
	
	public List<UserProfileDataElement> getBean(String value){
		return data.get(value);
	}
	
	public List<UserProfileDataDTO> getAllValues(){
		Collection<List<UserProfileDataElement>> allBean =  data.values();
		Iterator<List<UserProfileDataElement>> iterator = allBean.iterator();
		List<UserProfileDataDTO> dtoList = new ArrayList<UserProfileDataDTO>(100);
		while(iterator.hasNext()){
			List<UserProfileDataElement> list = iterator.next();
			for(UserProfileDataElement dataElement : list){
				dtoList.addAll(dataElement.getData());
			}
		}
		return dtoList;
	}
}
