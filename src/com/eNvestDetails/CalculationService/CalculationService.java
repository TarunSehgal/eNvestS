package com.eNvestDetails.CalculationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.util.UserAccountServiceUtil;

@CrossOrigin(origins= "*")
@RestController
public class CalculationService {	
	@Autowired
	private InterestCalculator interestCalculator = null;
	
	@RequestMapping(value="/CalculationService/getInterest",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public double getInterest(@RequestParam("principle") double principle, @RequestParam("tenor") int tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years){
		return interestCalculator.CalculateInterest(principle, tenor, annualInterestRate, years);		
	}	
	
	@RequestMapping(value="/CalculationService/getMaturityAmount",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public double getMaturityAmount(@RequestParam("principle") double principle, @RequestParam("tenor") int tenor, @RequestParam("annualInterestRate") double annualInterestRate,@RequestParam("years") double years){
		return interestCalculator.CalculateMaturity(principle, tenor, annualInterestRate, years);		
	}	
}
