package com.envest.servicegateways.adapter.plaid;

import java.util.ArrayList;
import java.util.List;

import com.envest.services.response.UserInfo;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.InfoResponse.Address;
import com.plaid.client.response.InfoResponse.Email;
import com.plaid.client.response.InfoResponse.Info;
import com.plaid.client.response.InfoResponse.PhoneNumber;

public class PlaidInfoAdapter implements IPlaidInfoAdapter {
IPlaidAccountAdapter accountAdapter = new PlaidAccountAdapter();
	public UserInfo convertToUserInfo(InfoResponse r,String bank,String userId){
		UserInfo userInfo = new UserInfo();
		Info f = r.getInfo();
		
		userInfo.setAccessToken(r.getAccessToken());
		userInfo.setResponseFor(bank);
		userInfo.setUserId(userId);
		UserInfo.Info info = new UserInfo.Info();
		info.setNames(f.getNames());
		
		com.envest.services.response.Address address = null;
		List<com.envest.services.response.Address> addList = new ArrayList<com.envest.services.response.Address>(10);
		
		for (Address ad : f.getAddresses()) {
			address = new com.envest.services.response.Address();	
			address.setPrimary(ad.getPrimary());
			if (null != ad.getAddressDetails()){
				address.setCity(ad.getAddressDetails().getCity());
				address.setState(ad.getAddressDetails().getState());
				address.setStreet(ad.getAddressDetails().getStreet());
				address.setZip(ad.getAddressDetails().getZip());				
			}		
			addList.add(address);
		}
		info.setAddresses(addList);
		
		UserInfo.PhoneNumber userPhone = null;
		List<UserInfo.PhoneNumber> userPhoneList = new ArrayList<UserInfo.PhoneNumber>(10);
		for(PhoneNumber pn : f.getPhoneNumbers()){
			userPhone = new UserInfo.PhoneNumber();
			userPhone.setNumber(pn.getData());
			userPhone.setType(pn.getType());
			userPhone.setPrimary(pn.isPrimary());
			userPhoneList.add(userPhone);
		}
		info.setPhoneNumbers(userPhoneList);
		
		UserInfo.Email userEmails = null;
		List<UserInfo.Email> userEmailList = new ArrayList<UserInfo.Email>(10);
		for(Email em : f.getEmails()){
			userEmails = new UserInfo.Email();
			userEmails.setEmail(em.getData());
			userEmails.setType(em.getType());
			//userEmails.setPrimary(em.);
			userEmailList.add(userEmails);
		}
		info.setEmails(userEmailList);
		userInfo.setInfo(info);
		userInfo.setAccounts(accountAdapter.convertPlaidAccountsToAccountDetails(r.getAccounts(), bank));
		return userInfo;
	}
}
