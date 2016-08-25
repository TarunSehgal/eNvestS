package com.eNvestDetails.Recommendation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.RecommendationEngine.AbstractRule;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.UserProfile.UserProfileDataElement;
import com.eNvestDetails.UserProfile.UserProfileDataMapping;
import com.eNvestDetails.UserProfile.UserProfileFactory;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dao.UserInfoDao;
//import com.eNvestDetails.dto.UserProfileDTO;
import com.eNvestDetails.dto.UserProfileDataDTO;
import com.eNvestDetails.util.UserAccountServiceUtil;
import com.eNvestDetails.util.UserServiceUtil;

public class UserProfile extends AbstractRule {
	
	private static Logger log = Logger.getLogger(UserProfile.class.getName()); 

	@Autowired
	private UserServiceUtil userServiceUtil;
	
	@Autowired
	private ErrorMessageFactory errorFactory = null;
	
	@Autowired
	private UserAccountServiceUtil accountServiceUtil;
	
	
	private UserProfileFactory profileFactory;
	
	protected boolean makeDecision(Map<String,Object> arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}
		
	@Autowired
	private MessageFactory message = null;
	
	/*@Autowired
	UserProfileDataMapping profileMapping;*/
	
	protected Map<String,Object> doWork(Map<String,Object> arg) throws Exception {
		log.info("inside doWork method in UserProfile");

		List<UserProfileDataDTO> saveProfileDataList = null;
		Long userKey = null;
		try{
			if(null == arg || null == arg.get(EnvestConstants.ENVEST_RESPONSE) ){
				return arg;
			}			
			EnvestResponse eNvestRes = (EnvestResponse) arg.get(EnvestConstants.ENVEST_RESPONSE);
			userKey = ((UserInfo)eNvestRes).getUserKey();
			UserInfo info = (UserInfo)accountServiceUtil.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);
			
			List<TransactionDetail> transactionList = info.getTransaction();
			Collections.sort(transactionList);
			
			Map<String, String> categories = userServiceUtil.getCategories();
			//clear profile data for fresh building
			UserInfoDao.clearProfileData(userKey, errorFactory);
			
			saveProfileDataList = new ArrayList<UserProfileDataDTO>();
			profileFactory = new UserProfileFactory();
			//UserProfileDataElement.initializeProfileMap();
			UserProfileDataMapping profileMapping = new UserProfileDataMapping();			
			for(TransactionDetail transaction : transactionList){
				
				String categoryID = transaction.getCategoryId();
				String categoryHierarchy = categories.get(categoryID);
				if(null != categoryHierarchy){
					String concatenatedCategory = null;
					String[] split = categoryHierarchy.split(",");
					concatenatedCategory = split[0];
					
					if(split.length >1){
						concatenatedCategory  = concatenatedCategory + ","+split[1];
					}else{
						log.info("array of category is less than 2 : "+split);
					}
					
					List<UserProfileDataElement> list = profileMapping.getBean(split[0]);
					if(null != list){
						for(UserProfileDataElement bean : list){
							bean.calculateDataelement(transaction, categoryHierarchy);
						}
					}
					List<UserProfileDataElement> inflowOutflow = profileMapping.getBean("Income");
					if(null != inflowOutflow){
						for(UserProfileDataElement bean : inflowOutflow){
							bean.calculateDataelement(transaction, categoryHierarchy);
						}
					}
				}										
			}	
	
			saveProfileDataList = profileMapping.getAllValues();
			//saveProfileDataList = profileFactory.getProfileData();
			for(UserProfileDataDTO dto1 : saveProfileDataList){
				dto1.setUserKey(userKey);
			}
			UserInfoDao.saveUserProfileData(saveProfileDataList, errorFactory);
		}catch (Exception e){
			log.error("error occured while building userprofile",e);
		}
		arg.put(EnvestConstants.USER_PROFILE, saveProfileDataList);
		return arg;
	}
}	