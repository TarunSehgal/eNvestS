package com.envest.dal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envest.dal.dao.UserInfoDao;
import com.envest.dal.dto.AddressDTO;
import com.envest.dal.dto.UserAccessTokenDTO;
import com.envest.dal.dto.UserEmailDTO;
import com.envest.dal.dto.UserInfoDTO;
import com.envest.dal.dto.UserPhoneDTO;
import com.envest.dal.dto.UserProfileDataDTO;
import com.envest.security.TokenUtils;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.UserInfo;
import com.plaid.client.response.InfoResponse;

@Component
public class UserDataService implements IUserDataService {

	@Autowired
	private EnvestMessageFactory errorFactory;
	
	public EnvestResponse getUserProfileData(Long userKey) throws EnvestException{
		
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

	@Override
	public Long saveUserInfo(EnvestResponse saveRespone, boolean saveAccesToken, EnvestMessageFactory errorFactory) throws EnvestException {
		// TODO Auto-generated method stub
		return UserInfoDao.saveUserInfo(saveRespone, saveAccesToken, errorFactory);
	}

	@Override
	public Long saveUserInfo(EnvestResponse saveRespone, EnvestMessageFactory errorFactory) throws EnvestException {
		return UserInfoDao.saveUserInfo(saveRespone, errorFactory);
	}

	@Override
	public UserInfoDTO getUserInfoDetail(long key, EnvestMessageFactory errorFactory) throws EnvestException {
		return UserInfoDao.getUserInfoDetail(key, errorFactory);
	}

	@Override
	public long registerUser(String userID, String password, MessageFactory message, EnvestMessageFactory errorFactory) throws EnvestException {
		return UserInfoDao.registerUser(userID, password, message, errorFactory);
	}

	@Override
	public int saveUser(Long userKey, String userID, String password) throws EnvestException {
		return UserInfoDao.saveUser(userKey, userID, password);
	}

	@Override
	public UserInfoDTO authenticateUser(String userId, String password)throws EnvestException  {
		return UserInfoDao.authenticateUser(userId, password);
	}

	@Override
	public void saveAccessToken(UserAccessTokenDTO accessToken) throws EnvestException  {
		UserInfoDao.saveAccessToken(accessToken);
	}
	
	public void saveAccessToken(EnvestResponse response) throws EnvestException  {
		UserAccessTokenDTO token = TokenUtils.createTokenFromResponse(response);
		UserInfoDao.saveAccessToken(token);
	}

	@Override
	public List<UserAccessTokenDTO> getAccesTokens(Long id) throws EnvestException  {
		return UserInfoDao.getAccesTokens(id);
	}

	@Override
	public UserAccessTokenDTO getAccesTokens(Long id, String bank) throws EnvestException  {
		return UserInfoDao.getAccesTokens(id, bank);
	}

	@Override
	public List<String> getAccesTokenList(Long id) throws EnvestException  {
		return UserInfoDao.getAccesTokenList(id);
	}

	@Override
	public String getAccesToken(Long id, String bank) throws EnvestException  {
		return UserInfoDao.getAccesToken(id, bank);
	}
	
	@Override
	public void saveUserProfileData(List<UserProfileDataDTO> userProfile, EnvestMessageFactory errorFactory) throws EnvestException{
	UserInfoDao.saveUserProfileData(userProfile, errorFactory);
		
	}

	@Override
	public List<UserProfileDataDTO> getUserProfileData(Long userKey, EnvestMessageFactory errorFactory)
			throws EnvestException {
		return UserInfoDao.getUserProfileData(userKey, errorFactory);
	}

	@Override
	public boolean testConnection() {
		return UserInfoDao.testConnection();
	}

	@Override
	public void deleteUser(Long key 
			, EnvestMessageFactory errorFactory) throws EnvestException {
		UserInfoDao.deleteUser(key, errorFactory);
	}

	@Override
	public void clearProfileData(Long key 
			, EnvestMessageFactory errorFactory) throws EnvestException {
		UserInfoDao.clearProfileData(key, errorFactory);
	}

	@Override
	public Map<String, List<Object>> getProfileData(Long userKey) throws EnvestException  {
		return UserInfoDao.getProfileData(userKey);
	}
	
}
