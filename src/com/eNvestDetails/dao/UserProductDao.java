package com.eNvestDetails.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;






import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dto.AccountsDTO;
import com.eNvestDetails.dto.AddressDTO;
import com.eNvestDetails.dto.BankDTO;
import com.eNvestDetails.dto.UserProductDTO;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserEmailDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.eNvestDetails.dto.UserPhoneDTO;
import com.eNvestDetails.util.ConvertBeanToDTO;
import com.eNvestDetails.util.HibernateUtils;

public class UserProductDao {
	
	private static Logger log = Logger.getLogger(UserProductDao.class.getName());
	
		
	public static List<UserProductDTO> getAllProduct(Long userKey) throws EnvestException{
		log.info("inside method getAllProducts");
		List<UserProductDTO> productsList = new ArrayList<UserProductDTO>();
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List products = session.createCriteria(UserProductDTO.class).add(Restrictions.eq("USER_KEY", userKey)).list();	
			if(products != null && products.size()>0)
			{
				for(int i=0; i<products.size(); i++)
				{
					productsList.add((UserProductDTO) products.get(i));
				}
			}
		}catch (HibernateException e) {
			log.error("Error occured while getting all products",e);
			throw new EnvestException(new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
					,e.getMessage()
					,null
					,"failure")) ;	
					
		}finally{
			session.close();
		}
		return productsList;	
	}
	public static UserProductDTO getProduct(String userId) throws EnvestException{
		log.info("inside method getproduct");
		UserProductDTO productDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List productList = session.createCriteria(UserProductDTO.class).add(Restrictions.eq("USER_ID", userId)).list();
			if(null != productList && productList.size() > 0){
				productDTO = (UserProductDTO)productList.get(0);
			}			
		}catch (HibernateException e) {
			log.error("Error occured while getting product",e);
			throw new EnvestException(new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
					,e.getMessage()
					,null
					,"failure")) ;	
					
		}finally{
			session.close();
		}
		return productDTO;			
	}
	
		
	public static int addNewProduct(UserProductDTO productDTO, MessageFactory message) throws EnvestException{

		Session session = null;
		int returnCode = EnvestConstants.RETURN_CODE_SUCCESS;
		try{
			log.info("in method addNewProduct");
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List bankExists = session.createCriteria(UserProductDTO.class).add(Restrictions.eq("USER_PRODUCT_ID", productDTO.getUserProductId())).list();
			if(null != bankExists && bankExists.size() > 0){
				returnCode = EnvestConstants.RETURN_CODE_USER_ALREADY_EXISTS;
				log.info("product already exists"+productDTO.getUserProductId());
				throw new EnvestException(new ErrorMessage(returnCode
						,message.getMessage("message.ProductAlreadyExist")
						,null
						,message.getMessage("message.failure"))) ;				
			}else {
				productDTO.setPurchaseDate(new Date());				
			}
			session.save(productDTO);
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			log.error("Error occured while saving adding bank",e);
			throw new EnvestException(new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
					,e.getMessage()
					,null
					,message.getMessage("message.failure"))) ;	
					
		}finally{
			session.close();
		}
		return productDTO.getUserProductId();
	}
}
