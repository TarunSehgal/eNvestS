package com.eNvestDetails.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.util.UserAccountServiceUtil;

@CrossOrigin(origins= "*")
@RestController
public class UserAccountService {
	
	@Autowired
	private ConfigFactory config = null;
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private UserAccountServiceUtil accountServiceUtil = null;
	
	@RequestMapping(value="/UserAccountService/users/transactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getTransactions(@RequestParam("userKey") Long userKey){
		return accountServiceUtil.getAccountAndTransaction(userKey, EnvestConstants.GET_TRANSACTIONS);		
	}	
	
	@RequestMapping(value="/UserAccountService/users/accounts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getAccounts(@RequestParam("userKey") Long userKey){
		return accountServiceUtil.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNTS);		
	}
	
	@RequestMapping(value="/UserAccountService/users/accountsTransactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getAccountsTransactions(@RequestParam("userKey") Long userKey){
		return accountServiceUtil.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);		
	}
	
	@RequestMapping(value="/UserAccountService/users/getDashBoard",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getDashBoard(@RequestParam("userKey") Long userKey){
		return accountServiceUtil.getDashboardData(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);		
	}
}
