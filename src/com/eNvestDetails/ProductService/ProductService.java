package com.eNvestDetails.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.util.Calculation.Response;
import com.eNvestDetails.util.Product.Product;
import com.eNvestDetails.util.Product.ProductType;
import com.eNvestDetails.util.Product.ProductUtil;
import com.eNvestDetails.util.Product.SaveProductReponse;

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
	
	@RequestMapping(value="/ProductService/saveProduct",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public SaveProductReponse saveProduct(@RequestParam("userId") String userId, @RequestParam("product") Product product, @RequestParam("noOfYears") double noOfYears, @RequestParam("principle") double principle, @RequestParam("compoundingTenor") String compoundingTenor) throws Exception
	{
		return productUtil.SaveProduct(userId, product.productId, noOfYears, principle, compoundingTenor);
	}
}
