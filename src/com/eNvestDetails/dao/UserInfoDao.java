package com.eNvestDetails.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Factories.IErrorMessageFactory;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dto.AccountsDTO;
import com.eNvestDetails.dto.AddressDTO;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserEmailDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.eNvestDetails.dto.UserPhoneDTO;
import com.eNvestDetails.dto.UserProfileDataDTO;
import com.eNvestDetails.util.ConvertBeanToDTO;
import com.eNvestDetails.util.HibernateUtils;

public class UserInfoDao {
	
	@Autowired
	private IErrorMessageFactory errorMessageFactory = null;
	private static Logger log = Logger.getLogger(UserInfoDao.class.getName());
	
	@Autowired
	private ConvertBeanToDTO convertBeanToDTO;
	
	public Long saveUserInfo(EnvestResponse saveRespone) throws EnvestException{
		return saveUserInfo(saveRespone, true);
	}
	
	public Long saveUserInfo(EnvestResponse saveRespone, boolean saveAccesToken) throws EnvestException{
		
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			UserInfoDTO dto = getUserInfoDetail(saveRespone.getUserKey());
			Map<String, Object> data = convertBeanToDTO.getUserInfoDTO(saveRespone, dto);
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
				session.saveOrUpdate(accessToken);
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
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;	
		}finally{
			session.close();
		}
		
		return userInfoDTO.getUserkey();
	}
	
	public UserInfoDTO getUserInfoDetail(long key) throws EnvestException{
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
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{
			session.close();
		}
		return userInfoDTO;			
	}
	
	public long createUser(String userID,String password, MessageFactory message) throws EnvestException{

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
				throw new EnvestException(errorMessageFactory.getFailureMessage(returnCode
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
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}catch(Exception e){
			log.error("Error occured while saving user",e);
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;	
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
			userInfoDTO = new UserInfoDTO();
			userInfoDTO.setEnvestUserID(userId.toUpperCase());
			List userExists = session.createCriteria(UserInfoDTO.class)
					.add(Restrictions.eq("envestUserID", userInfoDTO.getEnvestUserID())).
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
		return getAccesTokens(id,null);		
	}
	
	public static List<UserAccessTokenDTO> getAccesTokens(Long id,String bank){
		Session session = null;
		List<UserAccessTokenDTO> list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//need to add active flag condition
			Criteria criteria = session.createCriteria(UserAccessTokenDTO.class);
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
	
	/*public static void saveUserProfile(List<UserProfileDTO> userProfile)throws EnvestException{
		log.info("inside method saveUserProfile");
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			for(UserProfileDTO adto : userProfile){
				session.saveOrUpdate(adto);
			}
			session.getTransaction().commit();
		}catch (HibernateException e) {
			log.error("Error occured while getting user info",e);
			throw new EnvestException(new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
					,e.getMessage()
					,null
					,"failure")) ;	
					
		}finally{
			session.close();
		}			
	}*/
	
	public void saveUserProfileData(List<UserProfileDataDTO> userProfile)throws EnvestException{
		log.info("inside method saveUserProfileData");
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			for(UserProfileDataDTO adto : userProfile){
				session.saveOrUpdate(adto);
			}
			session.getTransaction().commit();
		}catch (HibernateException e) {
			log.error("Error occured while getting user info",e);
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{
			session.close();
		}			
	}
	
	/*public static List<UserProfileDTO> getUserProfile(Long userKey) throws EnvestException{
		Session session = null;
		List<UserProfileDTO> list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//need to add active flag condition
			list = session.createCriteria(UserProfileDTO.class).add(Restrictions.eq("userKey", userKey)).list();
		}catch (HibernateException e) {
			log.error("Error occured while getting access token",e);
			throw new EnvestException(new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
					,e.getMessage()
					,null
					,"failure")) ;			
		}finally{
			session.close();
		}
		return list;	
	}*/
	
	public List<UserProfileDataDTO> getUserProfileData(Long userKey) throws EnvestException{
		Session session = null;
		List<UserProfileDataDTO> list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//need to add active flag condition
			list = session.createCriteria(UserProfileDataDTO.class).add(Restrictions.eq("userKey", userKey)).list();
		}catch (HibernateException e) {
			log.error("Error occured while getting access token",e);
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;			
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
}
