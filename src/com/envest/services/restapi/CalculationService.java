package com.envest.services.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.envest.services.components.util.Calculation.AnnuityCalculator;
import com.envest.services.components.util.Calculation.GoalSeekCalculator;
import com.envest.services.components.util.Calculation.InterestCalculator;
import com.envest.services.components.util.Calculation.PayoutResponse;
import com.envest.services.components.util.Calculation.Response;

@CrossOrigin(origins= "*")
@RestController
public class CalculationService {	
	@Autowired
	private InterestCalculator interestCalculator = null;
	
	@Autowired
	private AnnuityCalculator annuityCalculator = null;
	
	@Autowired
	private GoalSeekCalculator goalSeekCalculator = null;
	
	@RequestMapping(value="/CalculationService/getInterest",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Response getInterest(@RequestParam("principle") double principle, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years) throws Exception{
		return interestCalculator.CalculateInterest(principle, tenor, annualInterestRate, years);		
	}	
	
	@RequestMapping(value="/CalculationService/getDueAnnuity",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Response getDueAnnuity(@RequestParam("cashFlow") double cashFlow, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years) throws Exception{
		return annuityCalculator.CalculateDueAnnity(cashFlow, years, annualInterestRate, tenor);		
	}	
	
	@RequestMapping(value="/CalculationService/getDueAnnuityPV",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public double getDueAnnuityPV(@RequestParam("cashFlow") double cashFlow, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years) throws Exception{
		return annuityCalculator.CalculateDueAnnityPV(cashFlow, years, annualInterestRate, tenor);		
	}
	
	@RequestMapping(value="/CalculationService/getOrdinaryAnnity",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Response getOrdinaryAnnity(@RequestParam("cashFlow") double cashFlow, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years) throws Exception{
		return annuityCalculator.CalculateOrdinaryAnnity(cashFlow, years, annualInterestRate, tenor);		
	}	
	
	@RequestMapping(value="/CalculationService/getOrdinaryAnnityPV",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public double getOrdinaryAnnityPV(@RequestParam("cashFlow") double cashFlow, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years) throws Exception{
		return annuityCalculator.CalculateOrdinaryAnnityPV(cashFlow, years, annualInterestRate, tenor);		
	}
	
	@RequestMapping(value="/CalculationService/getGoalSeekTargetYears",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Response getGoalSeekTargetYears(@RequestParam("cashFlow") double cashFlow, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("targetValue") double targetValue) throws Exception{
		return goalSeekCalculator.getAnnuityDueRequiredYears(targetValue, cashFlow, annualInterestRate, tenor);		
	}
	
	@RequestMapping(value="/CalculationService/getGoalSeekTargetCashFlow",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public PayoutResponse getGoalSeekTargetCashFlow(@RequestParam("targetValue") double targetValue, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years) throws Exception{
		return goalSeekCalculator.getAnnuityDueRequiredCashFlow(targetValue, years, annualInterestRate, tenor);		
	}
	
	@RequestMapping(value="/CalculationService/getAnnuityPayout",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public PayoutResponse getAnnuityPayout(@RequestParam("initialAmount") double initialAmount, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years) throws Exception{
		return annuityCalculator.getAnnuityPayout(initialAmount, years, annualInterestRate, tenor);		
	}
	
	@RequestMapping(value="/CalculationService/getAnnuityPayoutYears",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public PayoutResponse getAnnuityPayoutYears(@RequestParam("initialAmount") double initialAmount, @RequestParam("tenor") String tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("payout") double payout) throws Exception{
		return annuityCalculator.getAnnuityPayoutYears(initialAmount, payout, annualInterestRate, tenor);		
	}
}
