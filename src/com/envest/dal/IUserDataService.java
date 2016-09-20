package com.envest.dal;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.envest.dal.dao.UserInfoDao;
import com.envest.dal.dto.UserAccessTokenDTO;
import com.envest.dal.dto.UserInfoDTO;
import com.envest.dal.dto.UserProfileDataDTO;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.response.EnvestResponse;

public interface IUserDataService extends IDAOAdaptor{
	public Long saveUserInfo(EnvestResponse saveRespone, boolean saveAccesToken, EnvestMessageFactory errorFactory)
			throws EnvestException;

	public Long saveUserInfo(EnvestResponse saveRespone, EnvestMessageFactory errorFactory) throws EnvestException;

	public UserInfoDTO getUserInfoDetail(long key, EnvestMessageFactory errorFactory) throws EnvestException;

	public long registerUser(String userID, String password, MessageFactory message, EnvestMessageFactory errorFactory)
			throws EnvestException;

	public int saveUser(Long userKey, String userID, String password);

	public UserInfoDTO authenticateUser(String userId, String password);

	public void saveAccessToken(UserAccessTokenDTO accessToken);

	public List<UserAccessTokenDTO> getAccesTokens(Long id);

	public UserAccessTokenDTO getAccesTokens(Long id, String bank) throws Exception;
	public List<String> getAccesTokenList(Long id) throws Exception ;

	public String getAccesToken(Long id, String bank) throws Exception;

	public void saveUserProfileData(List<UserProfileDataDTO> userProfile, EnvestMessageFactory errorFactory)
			throws EnvestException;

	public List<UserProfileDataDTO> getUserProfileData(Long userKey, EnvestMessageFactory errorFactory)
			throws EnvestException;

	public boolean testConnection();

	public void deleteUser(Long key 
			, EnvestMessageFactory errorFactory) throws EnvestException;

	public void clearProfileData(Long key 
			, EnvestMessageFactory errorFactory) throws EnvestException;

	public Map<String, List<Object>> getProfileData(Long userKey) throws HibernateException;
}
