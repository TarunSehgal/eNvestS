package com.envest.dal;

import java.util.List;

import com.envest.dal.dto.ProductDTO;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;

public interface IProductDaoService extends IDAOAdaptor {
	public List<ProductDTO> getAllProducts();
	public ProductDTO getProducts(int ProductId);
}
