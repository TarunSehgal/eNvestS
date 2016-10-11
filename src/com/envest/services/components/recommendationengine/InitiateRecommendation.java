package com.envest.services.components.recommendationengine;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.envest.dal.dao.UserInfoDao;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.userprofile.CreateUserProfile;
import com.envest.services.response.EnvestUserProfile;


public class InitiateRecommendation {
	
	private static Logger log = Logger.getLogger(InitiateRecommendation.class.getName());
	
	private AbstractComponent firstStep;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private CreateUserProfile createUserProfile;

	private List<String> rules;
	   
	   public List<String> getRules() {
		return rules;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
	}

	public void setFirstStep(AbstractComponent firstStep) {
	      this.firstStep = firstStep;
	   }
	   
	   public RecommendationResponse processRequest(Long userKey) throws EnvestException {
		   RecommendationResponse response = null;
		   EnvestUserProfile userProfile = null;
		   try{
			   userProfile = createUserProfile.getUserProfile(userKey);
			   AbstractComponent  executeRule= null;
			   response = new RecommendationResponse();
			   for(String rule :rules){
				   executeRule = (AbstractComponent) context.getBean(rule);
				   response.Merge(executeRule.execute(userProfile));
			   }
		   }catch(Exception e){
			   log.error("Error occured while saving user",e);
				throw new EnvestException(new EnvestMessageFactory().
						getServerErrorMessage(e.getMessage())) ;
		   }
		   
	      //firstStep.execute(arg);
		   return response;
	   }
	   
}
