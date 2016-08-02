package com.eNvestDetails.Recommendation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import com.eNvestDetails.RecommendationEngine.AbstractRule;
import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.Response.UserProfileResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dao.UserInfoDao;
import com.eNvestDetails.dto.UserProfileDTO;
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
					accProfileData.setMonthlyFee(accProfileData.getMonthlyFee() +transaction.getAmount());					
				}
				if(null != categoryHierarchy && categoryHierarchy.contains("Interest")){
					accProfileData.setMonthlyInterest(accProfileData.getMonthlyInterest() +transaction.getAmount());		
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
						accProfileData.setOtherInflow(accProfileData.getOtherInflow()+ (transaction.getAmount() /** (-1.0)*/));
					}else{
						accProfileData.setOtherOutflow(accProfileData.getOtherOutflow() + (transaction.getAmount()/** (-1.0)*/));						
					}
				}
				if(transaction.getAmount() < 0.0){
					accProfileData.setInflow(accProfileData.getInflow()+ (transaction.getAmount() /** (-1.0)*/));
					
				}else{
					accProfileData.setOutflow(accProfileData.getOutflow() + (transaction.getAmount()/** (-1.0)*/));
				
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
					profileDTO.setMonthlyFee(r.getMonthlyFee());
					profileDTO.setMonthlyInterest(r.getMonthlyInterest());
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
	}
	
	
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