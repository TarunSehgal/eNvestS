package com.eNvestDetails.DAL;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.ErrorMessageFactory;

public interface IBankDaoService extends IDAOAdaptor {
	public BankDTO getBankInfo(int bankId, ErrorMessageFactory errorFactory) throws EnvestException;
	public void addNewBank(int bankId,String bankName, Double interest, MessageFactory message, ErrorMessageFactory errorFactory) throws EnvestException;
}
