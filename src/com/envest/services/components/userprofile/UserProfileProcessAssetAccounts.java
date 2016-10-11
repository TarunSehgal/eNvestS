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

public class UserProfileProcessAssetAccounts extends UserProfileBuilder {
	
	private static Logger log = Logger.getLogger(UserProfileProcessAssetAccounts.class.getName());

	@Override
	public EnvestUserProfile buildProfile(Object obj
			,EnvestUserProfile userProfile) throws EnvestException{
		
		UserInfo info = (UserInfo) obj;
		List<AccountList> assetList = new ArrayList<AccountList>(10);
		try{
			for(AccountType type : getAssetList()){
				assetList.add(getAccountByType(convertToEnvestAccount(info.getAccounts())
						, type));
			}
			userProfile.setAssets(assetList);
			
		}catch (Exception e){
			log.error("Error occured while creating UserProfileProcessAssetAccounts: "+ e);
			throw new EnvestException(new EnvestMessageFactory().
					getServerErrorMessage(e.getMessage())) ;
		}		
		return userProfile;		
	}
	
	private List<AccountType> getAssetList(){
		List<AccountType> list = new ArrayList<AccountType>(10);
		list.add(AccountType.saving);
		list.add(AccountType.checking);
		return list;
	}
}
