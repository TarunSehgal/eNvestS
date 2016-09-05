package com.eNvestDetails.DAL;

import java.util.List;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.ErrorMessageFactory;

public interface IUserProductDaoService extends IDAOAdaptor {
	public int addNewProduct(UserProductDTO productDTO, MessageFactory message, ErrorMessageFactory errorFactory)
			throws EnvestException;

	public UserProductDTO getProduct(String userId, ErrorMessageFactory errorFactory) throws EnvestException;

	public List<UserProductDTO> getAllProduct(Long userKey, ErrorMessageFactory errorFactory) throws EnvestException;
}
