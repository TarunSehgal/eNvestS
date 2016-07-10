package com.eNvestDetails.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;



import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dto.AddressDTO;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserEmailDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.eNvestDetails.dto.UserPhoneDTO;
import com.eNvestDetails.util.ConvertBeanToDTO;
import com.eNvestDetails.util.HibernateUtils;

public class UserInfoDao {
	
	private static Logger log = Logger.getLogger(UserInfoDao.class.getName());
	
	
	public static Long saveUserInfo(EnvestResponse saveRespone){
		
		UserInfoDTO userInfoDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			Map<String, Object> data = ConvertBeanToDTO.getUserInfoDTO(saveRespone);
			session.beginTransaction();
			userInfoDTO = (UserInfoDTO)data.get(ConvertBeanToDTO.USERINFODTO);			
			List userExists = null;//session.createCriteria(UserInfoDTO.class).add(Restrictions.eq("userID", userInfoDTO.getUserID())).list();
			if(null == userExists || userExists.size() == 0){
				session.saveOrUpdate(userInfoDTO);
			}
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
			
			UserAccessTokenDTO accessToken = (UserAccessTokenDTO)data.get(ConvertBeanToDTO.USERACCESSTOKEN);
			if(null != accessToken){
				accessToken.setUserKey(userInfoDTO.getUserkey());
				session.saveOrUpdate(accessToken);
			}
						//session.save(arg0)
			session.getTransaction().commit();
		} catch (HibernateException e) {
			log.error("Error occured while saving user info",e);
			throw e;
		}finally{
			session.close();
		}
		
		return userInfoDTO.getUserkey();
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
		Session session = null;
		List<UserAccessTokenDTO> list = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			//need to add active flag condition
			list = session.createCriteria(UserAccessTokenDTO.class).add(Restrictions.eq("userKey", id)).list();
		}catch (HibernateException e) {
			log.error("Error occured while getting access token",e);
			throw e;		
		}finally{
			session.close();
		}
		return list;
		
	}
}
