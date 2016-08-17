package com.eNvestDetails.Recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import com.eNvestDetails.Config.MessageFactory;

import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.RecommendationEngine.AbstractRule;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.Response.UserProfileResponse;
import com.eNvestDetails.UserProfile.UserProfileDataElement;
import com.eNvestDetails.UserProfile.UserProfileDataMapping;
import com.eNvestDetails.UserProfile.UserProfileFactory;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dao.UserInfoDao;
//import com.eNvestDetails.dto.UserProfileDTO;
import com.eNvestDetails.dto.UserProfileDataDTO;
import com.eNvestDetails.util.UserAccountServiceUtil;
import com.eNvestDetails.util.UserServiceUtil;

public class UserProfile extends AbstractRule {
	
	private static Logger log = Logger.getLogger(UserProfile.class.getName()); 

	@Autowired
	private UserServiceUtil userServiceUtil;
	
	@Autowired
	private ErrorMessageFactory errorFactory = null;
	
	@Autowired
	private UserAccountServiceUtil accountServiceUtil;
	
	
	private UserProfileFactory profileFactory;
	
	protected boolean makeDecision(Map<String,Object> arg) throws Exception {
		log.info("inside make decision method in testoppurtunity");
		return Boolean.parseBoolean(getRuleEnable());
	}
	
	
	private static final String INTEREST = "Interest";
	
	private static final String KEY_INTEREST_TOTAL = "1";
	private static final String KEY_BANK_FEE_TOTAL = "2";
	private static final String KEY_UTILITY_BILL_TOTAL = "3";
	private static final String KEY_CREDIT_BILL_TOTAL = "4";
	private static final String KEY_LOAN_AMT = "5";
	private static final String KEY_SALARY_AMT = "6";
	private static final String KEY_TAX_REFUND = "7";
	private static final String KEY_TAX_PAYMENT = "8";
	private static final String KEY_BILL_PAY = "9";
	private static final String KEY_INFLOW_TOTAL = "10";
	private static final String KEY_OUTFLOW_TOTAL = "11";
	private static final String KEY_RENT_PAYMENT = "12";
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	UserProfileDataMapping profileMapping;
	
