package com.eNvestDetails.DAL;

import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.DAO.BankDao;
import com.eNvestDetails.DAL.DTO.BankDTO;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.EnvestMessageFactory;

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
