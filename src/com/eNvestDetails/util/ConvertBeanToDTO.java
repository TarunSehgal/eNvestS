package com.eNvestDetails.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eNvestDetails.DAL.IUserInfoDAOService;
import com.eNvestDetails.DAL.UserInfoDAOService;
import com.eNvestDetails.DAL.DTO.AccountsDTO;
import com.eNvestDetails.DAL.DTO.AddressDTO;
import com.eNvestDetails.DAL.DTO.UserAccessTokenDTO;
import com.eNvestDetails.DAL.DTO.UserEmailDTO;
import com.eNvestDetails.DAL.DTO.UserInfoDTO;
import com.eNvestDetails.DAL.DTO.UserPhoneDTO;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.EnvestMessageFactory;
import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.Response.UserInfo.Address;
import com.eNvestDetails.Response.UserInfo.Email;
import com.eNvestDetails.Response.UserInfo.Info;
import com.eNvestDetails.Response.UserInfo.PhoneNumber;

public class ConvertBeanToDTO {
	
	private static final String NO = "N";
	private static final String YES = "Y";
	
	public static final String USERINFODTO = UserInfoDTO.class.getName();
	
	public static final String ADDRESSDTO = AddressDTO.class.getName();
	
	public static final String USEREMAILDTO = UserEmailDTO.class.getName();
	
	public static final String USERPHONEDTO = UserPhoneDTO.class.getName();
	
	public static final String USERACCESSTOKEN = UserAccessTokenDTO.class.getName();
	
	public static final String ACCOUNTDTO = AccountsDTO.class.getName();
	
	static IUserInfoDAOService userInfoDAOAdaptor = new UserInfoDAOService();
	
	public static Map<String,Object> getUserInfoDTO(EnvestResponse response, EnvestMessageFactory errorFactory) throws EnvestException{
		Map<String,Object> returnMap = new HashMap<String,Object>(10);
		Info info = ((UserInfo)response).getInfo();
		
		UserInfoDTO d = userInfoDAOAdaptor.getUserInfoDetail(response.getUserKey(), errorFactory);
		for(String s : info.getNames()){
			d.setUserName(s);
		}
		d.setUserID(info.getUserId());
		d.setUserID(response.getUserId());
		d.setIsDeleted(NO);
		d.setIsActive(YES);
		d.setAccessToken(response.getAccessToken());
		//d.setPassword("test");
		//d.setUser_id(1L);
		returnMap.put(USERINFODTO, d);
		//returnList.add(d);
		AddressDTO addressdto = null;
		List<AddressDTO> addressList = new ArrayList<AddressDTO>();
		
		if(null != info.getAddresses()){
			for (Address ad : info.getAddresses()){
				addressdto = new AddressDTO();
				addressdto.setAddressstate(ad.getState());
				addressdto.setCity(ad.getCity());
				addressdto.setIsdeleted(NO);
				addressdto.setIsActive(YES);
				addressdto.setIsprimary(Boolean.TRUE.equals(ad.getPrimary())? "Y":"N");
				addressdto.setStreet(ad.getStreet());
				addressdto.setZip(ad.getZip());
				addressList.add(addressdto);
			}
		}
		returnMap.put(ADDRESSDTO, addressList);	
		
		UserEmailDTO emailDTO = null;
		List<UserEmailDTO> emailList = new ArrayList<UserEmailDTO>();
		if(null != info.getEmails()){
			for(Email email:info.getEmails()){
				emailDTO = new UserEmailDTO();
				emailDTO.setEmail(email.getEmail());
				emailDTO.setIsdeleted(NO);
				emailDTO.setIsActive(YES);				
				emailDTO.setType(email.getType());
				emailList.add(emailDTO);
			}
		}
		returnMap.put(USEREMAILDTO, emailList);	
		
		UserPhoneDTO phoneDTO = null;
		List<UserPhoneDTO> phoneList = new ArrayList<UserPhoneDTO>();
		if(null != info.getPhoneNumbers()){
			for(PhoneNumber phone :info.getPhoneNumbers()){
				phoneDTO = new UserPhoneDTO();
				phoneDTO.setIsdeleted(NO);
				phoneDTO.setIsActive(YES);
				phoneDTO.setIsprimary(Boolean.TRUE.equals(phone.isPrimary())? "Y":"N");
				phoneDTO.setNumber(phone.getNumber());
				phoneDTO.setType(phone.getType());
				
				phoneList.add(phoneDTO);
			}
		}
		returnMap.put(USERPHONEDTO, phoneList);	
		
		UserAccessTokenDTO accesToken = new UserAccessTokenDTO();
		accesToken.setAccessToken(response.getAccessToken());
		accesToken.setUserBank(response.getResponseFor());
		accesToken.setIsActive(YES);
		accesToken.setIsdeleted(YES);
		
		returnMap.put(USERACCESSTOKEN, accesToken);	
		
		AccountsDTO accDTO = null;
		List<AccountsDTO> accList = new ArrayList<AccountsDTO>(10);
		for(AccountDetail accDetail : ((UserInfo)response).getAccounts()){
			accDTO = new AccountsDTO();
			accDTO.setAccountId(accDetail.getAccountId());
			if(null != accDetail.getNumbers()){
				accDTO.setAccountNumber(accDetail.getNumbers().getAccount());				
			}
			if(null != accDetail.getBalance()){
				accDTO.setAvailableBalance(accDetail.getBalance().getAvailable());
				accDTO.setCurrentBalance(accDetail.getBalance().getCurrent());
				accDTO.setBalanceAsOF(new Date());
			}
			accDTO.setInstitutiontype(accDetail.getInstitutionType());
			accDTO.setIsActive(YES);
			accDTO.setIsdeleted(NO);
			accDTO.setItemId(accDetail.getItem());
			if(null != accDetail.getMeta() ){
				accDTO.setName(accDetail.getMeta().getName());
			}
			accDTO.setSubType(accDetail.getSubtype());
			accDTO.setType(accDetail.getType());
			//accDTO.setName(name);
			accList.add(accDTO);
		}
		
		//List<AccountDetail> accDetail = response.get
		returnMap.put(ACCOUNTDTO, accList);	
		return returnMap;
	}

}