	protected Map<String,Object> doWork(Map<String,Object> arg) throws Exception {
		log.info("inside doWork method in UserProfile");
		Map<String,List<UserProfileResponse>> profileData = null;
		Map <String,Object> userProfileData = new HashMap<String,Object>();
		UserProfileDataDTO profileDataDTO = null;
		List<UserProfileDataDTO> saveProfileDataList = null;
		Long userKey = null;
		try{
			if(null == arg || null == arg.get(EnvestConstants.ENVEST_RESPONSE) ){
				return arg;
			}			
			EnvestResponse eNvestRes = (EnvestResponse) arg.get(EnvestConstants.ENVEST_RESPONSE);
			userKey = ((UserInfo)eNvestRes).getUserKey();
			UserInfo info = (UserInfo)accountServiceUtil.getAccountAndTransaction(userKey, EnvestConstants.GET_ACCOUNT_TRANSACTIONS);
			
			List<TransactionDetail> transactionList = info.getTransaction();
			Collections.sort(transactionList);
			
			Map<String, String> categories = userServiceUtil.getCategories();
			//clear profile data for fresh building
			UserInfoDao.clearProfileData(userKey, errorFactory);
			/*double bankFee = 0.0;
			double interest = 0.0;
			double utilityBill = 0.0;
			double creditBill = 0.0;
			double loanAmount = 0.0;
			double salary = 0.0;
			String employer = null;
			double taxPayment = 0.0;
			double taxRefund = 0.0;
			double billPay = 0.0;
			double inflow = 0.0;
			double outflow = 0.0;
			double rentPayment = 0.0;*/
			
			profileData = new HashMap<String,List<UserProfileResponse>>(10);
			saveProfileDataList = new ArrayList<UserProfileDataDTO>();
			profileFactory = new UserProfileFactory();
			UserProfileDataElement.initializeProfileMap();
			for(TransactionDetail transaction : transactionList){
				
				String categoryID = transaction.getCategoryId();
				String categoryHierarchy = categories.get(categoryID);
				if(null != categoryHierarchy){
					String concatenatedCategory = null;
					String[] split = categoryHierarchy.split(",");
					concatenatedCategory = split[0];
					
					if(split.length >1){
						concatenatedCategory  = concatenatedCategory + ","+split[1];
					}else{
						log.info("array of category is less than 2 : "+split);
					}
					
					List<UserProfileDataElement> list = profileMapping.getBean(split[0]);
					if(null != list){
						for(UserProfileDataElement bean : list){
							bean.calculateDataelement(transaction, categoryHierarchy,profileFactory);
						}
					}
					List<UserProfileDataElement> inflowOutflow = profileMapping.getBean("Income");
					if(null != inflowOutflow){
						for(UserProfileDataElement bean : inflowOutflow){
							bean.calculateDataelement(transaction, categoryHierarchy,profileFactory);
						}
					}
					//profileFactory.setProfileData(concatenatedCategory, transaction);
					
					
				}				
				/*
				if(null != categoryHierarchy && categoryHierarchy.contains("Interest")){
					interest = interest + transaction.getAmount();
				}else if (null != categoryHierarchy && categoryHierarchy.contains("Bank Fees")){
					bankFee = bankFee + transaction.getAmount();
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Service,Utilities")){
					utilityBill = utilityBill + transaction.getAmount();	
				} else if(null != categoryHierarchy &&categoryHierarchy.contains("Payment,Credit Card")){
					creditBill = creditBill + transaction.getAmount();		
				} else if(null != categoryHierarchy &&categoryHierarchy.contains("Payment,Loan") && loanAmount <= 0.0){
					loanAmount = loanAmount + transaction.getAmount();		
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Transfer,Payroll") && salary <= 0.0){
					employer = transaction.getName();
					salary = salary + transaction.getAmount();
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Tax,Refund") ){
					taxRefund = taxRefund + transaction.getAmount();
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Tax,Payment")){
					taxPayment = taxPayment + transaction.getAmount();
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Transfer,Billpay")){
					billPay = billPay + transaction.getAmount();
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Payment,Rent") && rentPayment <= 0.0){
					//execute this once for rent payment. save to add dto in loop
					profileDataDTO = new UserProfileDataDTO();
					rentPayment = transaction.getAmount();
					profileDataDTO.setAmount(rentPayment);
					profileDataDTO.setId(KEY_RENT_PAYMENT);
					profileDataDTO.setType("Rent Payment");
					profileDataDTO.setSubType("Rent Payment/month");
					profileDataDTO.setAccountId(transaction.getAccountId());
					saveProfileDataList.add(profileDataDTO);
					
				}
				
				if(transaction.getAmount() < 0.0){
					inflow = inflow + transaction.getAmount();					
				}else{
					outflow = outflow + transaction.getAmount();
				
				}*/								
			}	
			/*//profileFactory.getProfileData();
			
			profileDataDTO = new UserProfileDataDTO();			
			profileDataDTO.setAmount(interest);
			profileDataDTO.setId(KEY_INTEREST_TOTAL);
			profileDataDTO.setType(INTEREST);
			profileDataDTO.setSubType("Total Interest");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(bankFee);
			profileDataDTO.setId(KEY_BANK_FEE_TOTAL);
			profileDataDTO.setType("Bank Fee");
			profileDataDTO.setSubType("Total Bank Fee");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(utilityBill);
			profileDataDTO.setId(KEY_UTILITY_BILL_TOTAL);
			profileDataDTO.setType("Utility Bill");
			profileDataDTO.setSubType("Total Utility Bill");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(creditBill);
			profileDataDTO.setId(KEY_CREDIT_BILL_TOTAL);
			profileDataDTO.setType("Credit Card Bill");
			profileDataDTO.setSubType("Total Credit Card Bill");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(loanAmount);
			profileDataDTO.setId(KEY_LOAN_AMT);
			profileDataDTO.setType("Load Amount");
			profileDataDTO.setSubType("Loan Payment");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(salary);
			profileDataDTO.setId(KEY_SALARY_AMT);
			profileDataDTO.setType("Salary");
			profileDataDTO.setSubType(employer);
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(taxRefund);
			profileDataDTO.setId(KEY_TAX_REFUND);
			profileDataDTO.setType("Tax");
			profileDataDTO.setSubType("Refund");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(taxPayment);
			profileDataDTO.setId(KEY_TAX_PAYMENT);
			profileDataDTO.setType("Tax");
			profileDataDTO.setSubType("Payment");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(taxPayment);
			profileDataDTO.setId(KEY_BILL_PAY);
			profileDataDTO.setType("Payment");
			profileDataDTO.setSubType("BillPay");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(inflow);
			profileDataDTO.setId(KEY_INFLOW_TOTAL);
			profileDataDTO.setType("Inflow");
			profileDataDTO.setSubType("Total Inflow");
			saveProfileDataList.add(profileDataDTO);
			
			profileDataDTO = new UserProfileDataDTO();
			profileDataDTO.setAmount(outflow);
			profileDataDTO.setId(KEY_OUTFLOW_TOTAL);
			profileDataDTO.setType("Outflow");
			profileDataDTO.setSubType("Total Outflow");
			saveProfileDataList.add(profileDataDTO);*/
			
			saveProfileDataList = UserProfileDataElement.getData();
			//saveProfileDataList = profileFactory.getProfileData();
			for(UserProfileDataDTO dto1 : saveProfileDataList){
				dto1.setUserKey(userKey);
			}
			UserInfoDao.saveUserProfileData(saveProfileDataList, errorFactory);
		}catch (Exception e){
			log.error("error occured while building userprofile",e);
		}
		arg.put(EnvestConstants.USER_PROFILE, saveProfileDataList);
		return arg;
	}

