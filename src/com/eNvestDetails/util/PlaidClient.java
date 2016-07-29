package com.eNvestDetails.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.ConfigFactory;
import com.plaid.client.PlaidClients;
import com.plaid.client.PlaidPublicClient;
import com.plaid.client.PlaidUserClient;

@Component
@Scope("singleton")
public class PlaidClient {
	
	@Autowired
	private ConfigFactory config = null;
	
	 public static final String BASE_URI_PRODUCTION = "https://api.plaid.com";
	
	public PlaidUserClient getPlaidClient(){
		String clientid = config.getResultString("clientid");
		
		String key = config.getResultString("key");
		
		String environment = config.getResultString("env");
		PlaidUserClient client = null;
		if("TEST".equals(environment)){
			client = PlaidClients.testUserClient(
					clientid, key);
		}else if("PROD".equals(environment)){
			client =  PlaidClients.productionUserClient(
					clientid, key);
		}
		return client;
			
	}
	
	public PlaidPublicClient getPlaidPublicClient(){
		String clientid = config.getResultString("clientid");
		
		String key = config.getResultString("key");
		
		String environment = config.getResultString("env");
		PlaidPublicClient client = null;
		if("TEST".equals(environment)){
			client = PlaidClients.testPublicClient();
		}else if("PROD".equals(environment)){
			client =  PlaidClients.productionPublicClient();
		}
		return client;
			
	}
	
	

}
