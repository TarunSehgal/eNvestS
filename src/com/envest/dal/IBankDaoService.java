package com.envest.dal;

import com.envest.dal.dto.BankDTO;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;

public interface IBankDaoService extends IDAOAdaptor {
	public BankDTO getBankInfo(int bankId, EnvestMessageFactory errorFactory) throws EnvestException;
	public void addNewBank(int bankId,String bankName, Double interest, MessageFactory message, EnvestMessageFactory errorFactory) throws EnvestException;
}
