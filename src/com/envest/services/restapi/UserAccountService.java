package com.envest.services.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.envest.services.components.EnvestConstants;
import com.envest.services.facade.TransactionServiceFacade;
import com.envest.services.facade.UserServiceFacade;
import com.envest.services.response.EnvestResponse;

@CrossOrigin(origins= "*")
@RestController
public class UserAccountService implements eNvestService {
	
	@Autowired
	private UserServiceFacade dataService = null;
	
	@Autowired
	private TransactionServiceFacade transactionService;
		
	@RequestMapping(value="/UserAccountService/users/transactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getTransactions(@RequestParam("userKey") Long userKey){
		return transactionService.getAccountAndTransaction(userKey, EnvestConstants.GET_TRANSACTIONS);		
	}	
	
	@RequestMapping(value="/UserAccountService/users/accounts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getAccounts(@RequestParam("userKey") Long userKey){
		return transactionService.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNTS);		
	}
	
	@RequestMapping(value="/UserAccountService/users/accountsTransactions",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getAccountsTransactions(@RequestParam("userKey") Long userKey){
		return transactionService.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);		
	}

	@RequestMapping(value="/UserAccountService/users/getDashBoard",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getDashBoard(@RequestParam("userKey") Long userKey){
		return transactionService.getDashboardData(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);		
	}

	@RequestMapping(value="/UserAccountService/users/getUserProfile",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public EnvestResponse getUserProfile(@RequestParam("userKey") Long userKey){
		return dataService.getProfileData(userKey);	
	}
}