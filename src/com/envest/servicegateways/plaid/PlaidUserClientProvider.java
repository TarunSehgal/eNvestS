package com.envest.servicegateways.plaid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.envest.services.components.config.ConfigFactory;
import com.plaid.client.PlaidClients;
import com.plaid.client.PlaidPublicClient;
import com.plaid.client.PlaidUserClient;

@Component("tplaidClient")
@Scope("singleton")
class PlaidUserClientProvider {
	
	 public static final String BASE_URI_PRODUCTION = PlaidClients.BASE_URI_PRODUCTION;
	 
	 public static final String BASE_TEST = PlaidClients.BASE_URI_TEST;
	 
	 @Autowired
	 IPlaidEnvironment plaidEnvironment = null;
	 
	 public PlaidUserClientProvider(){
		
	 }
	
	public PlaidUserClient getPlaidClient(){
		if(IsTestEnvironment()){
			return PlaidClients.testUserClient(GetClientId(), GetSecretKey());
			
		}else if(IsProductionEnvironment()){
			return PlaidClients.productionUserClient(GetClientId(), GetSecretKey());
		}
		return null;
	}
	
	
	
/*	public PlaidPublicClient getPlaidPublicClient(){		

		if(IsTestEnvironment()){
			return PlaidClients.testPublicClient();
		}else if(IsProductionEnvironment()){
			return PlaidClients.productionPublicClient();
		}
		
		return null;
	}*/
	

	private Boolean IsTestEnvironment(){
		return "TEST".equals(plaidEnvironment.getEnvironment());
	}
	
	private Boolean IsProductionEnvironment(){
		return "PROD".equals(plaidEnvironment.getEnvironment());
	}
	
	private String GetClientId() {
		return plaidEnvironment.getClientId();
	}
	
	private String GetSecretKey() {
		return plaidEnvironment.getSecretKey();
	}
	
	private String GetEnvironment() {
		return plaidEnvironment.getEnvironment();
	}	
}