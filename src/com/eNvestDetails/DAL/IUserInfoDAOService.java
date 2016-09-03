package com.eNvestDetails.DAL;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.Response.EnvestResponse;

public interface IUserInfoDAOService extends IDAOAdaptor{
	public Long saveUserInfo(EnvestResponse saveRespone, boolean saveAccesToken, ErrorMessageFactory errorFactory)
			throws EnvestException;

	public Long saveUserInfo(EnvestResponse saveRespone, ErrorMessageFactory errorFactory) throws EnvestException;

	public UserInfoDTO getUserInfoDetail(long key, ErrorMessageFactory errorFactory) throws EnvestException;

	public long createUser(String userID, String password, MessageFactory message, ErrorMessageFactory errorFactory)
			throws EnvestException;

	public int saveUser(Long userKey, String userID, String password);

	public UserInfoDTO authenticateUser(String userId, String password);

	public void saveAccessToken(UserAccessTokenDTO accessToken);

	public List<UserAccessTokenDTO> getAccesTokens(Long id);

	public UserAccessTokenDTO getAccesTokens(Long id, String bank);

	public void saveUserProfileData(List<UserProfileDataDTO> userProfile, ErrorMessageFactory errorFactory)
			throws EnvestException;

	public List<UserProfileDataDTO> getUserProfileData(Long userKey, ErrorMessageFactory errorFactory)
			throws EnvestException;

	public boolean testConnection();

	public void deleteUser(Long key 
			, ErrorMessageFactory errorFactory) throws EnvestException;

	public void clearProfileData(Long key 
			, ErrorMessageFactory errorFactory) throws EnvestException;

	public Map<String, List<Object>> getProfileData(Long userKey) throws HibernateException;
}
