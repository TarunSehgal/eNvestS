/*package test;

import static org.junit.Assert.*;

import org.junit.Test;
import com.eNvestDetails.CalculationService.AnnuityCalculator;
import com.eNvestDetails.CalculationService.GoalSeekCalculator;
import com.eNvestDetails.CalculationService.PayoutResponse;
import com.eNvestDetails.CalculationService.Response;

public class AnnuityCalculatorTest {

	private AnnuityCalculator annuityCalculator = new AnnuityCalculator();
	private GoalSeekCalculator goalSeekCalculator = new GoalSeekCalculator();
	
	@Test
	public void getTransaction() throws Exception {
		Response val = annuityCalculator.CalculateDueAnnity(100, 5,0.015, "1Y");
		assertNotNull(val);
		//assertSame(val.interest, 0);
		assertTrue(val.principle== 500.0);
		//assertSame(val.maturity, 0);
		assertTrue(val.noOfYears== 5.0);
	}
	
	@Test
	public void getAnnuityPayout() throws Exception {
		PayoutResponse val = annuityCalculator.getAnnuityPayout(10000, 5, 1.5, "1m");
		assertNotNull(val);
		//assertSame(val.interest, 0);
		assertTrue(val.principle== 500.0);
		//assertSame(val.maturity, 0);
		assertTrue(val.noOfYears== 5.0);
	}
	
	@Test
	public void getAnnuityPayoutYears() throws Exception {
		PayoutResponse val = annuityCalculator.getAnnuityPayoutYears(10000, 173.09, 1.5, "1m");
		assertNotNull(val);
		//assertSame(val.interest, 0);
		assertTrue(val.principle== 500.0);
		//assertSame(val.maturity, 0);
		assertTrue(val.noOfYears== 5.0);
	}
	
	@Test
	public void getRequiredCashFlow() throws Exception {
		PayoutResponse val = goalSeekCalculator.getAnnuityDueRequiredCashFlow(50000, 3,0.03, "1m");
	}
	
	@Test
	public void getRequiredYears() throws Exception {
		Response val = goalSeekCalculator.getAnnuityDueRequiredYears(50000, 1329,0.03, "1m");
		assertNotNull(val);
	}

}
*/