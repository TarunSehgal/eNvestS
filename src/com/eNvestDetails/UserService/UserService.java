package com.eNvestDetails.UserService;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Recommendation.TestOppurtunity;
import com.eNvestDetails.RecommendationEngine.InitiateRecommendation;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.dao.UserInfoDao;
import com.eNvestDetails.util.UserServiceUtil;


@CrossOrigin(origins= "*")
@RestController
public class UserService {
	
	@Autowired
	private ConfigFactory config = null;
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private UserServiceUtil plaidUtil = null;
	
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	private static Logger log = Logger.getLogger(UserService.class.getName()); 
	
	@RequestMapping(value="/UserService/test",method=RequestMethod.GET,produces="application/json")	
	public @ResponseBody ErrorMessage test(@RequestParam(value="test",defaultValue="test") String test){
		ErrorMessage mes  = new ErrorMessage();
		mes.setCode(0);
		mes.setMessage("User added successfully");
		mes.setStatus(":Test");
		mes.setType("Success");
		message.getMessage("message.success");
		config.getResultString("key");
		try {
			recommendationEngine.processRequest(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error occurred during recomendationsengine" ,e);
		}
		return mes;
	}
	
	@RequestMapping(value="/UserService/users/getInfo",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody EnvestResponse getInfo(
			@RequestParam(value="userID") String userId
			,@RequestParam(value="password") String password
			,@RequestParam(value="bank") String bank){
		EnvestResponse response = plaidUtil.getInfo(userId, password, bank);
		if(response instanceof UserInfo){
			response.setUserKey(UserInfoDao.saveUserInfo(response));
		}		
		return response;		
	}
	
	@RequestMapping(value="/UserService/users/saveUser",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody EnvestResponse saveUser(@RequestParam("userKey") Long userKey,@RequestParam("userID") String userID,
			@RequestParam("password") String password) {
		return plaidUtil.saveUser(userKey, userID, password);
	}
	
	@RequestMapping(value="/UserService/users/authenticate",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse authenticate(@RequestParam("userID") String userID, @RequestParam("password") String password) {
		return plaidUtil.authenticate(userID, password);
	}
	@RequestMapping(value="/UserService/users/linkAccount",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse linkAccount(@RequestParam("userKey") Long userKey,@RequestParam("userID") String userID,
			@RequestParam("password") String password,@RequestParam("bank") String bank) {
		return plaidUtil.linkAccounts(userKey, userID, password, bank);
		
	}
	
}
