package com.eNvestDetails.dao;

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
import com.eNvestDetails.Factories.IErrorMessageFactory;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dto.AccountsDTO;
import com.eNvestDetails.dto.AddressDTO;
import com.eNvestDetails.dto.BankDTO;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserEmailDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.eNvestDetails.dto.UserPhoneDTO;
import com.eNvestDetails.util.ConvertBeanToDTO;
import com.eNvestDetails.util.HibernateUtils;

public class BankDao {
	
	@Autowired
	private IErrorMessageFactory errorMessageFactory = null;
	private static Logger log = Logger.getLogger(BankDao.class.getName());			
	
	public BankDTO getBankInfo(int bankId) throws EnvestException{
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
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{
			session.close();
		}
		return bankDTO;			
	}
		
	public void addNewBank(int bankId,String bankName, Double interest, MessageFactory message) throws EnvestException{

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
				throw new EnvestException(errorMessageFactory.getFailureMessage(returnCode
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
			throw new EnvestException(errorMessageFactory.getServerErrorMessage(e.getMessage())) ;	
					
		}finally{
			session.close();
		}
	}	
}