	/*protected Map<String,Object> doWork(Map<String,Object> arg) throws Exception {
		log.info("inside doWork method in UserProfile");
		Map<String,List<UserProfileResponse>> profileData = null;
		try{
			EnvestResponse eNvestRes = (EnvestResponse) arg.get(EnvestConstants.ENVEST_RESPONSE);
			UserInfo info = (UserInfo)accountServiceUtil.getAccountAndTransaction(((UserInfo)eNvestRes).getUserKey(), EnvestConstants.GET_ACCOUNT_TRANSACTIONS);
			
			List<TransactionDetail> transactionList = info.getTransaction();
			
			Map<String, String> categories = userServiceUtil.getCategories();
			double bankFee;
			profileData = new HashMap<String,List<UserProfileResponse>>(10);
			List<UserProfileDTO> dto = new ArrayList<UserProfileDTO>(10);
			UserProfileDTO profileDTO = null;
			
			for(TransactionDetail transaction : transactionList){
								
				List<UserProfileResponse> accProfileList = null;
				if(null != profileData.get(transaction.getAccountId())){
					accProfileList = profileData.get(transaction.getAccountId());
				}else{
					accProfileList = new ArrayList<UserProfileResponse>(10);
					profileData.put(transaction.getAccountId(), accProfileList);
				}
				UserProfileResponse accProfileData = null;
				LocalDate transactionDate = transaction.getDate();
				DateTimeFormatter formatter = DateTimeFormat
					    .forPattern("MMM");
				String text = formatter.print(transactionDate);
				for(UserProfileResponse accProfile : accProfileList){
					
					if(accProfile.getMonth().equals(text)){
						accProfileData = accProfile;
						break;
					}
				}
				if(accProfileData == null){
					accProfileData = new UserProfileResponse();
					accProfileList.add(accProfileData);
				}
				accProfileData.setMonth(text);
				accProfileData.setAccountId(transaction.getAccountId());
				String categoryID = transaction.getCategoryId();
				String categoryHierarchy = categories.get(categoryID);
				if(null != categoryHierarchy && categoryHierarchy.contains("Bank Fees")){
					accProfileData.setFee(accProfileData.getFee() +transaction.getAmount());					
				}
				if(null != categoryHierarchy && categoryHierarchy.contains("Interest")){
					accProfileData.setInterest(accProfileData.getInterest() +transaction.getAmount());		
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Service,Utilities")){
					accProfileData.setUtilityBills(accProfileData.getUtilityBills() +transaction.getAmount());		
				} else if(null != categoryHierarchy &&categoryHierarchy.contains("Payment,Credit Card")){
					accProfileData.setCreditBills(accProfileData.getCreditBills() +transaction.getAmount());		
				} else if(null != categoryHierarchy &&categoryHierarchy.contains("Payment,Loan")){
					accProfileData.setLoanPayment(accProfileData.getLoanPayment() +transaction.getAmount());		
				}else if(null != categoryHierarchy &&categoryHierarchy.contains("Transfer,Payroll")){
					accProfileData.setSalary(accProfileData.getSalary() +transaction.getAmount());		
					accProfileData.setEmployer(transaction.getName());
				}else {
					if(transaction.getAmount() < 0.0){
						accProfileData.setOtherInflow(accProfileData.getOtherInflow()+ (transaction.getAmount() *//** (-1.0)*//*));
					}else{
						accProfileData.setOtherOutflow(accProfileData.getOtherOutflow() + (transaction.getAmount()*//** (-1.0)*//*));						
					}
				}
				if(transaction.getAmount() < 0.0){
					accProfileData.setInflow(accProfileData.getInflow()+ (transaction.getAmount() *//** (-1.0)*//*));
					
				}else{
					accProfileData.setOutflow(accProfileData.getOutflow() + (transaction.getAmount()*//** (-1.0)*//*));
				
				}
				
			}
			
			Collection<List<UserProfileResponse>> coll = profileData.values();
			for(List<UserProfileResponse> list : coll){
				for(UserProfileResponse r : list){
					profileDTO  = new UserProfileDTO();
					profileDTO.setUserKey(((UserInfo)eNvestRes).getUserKey());
					profileDTO.setAccountId(r.getAccountId());
					profileDTO.setCreditBills(r.getCreditBills());
					profileDTO.setEmployer(r.getEmployer());
					profileDTO.setInflow(r.getInflow());
					profileDTO.setLoanPayment(r.getLoanPayment());
					profileDTO.setMonth(r.getMonth());
					profileDTO.setMonthlyFee(r.getFee());
					profileDTO.setMonthlyInterest(r.getInterest());
					profileDTO.setOutflow(r.getOutflow());
					profileDTO.setSalary(r.getSalary());
					profileDTO.setUtilityBills(r.getUtilityBills());
					profileDTO.setOtherInflow(r.getOtherInflow());
					profileDTO.setOtherInflow(r.getOtherInflow());
					dto.add(profileDTO);
				}
			}
			UserInfoDao.saveUserProfile(dto);
			
		}catch (Exception e){
			log.error("error occured while building userprofile",e);
		}
		arg.put(EnvestConstants.USER_PROFILE, profileData);
		return arg;
	}*/
	
	
	/*protected Map<String,Object> doWork(Map<String,Object> arg) throws Exception {
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
	}*/
}	