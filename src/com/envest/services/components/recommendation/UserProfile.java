package com.envest.services.components.recommendation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.envest.dal.UserDataService;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.recommendationengine.AbstractRule;
import com.envest.services.components.recommendationengine.RecommendationResponse;
import com.envest.services.components.util.account.UserProfileData;
import com.envest.services.facade.UserServiceFacade;
import com.envest.services.facade.TransactionServiceFacade;
import com.envest.services.facade.UserProfileDataCaptureService;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.ProfileResponse;
import com.envest.services.response.TransactionDetail;
import com.envest.services.response.UserInfo;

public class UserProfile extends AbstractRule {
	
	private static Logger log = Logger.getLogger(UserProfile.class.getName()); 
	
	@Autowired
	private EnvestMessageFactory errorFactory = null;
	
	@Autowired
	private UserServiceFacade dataService;
	
	@Autowired
	private TransactionServiceFacade transactionService;
	
	@Autowired
	private UserDataService daoAdapter;
	
	private UserProfileDataCaptureService userProfileService;
	
	protected boolean makeDecision(UserProfileData arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}
	
	protected RecommendationResponse doWork(UserProfileData arg) throws Exception {
		log.info("inside doWork method in UserProfile");
		Long userKey = null;
		RecommendationResponse response = new RecommendationResponse();
		try{
			if(null == arg || 0 == arg.getUserKey() ){
				return response;
			}			

			userKey = arg.getUserKey();
			UserInfo info = (UserInfo)transactionService.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);
			
			List<TransactionDetail> transactionList = info.getTransaction();
			Collections.sort(transactionList);
			
			Map<String, String> categories = dataService.getCategories();
			//clear profile data for fresh building
			daoAdapter.clearProfileData(userKey, errorFactory);			
			
			userProfileService = new UserProfileDataCaptureService(info.getAccounts());
			for(TransactionDetail transaction : transactionList){				
				String categoryHierarchy = categories.get(transaction.getCategoryId());
				userProfileService.processTransaction(transaction, categoryHierarchy);						
			}	
	
		}catch (Exception e){
			log.error("error occured while building userprofile",e);
		}
		response.setProfile((ProfileResponse)userProfileService.getProfileResponse());
		return response;
	}
}	