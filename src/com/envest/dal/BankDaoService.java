package com.envest.dal;

import org.springframework.stereotype.Component;

import com.envest.dal.dao.BankDao;
import com.envest.dal.dto.BankDTO;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;

@Component
public class BankDaoService implements IBankDaoService {

	@Override
	public BankDTO getBankInfo(int bankId, EnvestMessageFactory errorFactory) throws EnvestException {
		// TODO Auto-generated method stub
		return BankDao.getBankInfo(bankId, errorFactory);
	}

	@Override
	public void addNewBank(int bankId, String bankName, Double interest, MessageFactory message,
			EnvestMessageFactory errorFactory) throws EnvestException {
		BankDao.addNewBank(bankId, bankName, interest, message, errorFactory);		
	}

}
