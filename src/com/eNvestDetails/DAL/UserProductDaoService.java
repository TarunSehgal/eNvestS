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
public class UserProductDaoService implements IUserProductDaoService {

	@Override
	public int addNewProduct(UserProductDTO productDTO, MessageFactory message, ErrorMessageFactory errorFactory)
			throws EnvestException {
		return UserProductDao.addNewProduct(productDTO, message, errorFactory);
	}

	public UserProductDTO getProduct(String userId, ErrorMessageFactory errorFactory) throws EnvestException {
		return UserProductDao.getProduct(userId, errorFactory);
	}

	public List<UserProductDTO> getAllProduct(Long userKey, ErrorMessageFactory errorFactory) throws EnvestException {
		return UserProductDao.getAllProduct(userKey, errorFactory);
	}

}
