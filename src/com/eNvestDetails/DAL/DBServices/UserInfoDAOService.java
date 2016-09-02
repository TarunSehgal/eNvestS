package com.eNvestDetails.DAL.DBServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.DAL.Dao.UserInfoDao;
import com.eNvestDetails.DAL.Dto.AddressDTO;
import com.eNvestDetails.DAL.Dto.UserEmailDTO;
import com.eNvestDetails.DAL.Dto.UserInfoDTO;
import com.eNvestDetails.DAL.Dto.UserPhoneDTO;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.response.InfoResponse;

@Component
public class UserInfoDAOService implements IDAOAdaptor {

	@Autowired
	private ErrorMessageFactory errorFactory;
	
	public EnvestResponse getUserProfileData(Long userKey){
		
		List<Object> data = null;
		Map<String,List<Object>> userProfileData;
		UserInfo infoResponse = null;
		try{
			infoResponse = new UserInfo();
			UserInfo.Info info = new UserInfo.Info();
			UserInfo.Address addressResponse = null;
			UserInfo.Email emailResponse = null;
			UserInfo.PhoneNumber phoneResponse = null;
			infoResponse.setInfo(info);
			infoResponse.setUserKey(userKey);
			
			userProfileData = UserInfoDao.getProfileData(userKey);
			AddressDTO address = null;
			UserEmailDTO email = null;
			UserPhoneDTO phone = null;
			UserInfoDTO userInfo = null;
			data = (List)userProfileData.get("UserInfoDTO");
			if(null != data && data.size() > 0){
				userInfo = (UserInfoDTO)data.get(0);
				List<String> name = new ArrayList<String>(1);
				name.add(userInfo.getUserName());
				info.setNames(name);
			}
			data = (List)userProfileData.get("AddressDTO");
			List<UserInfo.Address> addList = new ArrayList<UserInfo.Address>(10);
			if(null != data && data.size() > 0){
				Iterator<Object> iterator = data.iterator();
				while(iterator.hasNext()){
					addressResponse = new  UserInfo.Address();
					address = (AddressDTO)iterator.next();
					addressResponse.setCity(address.getCity());
					addressResponse.setPrimary("Y".equals(address.getIsprimary()));
					addressResponse.setState(address.getAddressstate());
					addressResponse.setStreet(address.getStreet());
					addressResponse.setZip(address.getZip());
					addList.add(addressResponse);
				}
			}
			info.setAddresses(addList);
			
			data = (List)userProfileData.get("UserEmailDTO");
			List<UserInfo.Email> emailList = new ArrayList<UserInfo.Email>(10);
			if(null != data && data.size() > 0){
				Iterator<Object> iterator = data.iterator();
				while(iterator.hasNext()){
					emailResponse = new UserInfo.Email();
					email = (UserEmailDTO)iterator.next();
					emailResponse.setEmail(email.getEmail());
					emailResponse.setType(email.getType());
					emailList.add(emailResponse);
				}
			}
			info.setEmails(emailList);
			
			data = (List)userProfileData.get("UserPhoneDTO");
			List<UserInfo.PhoneNumber> phoneList = new ArrayList<UserInfo.PhoneNumber>(10);
			if(null != data && data.size() > 0){
				Iterator<Object> iterator = data.iterator();
				while(iterator.hasNext()){
					phoneResponse = new UserInfo.PhoneNumber();
					phone = (UserPhoneDTO)iterator.next();
					phoneResponse.setNumber(phone.getNumber());
					phoneResponse.setPrimary("Y".equals(phone.getIsprimary()));
					phoneResponse.setType(phone.getType());
					phoneList.add(phoneResponse);
				}
			}
			info.setPhoneNumbers(phoneList);
			
		}catch(HibernateException e){
			return errorFactory.getServerErrorMessage(e.getMessage());
		}
		return infoResponse;
	}
	
}
