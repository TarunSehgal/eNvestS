package com.envest.dal;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envest.dal.dao.ProductDao;
import com.envest.dal.dto.ProductDTO;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.exceptions.ErrorMessage;
import com.envest.services.components.util.ConvertBeanToDTO;
import com.envest.services.components.util.HibernateUtils;
import com.envest.services.response.EnvestResponse;

@Component
public class ProductDaoService implements IProductDaoService {

	@Override
	public List<ProductDTO> getAllProducts() throws EnvestException {
		return ProductDao.getAllProducts();
	}

	@Override
	public ProductDTO getProducts(int ProductId) throws EnvestException  {
		return ProductDao.getProducts(ProductId);
	}

}
