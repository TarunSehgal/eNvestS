package com.envest.services.components.userprofile;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.envest.dal.UserDataService;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.util.account.AccountList;
import com.envest.services.response.EnvestUserProfile;
import com.envest.services.response.UserInfo;

public class UserProfileBuildPersonalInfo extends UserProfileBuilder {
	
	private static Logger log = Logger.getLogger(UserProfileBuildPersonalInfo.class.getName());
	
	@Autowired
	private UserDataService dataService;

	@Override
	public EnvestUserProfile buildProfile(Object obj
			,EnvestUserProfile userProfile) throws EnvestException{
		
		UserInfo info = (UserInfo) obj;
		try{
			UserInfo data = (UserInfo)dataService.getUserProfileData(info.getUserKey());
			userProfile.setPersonnelinfo(data.getInfo());
			
		}catch (Exception e){
			log.error("Error occured while creating UserProfileBuildPersonalInfo: "+ e);
			throw new EnvestException(new EnvestMessageFactory().
					getServerErrorMessage(e.getMessage())) ;
		}		
		return userProfile;		
	}
}
