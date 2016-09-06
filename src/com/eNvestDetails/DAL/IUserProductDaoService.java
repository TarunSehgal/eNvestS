package com.eNvestDetails.DAL;

import java.util.List;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.DTO.UserProductDTO;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.EnvestMessageFactory;

public interface IUserProductDaoService extends IDAOAdaptor {
	public int addNewProduct(UserProductDTO productDTO, MessageFactory message, EnvestMessageFactory errorFactory)
			throws EnvestException;

	public UserProductDTO getProduct(String userId, EnvestMessageFactory errorFactory) throws EnvestException;

	public List<UserProductDTO> getAllProduct(Long userKey, EnvestMessageFactory errorFactory) throws EnvestException;
}
