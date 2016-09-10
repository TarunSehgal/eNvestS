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
import com.envest.services.components.recommendationengine.InitiateRecommendation;
import com.envest.services.facade.DataServiceFacade;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.UserInfo;

@CrossOrigin(origins= "*")
@RestController
public class DataService implements eNvestService {
	
	@Autowired
	private ConfigFactory config = null;
	
	@Autowired
	private MessageFactory message = null;
		
	@Autowired
	private DataServiceFacade dataService = null;
	
	@Autowired
	private EnvestMessageFactory errorFactory = null;
	
	@Autowired
	private UserInfoDAOService userInfoDaoService;
	
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	private static Logger log = Logger.getLogger(DataService.class.getName()); 
	
	@RequestMapping(value="/DataService/users/transactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getTransactions(@RequestParam("userKey") Long userKey){
		return dataService.getAccountAndTransaction(userKey, EnvestConstants.GET_TRANSACTIONS);		
	}	
	
	@RequestMapping(value="/DataService/users/accounts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getAccounts(@RequestParam("userKey") Long userKey){
		return dataService.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNTS);		
	}
	
	@RequestMapping(value="/DataService/users/accountsTransactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getAccountsTransactions(@RequestParam("userKey") Long userKey){
		return dataService.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);		
	}

	@RequestMapping(value="/DataService/users/getDashBoard",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getDashBoard(@RequestParam("userKey") Long userKey){
		return dataService.getDashboardData(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);		
	}

	@RequestMapping(value="/DataService/users/getUserProfile",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getUserProfile(@RequestParam("userKey") Long userKey){
		return dataService.getProfileData(userKey);	
	}
	
	@RequestMapping(value="/DataService/test",method=RequestMethod.GET,produces="application/json")	
	public @ResponseBody EnvestResponse test(@RequestParam(value="test",defaultValue="test") String test){
		EnvestResponse mes  = errorFactory.getSuccessMessage(message.getMessage("message.success"));
		config.getResultString("key");
		try {
			recommendationEngine.processRequest(null);
		} catch (Exception e) {
			log.error("Error occurred during recomendationsengine" ,e);
		}
		return mes;
	}
	
	@RequestMapping(value="/DataService/users/getInfo",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody EnvestResponse getInfo(
			@RequestParam(value="userID") String userId
			,@RequestParam(value="password") String password
			,@RequestParam(value="bank") String bank){
		EnvestResponse response = dataService.getInfo(userId, password, bank);
		if(response instanceof UserInfo){
			try {
				response.setUserKey(userInfoDaoService.saveUserInfo(response, errorFactory));
			} catch (EnvestException e) {
				return e.getErrorMessage();
			}
		}		
		return response;		
	}
	
	@RequestMapping(value="/DataService/users/saveUser",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody EnvestResponse saveUser(@RequestParam("userKey") Long userKey,@RequestParam("userID") String userID,
			@RequestParam("password") String password) {
		return dataService.saveUser(userKey, userID, password);
	}
	
	@RequestMapping(value="/DataService/users/registerUser",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody EnvestResponse registerUser(@RequestParam("userID") String userID,
			@RequestParam("password") String password
			,@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName) {
		return dataService.createUser(userID, password);
	}
	
	@RequestMapping(value="/DataService/users/authenticate",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse authenticate(@RequestParam("userID") String userID, @RequestParam("password") String password) {
		return dataService.authenticate(userID, password);
	}
	@RequestMapping(value="/DataService/users/linkAccount",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse linkAccount(@RequestParam("userKey") Long userKey,@RequestParam("userID") String userID,
			@RequestParam("password") String password,@RequestParam("bank") String bank) {
		return dataService.linkAccounts(userKey, userID, password, bank);
		
	}
		
	@RequestMapping(value="/DataService/users/submitMFA",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse submitMFA(@RequestParam("userKey") Long userKey,@RequestParam("mfa")String mfa,@RequestParam("bank")String bank){
		return dataService.submitMFA(userKey, mfa,bank);
	}
	
	@RequestMapping(value="/DataService/users/deleteUser",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse deleteUser(@RequestParam("userKey") Long userKey){
		return dataService.deleteUser(userKey);
	}
	
	@RequestMapping(value="/DataService/users/getUserInfo",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getUserInfo(@RequestParam("userKey") Long userKey){
		return dataService.getUserInfo(userKey);	
	}
}
