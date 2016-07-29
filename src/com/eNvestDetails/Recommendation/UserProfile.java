package com.eNvestDetails.Recommendation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.RecommendationEngine.AbstractRule;
import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.util.UserAccountServiceUtil;
import com.eNvestDetails.util.UserServiceUtil;

public class UserProfile extends AbstractRule {
	
	private static Logger log = Logger.getLogger(UserProfile.class.getName()); 

	@Autowired
	private UserServiceUtil userServiceUtil;
	
	@Autowired
	private UserAccountServiceUtil accountServiceUtil;
	
	protected boolean makeDecision(Map<String,Object> arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}

	
	protected Map<String,Object> doWork(Map<String,Object> arg) throws Exception {
		log.info("inside doWork method in UserProfile");
		Map<String,AccountDetail.AccountProfile> profileData = null;
		try{
			EnvestResponse eNvestRes = (EnvestResponse) arg.get(EnvestConstants.ENVEST_RESPONSE);
			UserInfo info = (UserInfo)accountServiceUtil.getAccountAndTransaction(((UserInfo)eNvestRes).getUserKey(), EnvestConstants.GET_ACCOUNT_TRANSACTIONS);
			
			List<TransactionDetail> transactionList = info.getTransaction();
			
			Map<String, String> categories = userServiceUtil.getCategories();
			double bankFee;
			profileData = new HashMap<String,AccountDetail.AccountProfile>(10);
			
			for(TransactionDetail transaction : transactionList){
				AccountDetail.AccountProfile accProfile = null;
				if(null != profileData.get(transaction.getAccountId())){
					accProfile = profileData.get(transaction.getAccountId());
				}else{
					accProfile = new  AccountDetail.AccountProfile();
					profileData.put(transaction.getAccountId(), accProfile);
				}
				String categoryID = transaction.getCategoryId();
				String categoryHierarchy = categories.get(categoryID);
				if(categoryHierarchy.contains("Bank Fees")){
					accProfile.setMonthlyFee(accProfile.getMonthlyFee() +transaction.getAmount());					
				}
				if(categoryHierarchy.contains("Interest")){
					accProfile.setMonthlyInterest(accProfile.getMonthlyInterest() +transaction.getAmount());		
				}
				if(categoryHierarchy.contains("Service,Utilities")){
					accProfile.setUtilityBills(accProfile.getUtilityBills() +transaction.getAmount());		
				}
				if(categoryHierarchy.contains("Payment,Credit Card")){
					accProfile.setCreditBills(accProfile.getCreditBills() +transaction.getAmount());		
				}
				if(categoryHierarchy.contains("Payment,Loan")){
					accProfile.setLoanPayment(accProfile.getLoanPayment() +transaction.getAmount());		
				}
				
				if(categoryHierarchy.contains("Transfer,Payroll")){
					accProfile.setSalary(accProfile.getSalary() +transaction.getAmount());		
					accProfile.setEmployer(transaction.getName());
				}
				if(transaction.getAmount() < 0.0){
					accProfile.setInflow(accProfile.getInflow()+ (transaction.getAmount() * (-1.0)));
					accProfile.setOutflow(0.0);
				}else{
					accProfile.setOutflow(accProfile.getOutflow() + (transaction.getAmount()* (-1.0)));
					accProfile.setInflow(0.0);
				}
				
			}
			
		}catch (Exception e){
			
		}
		arg.put(EnvestConstants.USER_PROFILE, profileData);
		return arg;
	}
}	