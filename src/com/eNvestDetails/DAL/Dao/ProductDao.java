package com.eNvestDetails.DAL.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;






import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.Dto.AccountsDTO;
import com.eNvestDetails.DAL.Dto.AddressDTO;
import com.eNvestDetails.DAL.Dto.ProductDTO;
import com.eNvestDetails.DAL.Dto.UserAccessTokenDTO;
import com.eNvestDetails.DAL.Dto.UserEmailDTO;
import com.eNvestDetails.DAL.Dto.UserInfoDTO;
import com.eNvestDetails.DAL.Dto.UserPhoneDTO;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.util.ConvertBeanToDTO;
import com.eNvestDetails.util.HibernateUtils;

public class ProductDao {
	
	private static Logger log = Logger.getLogger(ProductDao.class.getName());
		
	public static List<ProductDTO> getAllProducts() {
		List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List products = session.createCriteria(ProductDTO.class).list();
			if(null != products && products.size() > 0){
				for(int i=0;i<products.size();i++)
				{
					productDTOList.add((ProductDTO) products.get(i));
				}			
			}			
		}catch (HibernateException e) {
			log.error("Error occured while saving user",e);
			throw e;		
		}finally{
			session.close();
		}
		
		return productDTOList;
		
	}
	
	public static ProductDTO getProducts(int ProductId) {
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List products = session.createCriteria(ProductDTO.class).add(Restrictions.eq("PRODUCT_ID", ProductId)).list();
			if(null != products && products.size() > 0){
				return (ProductDTO) products.get(0);	
			}			
		}catch (HibernateException e) {
			log.error("Error occured while saving user",e);
			throw e;		
		}finally{
			session.close();
		}		
		return null;		
	}	
}
