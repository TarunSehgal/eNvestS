package com.eNvestDetails.DAL;

import java.util.List;

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.DTO.ProductDTO;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.EnvestMessageFactory;

public interface IProductDaoService extends IDAOAdaptor {
	public List<ProductDTO> getAllProducts();
	public ProductDTO getProducts(int ProductId);
}
