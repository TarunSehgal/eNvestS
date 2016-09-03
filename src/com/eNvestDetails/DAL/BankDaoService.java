package com.eNvestDetails.DAL;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.util.ConvertBeanToDTO;
import com.eNvestDetails.util.HibernateUtils;

@Component
class BankDaoService implements IBankDaoService {

	@Override
	public BankDTO getBankInfo(int bankId, ErrorMessageFactory errorFactory) throws EnvestException {
		// TODO Auto-generated method stub
		return BankDao.getBankInfo(bankId, errorFactory);
	}

	@Override
	public void addNewBank(int bankId, String bankName, Double interest, MessageFactory message,
			ErrorMessageFactory errorFactory) throws EnvestException {
		BankDao.addNewBank(bankId, bankName, interest, message, errorFactory);		
	}

}
