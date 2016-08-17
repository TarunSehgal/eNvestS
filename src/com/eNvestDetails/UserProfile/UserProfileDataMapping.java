package com.eNvestDetails.UserProfile;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

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
}
