package com.envest.dal.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.envest.dal.dto.BankDTO;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.util.HibernateUtils;

public class BankDao {
		
	private static Logger log = Logger.getLogger(BankDao.class.getName());	
	
	public static BankDTO getBankInfo(int bankId, EnvestMessageFactory errorFactory) throws EnvestException{
		log.info("inside method getBankInfo");
		BankDTO bankDTO = null;
		Session session = null;
		try{
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List bankExist = session.createCriteria(BankDTO.class).add(Restrictions.eq("BANK_ID", bankId)).list();
			if(null != bankExist && bankExist.size() > 0){
				bankDTO = (BankDTO)bankExist.get(0);
			}			
		}catch (HibernateException e) {
			log.error("Error occured while getting bank info",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{
			session.close();
		}
		return bankDTO;			
	}
		
	public static void addNewBank(int bankId,String bankName, Double interest, MessageFactory message, EnvestMessageFactory errorFactory) throws EnvestException{

		BankDTO bankDTO = null;
		Session session = null;
		int returnCode = EnvestConstants.RETURN_CODE_SUCCESS;
		try{
			log.info("in method createUser");
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			List bankExists = session.createCriteria(BankDTO.class).add(Restrictions.eq("BANK_ID", bankId)).list();
			if(null != bankExists && bankExists.size() > 0){
				returnCode = EnvestConstants.RETURN_CODE_USER_ALREADY_EXISTS;
				log.info("user already exists"+bankId);
				throw new EnvestException(errorFactory.getFailureMessage(returnCode
						,message.getMessage("message.bankAlreadyExist"))) ;				
			}else {
				bankDTO = new BankDTO();
				bankDTO.setBankId(bankId);
				bankDTO.setBankName(bankName);
				bankDTO.setInterest(interest);				
			}
			session.save(bankDTO);
			session.getTransaction().commit();
			
		}catch (HibernateException e) {
			log.error("Error occured while saving adding bank",e);
			throw new EnvestException(errorFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{
			session.close();
		}
	}	
}
