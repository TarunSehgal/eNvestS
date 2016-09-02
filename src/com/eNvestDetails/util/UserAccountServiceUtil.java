package com.eNvestDetails.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.DAL.Dao.UserInfoDao;
import com.eNvestDetails.DAL.Dto.UserAccessTokenDTO;
import com.eNvestDetails.DAL.Dto.UserProfileDataDTO;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.RecommendationEngine.InitiateRecommendation;
import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.BankBalance;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.ProfileResponse;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.TransferService.PlaidConnector;
import com.eNvestDetails.TransferService.UpdateTransactionResult;
import com.eNvestDetails.UserProfileData.DataElement.DataElement;
import com.eNvestDetails.constant.EnvestConstants;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.exception.PlaidMfaException;
import com.plaid.client.exception.PlaidServersideException;
import com.plaid.client.request.GetOptions;
import com.plaid.client.response.MfaResponse;
import com.plaid.client.response.TransactionsResponse;

@Component
public class UserAccountServiceUtil {
	
	@Autowired
	private PlaidConnector plaidGateway = null;
	
	@Autowired
	private ErrorMessageFactory errorFactory = null;
	
	private Logger logger = Logger.getLogger(UserAccountServiceUtil.class.getName());
	
	public EnvestResponse getDashboardData(Long userKey,int type){

		UserInfo response = null;
		TransactionsResponse tResponse = null;
		try{
			List<UserAccessTokenDTO> list = UserInfoDao.getAccesTokens(userKey);
			response = new UserInfo();
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			List<UserInfo.Summary> summary = new ArrayList<UserInfo.Summary>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			UserInfo.DashBoardSummary dashBoardSummaryObject = new UserInfo.DashBoardSummary();
			List<BankBalance> balance = new ArrayList<BankBalance>(10);
			//list = new ArrayList<UserAccessTokenDTO>(1);
			for(UserAccessTokenDTO token : list){				
				try{
					//response = new UserDetails();
					response.setUserKey(userKey);
					GetOptions option = new GetOptions();
					option.setGte("04/01/2016");
					UpdateTransactionResult result = plaidGateway.updateTransactions(token.getAccessToken(),token.getUserBank());					

					extractDetails(type, accDetails, transactionsList, summaryMap, result);
					
					BankBalance bankBalance = getBankBalanceFromAccounts(token.getUserBank(), result.accountDetails);
					balance.add(bankBalance);				
				}catch(Exception e){
					return errorFactory.getServerErrorMessage(e.getMessage());
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
	

	public EnvestResponse getAccountAndTransaction(Long userKey, int type){
		UserInfo response = null;
		try{
			List<UserAccessTokenDTO> list = UserInfoDao.getAccesTokens(userKey);
			response = new UserInfo();
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			List<UserInfo.Summary> summary = new ArrayList<UserInfo.Summary>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			//list = new ArrayList<UserAccessTokenDTO>(1);
			for(UserAccessTokenDTO token : list){				
				try{
					response.setUserKey(userKey);
					
					GetOptions option = new GetOptions();
					option.setGte("04/01/2016");
					UpdateTransactionResult result = plaidGateway.updateTransactions(token.getAccessToken(),token.getUserBank());
					
					extractDetails(type, accDetails, transactionsList, summaryMap, result);
									
				}catch(Exception e){
					return errorFactory.getServerErrorMessage(e.getMessage());
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

	
	/*public UserProfileResponse getUserProfile(Long userKey){
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
				if(null != r.getEmployer()){
					summary.setEmployer(r.getEmployer());
				}
				response.setEmployer(r.getEmployer());
				response.setInflow(r.getInflow());
				summary.setInflow(summary.getInflow() + r.getInflow());
				response.setLoanPayment(r.getLoanPayment());
				summary.setLoanPayment(summary.getLoanPayment() + r.getLoanPayment());
				response.setMonth(r.getMonth());
				response.setFee(r.getMonthlyFee());
				summary.setFee(summary.getFee() + r.getMonthlyFee());
				response.setInterest(r.getMonthlyInterest());
				summary.setInterest(summary.getInterest() + r.getMonthlyInterest());
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
			int size = dto.size();
			summary.setCreditBills(summary.getCreditBills()/size);
			summary.setInflow(summary.getInflow()/size);
			summary.setOutflow(summary.getOutflow()/size);
			summary.setLoanPayment(summary.getLoanPayment()/size);
			summary.setSalary(summary.getSalary()/size);
			summary.setUtilityBills(summary.getUtilityBills()/size);
			summary.setOtherInflow(summary.getOtherInflow()/size);
			summary.setOtherOutflow(summary.getOtherOutflow()/size);
			summary.setProfileData(list);
		}catch(EnvestException e){
			logger.error("error occured while getting user profile",e);
		}
		return summary;
	}*/
	
	/*public EnvestResponse getProfileData(Long userKey){
=======
		
	public EnvestResponse getProfileData(Long userKey){
>>>>>>> ee7ef167ee02f145a79c1eb0feb8be8df47b08ca
		List<UserProfileDataDTO> list = null;
		ProfileResponse response = null;
		try{
			list =  UserInfoDao.getUserProfileData(userKey, errorFactory);
			response = new ProfileResponse();
			response.setProfile(list);
			response.setUserKey(userKey);
	
		}catch(EnvestException e){
			logger.error("error occured while getting user profile",e);
		}
		return response;
	}*/
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	public EnvestResponse getProfileData(Long userKey){
		List<UserProfileDataDTO> list = null;
		ProfileResponse response = null;
		try {
			UserInfo info = new UserInfo();
			response = new ProfileResponse();
			info.setUserKey(userKey);
			Map<String,Object> input = new HashMap<String,Object>(10);
			input.put(EnvestConstants.ENVEST_RESPONSE, info);
			Map<String,Object> output = recommendationEngine.processRequest(input);
			response.setProfile((List<DataElement>)input.get(
					EnvestConstants.USER_PROFILE));
			response.setUserKey(userKey);
		} catch (EnvestException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
			logger.error("Error occurred during recomendationsengine" ,e);
		}		
		return response;
	}
	

}
