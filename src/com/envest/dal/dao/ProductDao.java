package com.envest.dal.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.envest.dal.dto.ProductDTO;
import com.envest.services.components.util.HibernateUtils;

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
