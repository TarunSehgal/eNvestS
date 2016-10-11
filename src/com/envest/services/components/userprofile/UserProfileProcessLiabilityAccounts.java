package com.envest.services.components.userprofile;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.util.account.AccountList;
import com.envest.services.components.util.account.AccountType;
import com.envest.services.response.EnvestUserProfile;
import com.envest.services.response.UserInfo;

public class UserProfileProcessLiabilityAccounts extends UserProfileBuilder {
	
	private static Logger log = Logger.getLogger(UserProfileProcessLiabilityAccounts.class.getName());

	@Override
	public EnvestUserProfile buildProfile(Object obj
			,EnvestUserProfile userProfile) throws EnvestException{
		
		UserInfo info = (UserInfo) obj;
		List<AccountList> liabilityList = new ArrayList<AccountList>(10);
		try{
			for(AccountType type : getAssetList()){
				liabilityList.add(getAccountByType(convertToEnvestAccount(info.getAccounts())
						, type));
			}
			userProfile.setLiabilities(liabilityList);
			
		}catch (Exception e){
			log.error("Error occured while creating UserProfileProcessLiabilityAccounts: "+ e);
			throw new EnvestException(new EnvestMessageFactory().
					getServerErrorMessage(e.getMessage())) ;
		}		
		return userProfile;		
	}
	
	private List<AccountType> getAssetList(){
		List<AccountType> list = new ArrayList<AccountType>(10);
		list.add(AccountType.creditcard);
		return list;
	}
}
