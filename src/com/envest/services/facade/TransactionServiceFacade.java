package com.envest.services.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.envest.dal.UserDataService;
import com.envest.dal.dto.UserAccessTokenDTO;
import com.envest.servicegateways.plaid.PlaidService;
import com.envest.servicegateways.plaid.UpdateTransactionResult;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.ConfigFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.recommendationengine.InitiateRecommendation;
import com.envest.services.components.util.CommonUtil;
import com.envest.services.response.AccountDetail;
import com.envest.services.response.BankBalance;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.TransactionDetail;
import com.envest.services.response.UserInfo;
import com.plaid.client.request.GetOptions;

@Component
public class TransactionServiceFacade {
	@Autowired
	public MessageFactory message;
	@Autowired
	public EnvestMessageFactory errorFactory;
	@Autowired
	public PlaidService plaidClientService;
	public Logger logger  = Logger.getLogger(TransactionServiceFacade.class.getName());;
	@Autowired
	public InitiateRecommendation recommendationEngine;
	@Autowired
	public PasswordEncoder passwordEncoder;
	@Autowired
	public UserDetailsService userService;
	@Qualifier("authenticationManager")
	@Autowired
	public AuthenticationManager authManager;
	@Autowired
	public UserDataService userInfoDaoService;
	@Autowired
	public UserDataService daoAdapter;
	@Autowired
	public CommonUtil commUtil;
	@Autowired
	public ConfigFactory config;

	public EnvestResponse getDashboardData(Long userKey,int type){

		try{
			List<UserAccessTokenDTO> list = daoAdapter.getAccesTokens(userKey);
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			List<BankBalance> balance = new ArrayList<BankBalance>(10);
			for(UserAccessTokenDTO token : list){				
				try{
					UpdateTransactionResult result = plaidClientService.updateTransactions(token.getAccessToken(),token.getUserBank());					

					extractDetails(type, accDetails, transactionsList, summaryMap, result);
					
					BankBalance bankBalance = getBankBalanceFromAccounts(token.getUserBank(), result.accountDetails);
					balance.add(bankBalance);				
				}catch(Exception e){
					return errorFactory.getServerErrorMessage(e.getMessage());
				}
				
			}			
			return createDashBoardResponse(userKey, accDetails, transactionsList, summaryMap, balance);
		}catch(Exception e){
			logger.error("Error occured while getting dashboarddata", e);
		}	
		return null;
	}

	public EnvestResponse getAccountAndTransaction(Long userKey, int type){
		try{
			List<UserAccessTokenDTO> list = daoAdapter.getAccesTokens(userKey);
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			for(UserAccessTokenDTO token : list){				
				try{
					GetOptions option = null;
					if(!commUtil.isTestUser(token.getAccessToken())){
						option = new GetOptions();
						option.setGte(commUtil.getGte(config.getResultString("transactionMonthRange")));
					}
					UpdateTransactionResult result = plaidClientService.updateTransactions(token.getAccessToken(),option,token.getUserBank());
					
					if(commUtil.isTestUser(token.getAccessToken())){
						createDummyData(result.transactionDetails);
						for(AccountDetail acc : result.accountDetails){
							if("depository".equals(acc.getType())){
								acc.setSubtype("checking");
							}
							if("credit".equals(acc.getType())){
								acc.setSubtype("credit");
							}
						}
					}
					
					extractDetails(type, accDetails, transactionsList, summaryMap, result);
									
				}catch(Exception e){
					return errorFactory.getServerErrorMessage(e.getMessage());
				}			
			}

			return createResponse(userKey, accDetails, transactionsList, summaryMap);
		}catch(Exception e){
			logger.error("Error occured while getting transactions", e);
			return errorFactory.getServerErrorMessage(e.getMessage());
		}	
		
	}
		
	private UserInfo createDashBoardResponse(Long userKey, List<AccountDetail> accDetails,
			List<TransactionDetail> transactionsList, Map<String, UserInfo.Summary> summaryMap,
			List<BankBalance> balance) {
		UserInfo response = new UserInfo();
		response.setUserKey(userKey);
		response.setSummary(getSummary(summaryMap));
		response.setDashBoardSummary(getDashBoardSummary(summaryMap, balance));
		response.setAccounts(accDetails);
		response.setTransaction(transactionsList);
		
		return response;
	}
	
	private UserInfo createResponse(Long userKey, List<AccountDetail> accDetails,
			List<TransactionDetail> transactionsList, Map<String, UserInfo.Summary> summaryMap) {
		UserInfo response = new UserInfo();
		response.setUserKey(userKey);
		response.setSummary(getSummary(summaryMap));
		response.setAccounts(accDetails);
		response.setTransaction(transactionsList);
		
		return response;
	}


	private List<UserInfo.Summary> getSummary(Map<String, UserInfo.Summary> summaryMap) {
		List<UserInfo.Summary> summary = new ArrayList<UserInfo.Summary>();
		Collection<UserInfo.Summary> coll = summaryMap.values();
		for(UserInfo.Summary sum : coll){
			summary.add(sum);
		}
		
		return summary;
	}	
	
	private UserInfo.DashBoardSummary getDashBoardSummary(Map<String, UserInfo.Summary> summaryMap, List<BankBalance> balance) {
		UserInfo.DashBoardSummary dashBoardSummaryObject = new UserInfo.DashBoardSummary();
		dashBoardSummaryObject.setBankBalances(balance);
		Collection<UserInfo.Summary> coll = summaryMap.values();
		for(UserInfo.Summary sum : coll){
			dashBoardSummaryObject.setTotalBankFee(dashBoardSummaryObject.getTotalBankFee() + sum.getTotalBankFee());
			dashBoardSummaryObject.setTotalInterest(dashBoardSummaryObject.getTotalInterest() + sum.getTotalInterest());
			dashBoardSummaryObject.setTotalInflow(dashBoardSummaryObject.getTotalInflow() + sum.getInflow());
			dashBoardSummaryObject.setTotalOutflow(dashBoardSummaryObject.getTotalOutflow() + sum.getOutflow());
		}
		
		return dashBoardSummaryObject;
	}

	private void extractDetails(int type, List<AccountDetail> accDetails, List<TransactionDetail> transactionsList,
			Map<String, UserInfo.Summary> summaryMap, UpdateTransactionResult result) {
		if (type == EnvestConstants.GET_ACCOUNTS|| type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
			accDetails.addAll(result.accountDetails);
		}					
		
		if (type == EnvestConstants.GET_TRANSACTIONS || type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
			transactionsList.addAll(result.transactionDetails);
			summaryMap.putAll(result.summaryMap);
		}
	}


	private BankBalance getBankBalanceFromAccounts(String bankName, List<AccountDetail> acc) {
		BankBalance bankBalance = new BankBalance();
		bankBalance.setBankName(bankName);
		for(AccountDetail account :acc){
			if(null != account.getBalance()){
				bankBalance.setAvailableBalance(bankBalance.getAvailableBalance() + account.getBalance().getAvailable());
				bankBalance.setCurrentBalance(bankBalance.getCurrentBalance() + account.getBalance().getCurrent());
			}						
		}
		return bankBalance;
	}
	
	private void createDummyData(List<TransactionDetail> list){
		LocalDate testDate = null;
		for(TransactionDetail transaction : list){
			if(null == testDate){
				testDate = transaction.getDate();
			}
			int month = testDate.getMonthOfYear() - transaction.getDate().getMonthOfYear();
			transaction.setDate(new LocalDate().minusMonths(month));
		}
	}
}