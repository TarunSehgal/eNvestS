package com.eNvestDetails.DAL;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.DTO.BankDTO;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.EnvestMessageFactory;

public interface IBankDaoService extends IDAOAdaptor {
	public BankDTO getBankInfo(int bankId, EnvestMessageFactory errorFactory) throws EnvestException;
	public void addNewBank(int bankId,String bankName, Double interest, MessageFactory message, EnvestMessageFactory errorFactory) throws EnvestException;
}
