/*package test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.Factories.IErrorMessageFactory;
import com.eNvestDetails.util.Product.Product;
import com.eNvestDetails.util.Product.ProductUtil;

public class AnnuityCalculatorTest {
	@Autowired
	ErrorMessageFactory fac;
	@Test
	public void getTransaction() throws Exception {
		//ProductService service = new ProductService();
		ErrorMessageFactory fac = new ErrorMessageFactory();
		ErrorMessage msg = fac.getSuccessMessage(5, "test");
		ProductUtil util = new ProductUtil();
		int val = util.SaveUserProduct(1002, 5000, 5000, 1.5, (long) 1001);
		List<Product> list = util.GetUserProduct((long) 1001);
		//assertNotNull(util);
	}
	

}
*/