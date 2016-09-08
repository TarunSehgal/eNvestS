package com.envest.services.restapi;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.envest.dal.UserInfoDAOService;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.ConfigFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.exceptions.ErrorMessage;
import com.envest.services.components.recommendationengine.InitiateRecommendation;
import com.envest.services.facade.UserAccountServiceUtil;
import com.envest.services.facade.UserServiceUtil;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.UserInfo;


@CrossOrigin(origins= "*")
@RestController
public class UserService implements eNvestService {
	
	@Autowired
	private ConfigFactory config = null;
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private UserServiceUtil plaidUtil = null;
	
	@Autowired
	private EnvestMessageFactory errorFactory = null;
	
	@Autowired
	private UserInfoDAOService daoAdapter;
	
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	private static Logger log = Logger.getLogger(UserService.class.getName()); 
	
	@Autowired
	private UserAccountServiceUtil accountServiceUtil;
	
		
	
	@RequestMapping(value="/UserService/test",method=RequestMethod.GET,produces="application/json")	
	public @ResponseBody EnvestResponse test(@RequestParam(value="test",defaultValue="test") String test){
		EnvestResponse mes  = errorFactory.getSuccessMessage(message.getMessage("message.success"));
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
			try {
				response.setUserKey(daoAdapter.saveUserInfo(response, errorFactory));
			} catch (EnvestException e) {
				return e.getErrorMessage();
			}
		}		
		return response;		
	}
	
	@RequestMapping(value="/UserService/users/saveUser",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody EnvestResponse saveUser(@RequestParam("userKey") Long userKey,@RequestParam("userID") String userID,
			@RequestParam("password") String password) {
		return plaidUtil.saveUser(userKey, userID, password);
	}
	
	@RequestMapping(value="/UserService/users/registerUser",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody EnvestResponse registerUser(@RequestParam("userID") String userID,
			@RequestParam("password") String password
			,@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName) {
		return plaidUtil.createUser(userID, password);
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
	
	@RequestMapping(value="/UserService/users/getDashBoard",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getDashBoard(@RequestParam("userKey") Long userKey){
		return accountServiceUtil.getDashboardData(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);		
	}
	
	@RequestMapping(value="/UserService/users/submitMFA",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse submitMFA(@RequestParam("userKey") Long userKey,@RequestParam("mfa")String mfa,@RequestParam("bank")String bank){
		return plaidUtil.submitMFA(userKey, mfa,bank);
	}
	
	@RequestMapping(value="/UserService/users/deleteUser",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse deleteUser(@RequestParam("userKey") Long userKey){
		return plaidUtil.deleteUser(userKey);
	}
	
	@RequestMapping(value="/UserService/users/getUserInfo",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getUserInfo(@RequestParam("userKey") Long userKey){
		return plaidUtil.getUserInfo(userKey);	
	}
}
