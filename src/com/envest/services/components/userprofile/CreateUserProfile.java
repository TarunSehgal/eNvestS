package com.envest.services.components.userprofile;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.recommendationengine.AbstractComponent;
import com.envest.services.facade.TransactionServiceFacade;
import com.envest.services.response.EnvestUserProfile;
import com.envest.services.response.UserInfo;

public class CreateUserProfile {
	
	private static Logger log = Logger.getLogger(CreateUserProfile.class.getName());
	
	@Autowired
	private TransactionServiceFacade transactionService;
	
	@Autowired
	private AnalyzeCashFlow analyzeCashFlow = null;
	
	@Autowired
	private ApplicationContext context;
	
	private List<String> steps;
	
	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public EnvestUserProfile getUserProfile(long userKey) throws EnvestException{
		log.info("Starting to build user profile for: " +userKey);
		EnvestUserProfile envestUserProfile = null;
		try{
			envestUserProfile = new EnvestUserProfile();
			UserInfo info = (UserInfo)transactionService.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);
			envestUserProfile.setUserKey(userKey);
			envestUserProfile.setCashflowDataElements(
					analyzeCashFlow.processTransaction(info));
			UserProfileBuilder builder = null;
			
			for(String bean : steps){
				   builder = (UserProfileBuilder) context.getBean(bean);
				   builder.buildProfile(info, envestUserProfile);
			}
			
		}catch (Exception e){
			log.error("Error occured while creating userprofile: "+ e);
			throw new EnvestException(new EnvestMessageFactory().
					getServerErrorMessage(e.getMessage())) ;	
		}
		return envestUserProfile;
	}

}
