package com.eNvestDetails.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.Response.UserProfileResponse;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dao.UserInfoDao;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserProfileDTO;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.exception.PlaidMfaException;
import com.plaid.client.exception.PlaidServersideException;
import com.plaid.client.request.GetOptions;
import com.plaid.client.response.Account;
import com.plaid.client.response.MfaResponse;
import com.plaid.client.response.TransactionsResponse;

@Component
public class UserAccountServiceUtil {
	
	@Autowired
	private ConfigFactory config = null;
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private PlaidClient plaidClient = null;
	
	private Logger logger = Logger.getLogger(UserAccountServiceUtil.class.getName());
	
	@Autowired
	private UserServiceUtil serviceUtil;
	
	public EnvestResponse getDashboardData(Long userKey,int type){

		UserInfo response = null;
		PlaidUserClient plaidUserClient = null;
		TransactionsResponse tResponse = null;
		try{
			List<UserAccessTokenDTO> list = UserInfoDao.getAccesTokens(userKey);
			
			plaidUserClient = plaidClient.getPlaidClient();
			response = new UserInfo();
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			List<UserInfo.Summary> summary = new ArrayList<UserInfo.Summary>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			UserInfo.DashBoardSummary dashBoardSummaryObject = new UserInfo.DashBoardSummary();
			List<UserInfo.BankBalance> balance = new ArrayList<UserInfo.BankBalance>(10);
			//list = new ArrayList<UserAccessTokenDTO>(1);
			for(UserAccessTokenDTO token : list){				
				try{
					plaidUserClient.setAccessToken(token.getAccessToken());		
					//response = new UserDetails();
					response.setUserKey(userKey);
					GetOptions option = new GetOptions();
					option.setGte("04/01/2016");
					tResponse = plaidUserClient.updateTransactions();
					List<Account> acc = null;
					if(null !=tResponse.getAccounts()){
						acc = tResponse.getAccounts();
					}
					
					if (type == EnvestConstants.GET_ACCOUNTS|| type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
						accDetails.addAll(CommonUtil.parseAccounts(acc, token.getUserBank()));
					}
					
					
					UserInfo.BankBalance bankBalance = new UserInfo.BankBalance();
					for(Account account :acc){
						bankBalance.setBankName(token.getUserBank());
						if(null != account.getBalance()){
							bankBalance.setAvailableBalance(bankBalance.getAvailableBalance() + account.getBalance().getAvailable());
							bankBalance.setCurrentBalance(bankBalance.getCurrentBalance() + account.getBalance().getCurrent());
						}						
					}
					balance.add(bankBalance);
				
					
					if (type == EnvestConstants.GET_TRANSACTIONS || type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
						transactionsList.addAll(CommonUtil.parseTransaction(tResponse.getTransactions(),summaryMap,serviceUtil.getCategories()));
					}
									
				}catch(PlaidMfaException e){
					MfaResponse mfa = e.getMfaResponse();
					CommonUtil.handleMfaException(mfa, token.getUserBank());
				}catch(PlaidServersideException e){
					ErrorMessage mes = new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
							,e.getErrorResponse().getResolve()
							,null
							,message.getMessage("message.failure"));
					return mes;
				}
				
			}
			Collection<UserInfo.Summary> coll = summaryMap.values();
			for(UserInfo.Summary sum : coll){
				dashBoardSummaryObject.setTotalBankFee(dashBoardSummaryObject.getTotalBankFee() + sum.getTotalBankFee());
				dashBoardSummaryObject.setTotalInterest(dashBoardSummaryObject.getTotalInterest() + sum.getTotalInterest());
				dashBoardSummaryObject.setTotalInflow(dashBoardSummaryObject.getTotalInflow() + sum.getInflow());
				dashBoardSummaryObject.setTotalOutflow(dashBoardSummaryObject.getTotalOutflow() + sum.getOutflow());
				summary.add(sum);
			}
			
			dashBoardSummaryObject.setBankBalances(balance);
			response.setSummary(summary);
			response.setDashBoardSummary(dashBoardSummaryObject);
			response.setAccounts(accDetails);
			response.setTransaction(transactionsList);
		}catch(Exception e){
			logger.error("Error occured while getting dashboarddata", e);
		}		
		return response;
	
		
	}
	

	public EnvestResponse getAccountAndTransaction(Long userKey, int type){
		UserInfo response = null;
		PlaidUserClient plaidUserClient = null;
		TransactionsResponse tResponse = null;
		try{
			List<UserAccessTokenDTO> list = UserInfoDao.getAccesTokens(userKey);
			
			plaidUserClient = plaidClient.getPlaidClient();
			response = new UserInfo();
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			List<UserInfo.Summary> summary = new ArrayList<UserInfo.Summary>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			//list = new ArrayList<UserAccessTokenDTO>(1);
			for(UserAccessTokenDTO token : list){				
				try{
					plaidUserClient.setAccessToken(token.getAccessToken());		
					//response = new UserDetails();
					response.setUserKey(userKey);
					GetOptions option = new GetOptions();
					option.setGte("04/01/2016");
					tResponse = plaidUserClient.updateTransactions();
					List<Account> acc = null;
					if(null !=tResponse.getAccounts()){
						acc = tResponse.getAccounts();
					}
					
					if (type == EnvestConstants.GET_ACCOUNTS|| type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
						accDetails.addAll(CommonUtil.parseAccounts(acc, token.getUserBank()));
					}
						
					if (type == EnvestConstants.GET_TRANSACTIONS || type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
						transactionsList.addAll(CommonUtil.parseTransaction(tResponse.getTransactions(),summaryMap,serviceUtil.getCategories()));
					}
									
				}catch(PlaidMfaException e){
					MfaResponse mfa = e.getMfaResponse();
					CommonUtil.handleMfaException(mfa, token.getUserBank());
				}catch(PlaidServersideException e){
					ErrorMessage mes = new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
							,e.getErrorResponse().getResolve()
							,null
							,message.getMessage("message.failure"));
					return mes;
				}
				
			}
			Collection<UserInfo.Summary> coll = summaryMap.values();
			for(UserInfo.Summary sum : coll){
				summary.add(sum);
			}
			response.setSummary(summary);
			response.setAccounts(accDetails);
			response.setTransaction(transactionsList);
		}catch(Exception e){
			logger.error("Error occured while getting transactions", e);
		}		
		return response;
	}
	
	public UserProfileResponse getUserProfile(Long userKey){
		List<UserProfileResponse> list = new ArrayList<UserProfileResponse>(10);
		UserProfileResponse summary = null;
		try{
			
			List<UserProfileDTO> dto = UserInfoDao.getUserProfile(userKey);
			UserProfileResponse response = null;
			summary = new UserProfileResponse();
			for(UserProfileDTO r :dto){
				response = new UserProfileResponse();
				response.setAccountId(r.getAccountId());
				response.setCreditBills(r.getCreditBills());
				summary.setCreditBills(summary.getCreditBills() + r.getCreditBills());
				response.setEmployer(r.getEmployer());
				response.setInflow(r.getInflow());
				summary.setInflow(summary.getInflow() + r.getInflow());
				response.setLoanPayment(r.getLoanPayment());
				summary.setLoanPayment(summary.getLoanPayment() + r.getLoanPayment());
				response.setMonth(r.getMonth());
				response.setMonthlyFee(r.getMonthlyFee());
				summary.setMonthlyFee(summary.getMonthlyFee() + r.getMonthlyFee());
				response.setMonthlyInterest(r.getMonthlyInterest());
				summary.setMonthlyInterest(summary.getMonthlyInterest() + r.getMonthlyInterest());
				response.setOutflow(r.getOutflow());
				summary.setOutflow(summary.getOutflow() + r.getOutflow());
				response.setSalary(r.getSalary());
				summary.setSalary(summary.getSalary() + r.getSalary());
				response.setUtilityBills(r.getUtilityBills());
				summary.setUtilityBills(summary.getUtilityBills() + r.getUtilityBills());
				
				response.setOtherInflow(r.getOtherInflow());
				summary.setOtherInflow(summary.getOtherInflow() + r.getOtherInflow());
				
				response.setOtherOutflow(r.getOtherOutflow());
				summary.setOtherOutflow(summary.getOtherOutflow() + r.getOtherOutflow());;
				
				list.add(response);
			}
			summary.setProfileData(list);
		}catch(EnvestException e){
			logger.error("error occured while getting user profile",e);
		}
		return summary;
	}

}
