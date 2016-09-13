/*package com.envest.tests;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.exceptions.SuccessMessage;
import com.envest.services.components.util.Product.Product;
import com.envest.services.components.util.Product.ProductUtil;
import com.envest.services.restapi.DataService;

public class AnnuityCalculatorTest {
	@Autowired
	EnvestMessageFactory fac;
	@Test
	public void getTransaction() throws Exception {
		//ProductService service = new ProductService();
		EnvestMessageFactory fac = new EnvestMessageFactory();
		SuccessMessage msg = fac.getSuccessMessage("test");
		ProductUtil util = new ProductUtil();
		int val = util.SaveUserProduct(1002, 5000, 5000, 1.5, (long) 1001);
		List<Product> list = util.GetUserProduct((long) 1001);
		//assertNotNull(util);
	}
	

}
*/