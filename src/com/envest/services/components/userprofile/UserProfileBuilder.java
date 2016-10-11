package com.envest.services.components.userprofile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.util.account.Account;
import com.envest.services.components.util.account.AccountBuilder;
import com.envest.services.components.util.account.AccountList;
import com.envest.services.components.util.account.AccountType;
import com.envest.services.response.AccountDetail;
import com.envest.services.response.EnvestUserProfile;

public abstract class UserProfileBuilder {
	
	@Autowired
	private AccountBuilder accBuilder;
	
	public abstract EnvestUserProfile buildProfile(Object obj,
			EnvestUserProfile userProfile) throws EnvestException;

	public AccountList getAccountByType(List<Account> acc,AccountType type){
		AccountList  asset = new AccountList();
		List<Account> assetList = new ArrayList<Account>(10);
		
		for(Account account : acc){
			if(null != account){
				if(type.equals(account.getAccountType())){
					assetList.add(account);
					asset.setAccountType(type);
					asset.setNoOfAccounts(asset.getNoOfAccounts() + 1);
					asset.setTotalBalance(asset.getTotalBalance() + account.getBalance());
				}
			}

		}
		asset.setAccounts(assetList);
		return asset;
	}
	
	public List<Account> convertToEnvestAccount(List<AccountDetail> accList){
		List<Account> acc = new ArrayList<Account>(10);
	
		for(AccountDetail accDetail : accList){
			acc.add(accBuilder.accountBuilder(accDetail));			
		}	
		return acc;
	}
}
