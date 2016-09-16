package com.envest.dal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.envest.dal.dto.AccountsDTO;
import com.envest.dal.dto.AddressDTO;
import com.envest.dal.dto.UserAccessTokenDTO;
import com.envest.dal.dto.UserEmailDTO;
import com.envest.dal.dto.UserInfoDTO;
import com.envest.dal.dto.UserPhoneDTO;
import com.envest.dal.dto.UserProfileDataDTO;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.util.ConvertBeanToDTO;
import com.envest.services.components.util.HibernateUtils;
import com.envest.services.response.EnvestResponse;

public class UserInfoDao {
		
	private static Logger log = Logger.getLogger(UserInfoDao.class.getName());
	
	public static Long saveUserInfo(EnvestResponse saveRespone, EnvestMessageFactory errorFactory) throws EnvestException{
		return saveUserInfo(saveRespone, true, errorFactory);
	}
	
	public static Long saveUserInfo(EnvestResponse saveRespone, boolean saveAccesToken, EnvestMessageFactory errorFactory) throws EnvestException{
		
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			Map<String, Object> data = ConvertBeanToDTO.getUserInfoDTO(saveRespone, errorFactory);
			session.beginTransaction();
			userInfoDTO = (UserInfoDTO)data.get(ConvertBeanToDTO.USERINFODTO);	
			List<UserAccessTokenDTO> accessTokenList = getAccesTokens(userInfoDTO.getUserkey());
			boolean userAlreadyLinked = false;
			if(null != accessTokenList && accessTokenList.size() > 0){
				userAlreadyLinked = true;
			}
			
			if(!userAlreadyLinked){
				session.saveOrUpdate(userInfoDTO);
				
				//session.save(userInfoDTO);
				List<AddressDTO> addressList = (List)data.get(ConvertBeanToDTO.ADDRESSDTO);
				for(AddressDTO adto : addressList){
					adto.setUserKey(userInfoDTO.getUserkey());
					session.saveOrUpdate(adto);
				}
				
				List<UserEmailDTO> emaillist = (List)data.get(ConvertBeanToDTO.USEREMAILDTO);			
				for(UserEmailDTO email : emaillist){
					email.setUserKey(userInfoDTO.getUserkey());
					session.saveOrUpdate(email);
				}
				
				List<UserPhoneDTO> phonelist = (List)data.get(ConvertBeanToDTO.USERPHONEDTO);			
				for(UserPhoneDTO phone : phonelist){
					phone.setUserKey(userInfoDTO.getUserkey());
					session.saveOrUpdate(phone);
				}
			}
			
			
			UserAccessTokenDTO accessToken = (UserAccessTokenDTO)data.get(ConvertBeanToDTO.USERACCESSTOKEN);
			if(null != accessToken && saveAccesToken){
				accessToken.setUserKey(userInfoDTO.getUserkey());
				List<UserAccessTokenDTO> alreadyExistAccessToken = getAccessTokensList(accessToken.getUserKey(), accessToken.getUserBank());
				if(!(alreadyExistAccessToken != null && alreadyExistAccessToken.size() > 0)){
					
					session.saveOrUpdate(accessToken);
				}
				
			}
			List<AccountsDTO> accList = (List)data.get(ConvertBeanToDTO.ACCOUNTDTO);
			for(AccountsDTO acc : accList){
				acc.setUserKey(userInfoDTO.getUserkey());
				session.saveOrUpdate(acc);
			}
			
						//session.save(arg0)
			session.getTransaction().commit();
		} catch (HibernateException e) {
			log.error("Error occured while saving user info",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage()));	
		}finally{
			session.close();
		}
		
		return userInfoDTO.getUserkey();
	}
	
	public static UserInfoDTO getUserInfoDetail(long key, EnvestMessageFactory errorFactory) throws EnvestException{
		log.info("inside method getUserInfoDetail");
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List userExists = session.createCriteria(UserInfoDTO.class).add(Restrictions.eq("userkey", key)).list();
			if(null != userExists && userExists.size() > 0){
				userInfoDTO = (UserInfoDTO)userExists.get(0);
			}			
		}catch (HibernateException e) {
			log.error("Error occured while getting user info",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{
			session.close();
		}
		return userInfoDTO;			
	}
	
	public static long createUser(String userID,String password, MessageFactory message, EnvestMessageFactory errorFactory) throws EnvestException{

		UserInfoDTO userInfoDTO = null;
		Session session = null;
		int returnCode = EnvestConstants.RETURN_CODE_SUCCESS;
		try{
			log.info("in method createUser");
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List userExists = session.createCriteria(UserInfoDTO.class).add(Restrictions.eq("envestUserID", userID)).list();
			if(null != userExists && userExists.size() > 0){
				returnCode = EnvestConstants.RETURN_CODE_USER_ALREADY_EXISTS;
				log.info("user already exists"+userID);				
				throw new EnvestException(errorFactory.getFailureMessage(returnCode
						,message.getMessage("message.userAlreadyExist"))) ;				
			}else {
				userInfoDTO = new UserInfoDTO();
				userInfoDTO.setIsActive("Y");
				userInfoDTO.setPassword(password);
				userInfoDTO.setEnvestUserID(userID.toUpperCase());
				userInfoDTO.setIsDeleted("N");
			}
			session.save(userInfoDTO);
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			log.error("Error occured while saving user",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}catch(Exception e){
			log.error("Error occured while saving user",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
		}
		finally{
			log.info("Starting to close session");
			session.close();
		}
		
		return userInfoDTO.getUserkey() ;
		
	
	}
	
	public static int saveUser(Long userKey,String userID,String password) {
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		int returnCode = EnvestConstants.RETURN_CODE_SUCCESS;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List userExists = session.createCriteria(UserInfoDTO.class).add(Restrictions.eq("userkey", userKey)).list();
			if(null != userExists && userExists.size() > 0){
				userInfoDTO = (UserInfoDTO)userExists.get(0);
			}else {
				returnCode = EnvestConstants.RETURN_CODE_USER_NOT_EXIST;
				return returnCode;
			}
			userInfoDTO.setIsActive("Y");
			userInfoDTO.setPassword(password);
			userInfoDTO.setEnvestUserID(userID.toUpperCase());
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			log.error("Error occured while saving user",e);
			throw e;		
		}finally{
			session.close();
		}
		
		return returnCode;
		
	}
	
	public static UserInfoDTO authenticateUser(String userId,String password){
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//userInfoDTO = new UserInfoDTO();
			//userInfoDTO.setEnvestUserID(userId.toUpperCase());
			List userExists = session.createCriteria(UserInfoDTO.class)
					.add(Restrictions.eq("envestUserID", userId.toUpperCase())).
					add(Restrictions.eq("isActive", "Y")).list();
			if(null == userExists || userExists.size() > 0){
				userInfoDTO = (UserInfoDTO)userExists.get(0);
				log.info("user retrived from authentication:Passord"+userInfoDTO.getPassword()+" userkey:"+ userInfoDTO.getUserkey());
			}
		}catch (HibernateException e) {
			log.error("Error occured while authenticating the user",e);
			throw e;		
		}finally{
			session.close();
		}
		return userInfoDTO;
	}

	public static void saveAccessToken(UserAccessTokenDTO accessToken){
		Session session = null;
		try{
			List<UserAccessTokenDTO> alreadyExistAccessToken = getAccessTokensList(accessToken.getUserKey(), accessToken.getUserBank());
			if(alreadyExistAccessToken != null && alreadyExistAccessToken.size() > 0){
				return;
			}
			
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(accessToken);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			log.error("Error occured while saving access token",e);
			throw e;		
		}finally{
			session.close();
		}
	}
	
	public static List<UserAccessTokenDTO> getAccesTokens(Long id){
		return getAccessTokensList(id,null);		
	}
	
	public static UserAccessTokenDTO getAccesTokens(Long id,String bank) throws Exception{
		
		List<UserAccessTokenDTO> tokens = getAccessTokensList(id, bank);		
		if(tokens != null && tokens.size() > 0)
		{
			return tokens.get(0);
		}
		else{
			throw new Exception("Access token not found");
		}
	}
	
	private static List<UserAccessTokenDTO> getAccessTokensList(Long id,String bank){
		Session session = null;
		List<UserAccessTokenDTO> list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//need to add active flag condition
			Criteria criteria = session.createCriteria(UserAccessTokenDTO.class);
			/*criteria.setProjection(Projections.distinct(Projections.projectionList()
				    .add(Projections.property("userBank"), "userBank")));*/
			criteria.add(Restrictions.eq("userKey", id));
			if(null != bank){
				criteria.add(Restrictions.eq("userBank", bank));
			}
			
			list = criteria.list();
		}catch (HibernateException e) {
			log.error("Error occured while getting access token",e);
			throw e;		
		}finally{
			session.close();
		}
		return list;		
	}
	

	
	
	public static void saveUserProfileData(List<UserProfileDataDTO> userProfile, EnvestMessageFactory errorFactory)throws EnvestException{
		log.info("inside method saveUserProfileData");
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			
			for(UserProfileDataDTO adto : userProfile){
				session.save(adto);
			}
			session.getTransaction().commit();
		}catch (HibernateException e) {
			log.error("Error occured while getting user info",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{			
			session.close();
		}			
	}
	

	public static List<UserProfileDataDTO> getUserProfileData(Long userKey, EnvestMessageFactory errorFactory) throws EnvestException{
		Session session = null;
		List<UserProfileDataDTO> list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//need to add active flag condition
			list = session.createCriteria(UserProfileDataDTO.class).add(Restrictions.eq("userKey", userKey)).list();
		}catch (HibernateException e) {
			log.error("Error occured while getting access token",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;			
		}finally{			
			session.close();
		}
		return list;	
	}
	
	public static boolean testConnection() {
		Session session = null;
		List list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//need to add active flag condition
			list = session.createCriteria(UserAccessTokenDTO.class).add(Restrictions.eq("userKey", -1L)).list();
		}catch (HibernateException e) {
			log.error("Error occured while getting access token",e);
			return false;		
		}finally{
			session.close();
		}
		return true;
	}
	
	public static void deleteUser(Long key 
			, EnvestMessageFactory errorFactory) throws EnvestException{
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			session.createQuery("delete from UserEmailDTO email where email.userKey = :userKey")
				.setLong("userKey", key).executeUpdate();
			session.createQuery("delete from UserPhoneDTO phone where phone.userKey = :userKey")
			.setLong("userKey", key).executeUpdate();
			session.createQuery("delete from AddressDTO addr where addr.userKey = :userKey")
			.setLong("userKey", key).executeUpdate();
			session.createQuery("delete from AccountsDTO acc where acc.userKey = :userKey")
			.setLong("userKey", key).executeUpdate();
			session.createQuery("delete from UserAccessTokenDTO tok where tok.userKey = :userKey")
			.setLong("userKey", key).executeUpdate();
			session.createQuery("delete from UserInfoDTO inf where inf.userkey = :userKey")
			.setLong("userKey", key).executeUpdate();
			session.createQuery("delete from UserProfileDataDTO pro where pro.userKey = :userKey")
			.setLong("userKey", key).executeUpdate();
			session.getTransaction().commit();
		}catch(HibernateException e){
			log.error("error deleting user",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
		}finally{
			session.close();
		}
	}
	
	public static void clearProfileData(Long key 
			, EnvestMessageFactory errorFactory) throws EnvestException{
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();	
			session.createQuery("delete from UserProfileDataDTO pro where pro.userKey = :userKey")
			.setLong("userKey", key).executeUpdate();
			session.getTransaction().commit();
		}catch(HibernateException e){
			log.error("error deleting user",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
		}finally{
			session.clear();
			session.close();
		}
	}
	
	public static Map<String,List<Object>> getProfileData(Long userKey) throws HibernateException{
		Session session = null;
		Map<String,List<Object>> returnData = new HashMap<String,List<Object>>(10);
		List<Object> list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			list = session.createCriteria(UserInfoDTO.class).add(Restrictions.eq("userkey", userKey)).list();
			returnData.put("UserInfoDTO", list);
			list = session.createCriteria(AddressDTO.class).add(Restrictions.eq("userKey", userKey)).list();
			returnData.put("AddressDTO", list);
			list = session.createCriteria(UserEmailDTO.class).add(Restrictions.eq("userKey", userKey)).list();
			returnData.put("UserEmailDTO", list);
			list = session.createCriteria(UserPhoneDTO.class).add(Restrictions.eq("userKey", userKey)).list();
			returnData.put("UserPhoneDTO", list);
			
			/*returnData = session.createQuery("from UserInfoDTO i,AddressDTO a,UserEmailDTO e,UserPhoneDTO p where"
					+ " i.userkey =a.userKey and i.userkey = e.userKey and i.userkey = p.userKey and i.userkey= :userKey")
					.setLong("userKey", userKey).list();*/
		}catch(HibernateException e){
			log.error("error getting getProfileData",e);
			throw e;
		}finally{
			session.close();
		}
		return returnData;
	}
}
