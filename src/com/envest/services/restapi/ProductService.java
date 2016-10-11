package com.envest.services.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.util.Calculation.Response;
import com.envest.services.components.util.Product.Product;
import com.envest.services.components.util.Product.ProductType;
import com.envest.services.components.util.Product.ProductUtil;
import com.envest.services.components.util.Product.SaveProductReponse;
import com.envest.services.components.util.Product.StatusCode;

@CrossOrigin(origins= "*")
@RestController
public class ProductService {
	
	@Autowired
	private ProductUtil productUtil = null;
	
	@RequestMapping(value="/ProductService/getAvailableProducts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<Product> getAvailableProducts() throws EnvestException
	{
		return productUtil.GetAvailableProducts();
	}
	
	@RequestMapping(value="/ProductService/getRecommendedProducts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<Product> getRecommendedProducts(@RequestParam("userKey") Long userKey) throws Exception
	{
		return productUtil.GetRecommendedProducts(userKey);
	}
	
	@RequestMapping(value="/ProductService/getAvailableUserProducts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<Product> getAvailableUserProducts(@RequestParam("userKey") Long userKey) throws EnvestException
	{
		return productUtil.GetUserProduct(userKey);
	}
	
	@RequestMapping(value="/ProductService/getSelectedProducts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<Product> getSelectedProducts(@RequestParam("productType") ProductType productType)
	{
		return productUtil.GetAvailableProducts(productType);
	}
	
	@RequestMapping(value="/ProductService/recalculateProduct",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Product recalculateProduct(@RequestParam("productId") int productId, @RequestParam("noOfYears") double noOfYears, @RequestParam("principle") double principle, @RequestParam("compoundingTenor") String compoundingTenor) throws Exception
	{
		return productUtil.recalculateProduct(productId, noOfYears, principle, compoundingTenor);
	}
	
	@RequestMapping(value="/ProductService/saveUserProduct",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public SaveProductReponse saveUserProduct(@RequestParam("userKey") Long userKey, @RequestParam("productId") int productId, @RequestParam("interestRate") double interestRate, @RequestParam("principle") double principle, @RequestParam("valueAtMaturity") double valueAtMaturity) throws Exception
	{
		int id = productUtil.SaveUserProduct(productId, principle,valueAtMaturity,interestRate, userKey);
		return new SaveProductReponse(userKey, id, StatusCode.Success );
	}
}
