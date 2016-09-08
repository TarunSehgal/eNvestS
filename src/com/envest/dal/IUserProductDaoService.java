package com.envest.dal;

import java.util.List;

import com.envest.dal.dto.UserProductDTO;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;

public interface IUserProductDaoService extends IDAOAdaptor {
	public int addNewProduct(UserProductDTO productDTO, MessageFactory message, EnvestMessageFactory errorFactory)
			throws EnvestException;

	public UserProductDTO getProduct(String userId, EnvestMessageFactory errorFactory) throws EnvestException;

	public List<UserProductDTO> getAllProduct(Long userKey, EnvestMessageFactory errorFactory) throws EnvestException;
}
