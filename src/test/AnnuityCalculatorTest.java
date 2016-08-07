/*package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.eNvestDetails.ProductService.ProductService;
import com.eNvestDetails.util.PlaidClient;
import com.eNvestDetails.util.Product.Product;
import com.eNvestDetails.util.Product.ProductUtil;

public class AnnuityCalculatorTest {
	
	@Test
	public void getTransaction() throws Exception {
		//ProductService service = new ProductService();
		ProductUtil util = new ProductUtil();
		int val = util.SaveUserProduct(1002, 5000, 5000, 1.5, (long) 1001);
		List<Product> list = util.GetUserProduct((long) 1001);
		assertNotNull(util);
	}
	

}
*/