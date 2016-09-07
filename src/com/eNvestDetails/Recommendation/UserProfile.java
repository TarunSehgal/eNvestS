package com.eNvestDetails.Recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.UserInfoDAOService;
import com.eNvestDetails.DAL.DTO.UserProfileDataDTO;
import com.eNvestDetails.Factories.EnvestMessageFactory;
import com.eNvestDetails.RecommendationEngine.AbstractRule;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.UserProfileData.UserProfileDataCaptureService;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.util.UserAccountServiceUtil;
import com.eNvestDetails.util.UserServiceUtil;

public class UserProfile extends AbstractRule {
	
	private static Logger log = Logger.getLogger(UserProfile.class.getName()); 

	@Autowired
	private UserServiceUtil userServiceUtil;
	
	@Autowired
	private EnvestMessageFactory errorFactory = null;
	
	@Autowired
	private UserAccountServiceUtil accountServiceUtil;
	
	@Autowired
	private UserInfoDAOService daoAdapter;
	
	private UserProfileDataCaptureService userProfileService;
	
	protected boolean makeDecision(Map<String,Object> arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}
		
	@Autowired
	private MessageFactory message = null;
	
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
			userProfileService = new UserProfileDataCaptureService(info.getAccounts());
			Map<String, String> categories = userServiceUtil.getCategories();
			//clear profile data for fresh building
			daoAdapter.clearProfileData(userKey, errorFactory);
			
			saveProfileDataList = new ArrayList<UserProfileDataDTO>();

			for(TransactionDetail transaction : transactionList){
				String categoryHierarchy = categories.get(transaction.getCategoryId());
				userProfileService.processTransaction(transaction, categoryHierarchy);						
			}	
	
		}catch (Exception e){
			log.error("error occured while building userprofile",e);
		}
		arg.put(EnvestConstants.USER_PROFILE, userProfileService.getData());
		return arg;
	}
}	