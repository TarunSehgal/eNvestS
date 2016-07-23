package com.eNvestDetails.Products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eNvestDetails.CalculationService.Response;

@CrossOrigin(origins= "*")
@RestController
public class ProductService {
	
	@Autowired
	private ProductUtil productUtil = null;
	
	@RequestMapping(value="/ProductService/getAvailableProducts",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public List<Product> getAvailableProducts()
	{
		return productUtil.GetAvailableProducts();
	}
	
	@RequestMapping(value="/ProductService/getProduct",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Product getProduct(@RequestParam("productId") int productId, @RequestParam("maturityDate") double maturityDate, @RequestParam("notional") double notional, @RequestParam("compoundingTenor") String compoundingTenor) throws Exception
	{
		return productUtil.getProduct(productId, maturityDate, notional, compoundingTenor);
	}
}
