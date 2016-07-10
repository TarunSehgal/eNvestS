package com.eNvestDetails.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.Response.UserInfo.Address;
import com.eNvestDetails.Response.UserInfo.Email;
import com.eNvestDetails.Response.UserInfo.Info;
import com.eNvestDetails.Response.UserInfo.PhoneNumber;
import com.eNvestDetails.dto.AddressDTO;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserEmailDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.eNvestDetails.dto.UserPhoneDTO;

public class ConvertBeanToDTO {
	
	private static final String NO = "N";
	private static final String YES = "Y";
	
	public static final String USERINFODTO = UserInfoDTO.class.getName();
	
	public static final String ADDRESSDTO = AddressDTO.class.getName();
	
	public static final String USEREMAILDTO = UserEmailDTO.class.getName();
	
	public static final String USERPHONEDTO = UserPhoneDTO.class.getName();
	
	public static final String USERACCESSTOKEN = UserAccessTokenDTO.class.getName();
	
	public static Map<String,Object> getUserInfoDTO(EnvestResponse response){
		Map<String,Object> returnMap = new HashMap<String,Object>(10);
		Info info = ((UserInfo)response).getInfo();
		UserInfoDTO d = new UserInfoDTO();
		for(String s : info.getNames()){
			d.setUserName(s);
		}
		d.setUserID(info.getUserId());
		d.setUserID(response.getUserId());
		d.setIsDeleted(NO);
		d.setIsActive(NO);
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
		
		return returnMap;
	}

}
