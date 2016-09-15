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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.envest.dal.UserInfoDAOService;
import com.envest.dal.dto.UserAccessTokenDTO;
import com.envest.dal.dto.UserProfileDataDTO;
import com.envest.security.TokenUtils;
import com.envest.security.User;
import com.envest.servicegateways.plaid.PlaidConnector;
import com.envest.servicegateways.plaid.UpdateTransactionResult;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.ConfigFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.exceptions.ErrorMessage;
import com.envest.services.components.recommendationengine.InitiateRecommendation;
import com.envest.services.components.util.CommonUtil;
import com.envest.services.response.AccountDetail;
import com.envest.services.response.BankBalance;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.ProfileResponse;
import com.envest.services.response.TransactionDetail;
import com.envest.services.response.UserInfo;
import com.plaid.client.exception.PlaidMfaException;
import com.plaid.client.request.GetOptions;
import com.plaid.client.response.TransactionsResponse;

@Component
public class DataServiceFacade {

	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private EnvestMessageFactory errorFactory = null;
	
	@Autowired
	private PlaidConnector plaidGateway = null;
	
	private Logger logger = Logger.getLogger(DataServiceFacade.class.getName());
	
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
		private UserDetailsService userService;
		
		@Autowired
		@Qualifier("authenticationManager")
		private AuthenticationManager authManager;
	
	@Autowired
	private UserInfoDAOService userInfoDaoService;
		
	@Autowired
	private UserInfoDAOService daoAdapter;
	

	@Autowired
	CommonUtil commUtil;
	
	@Autowired
	ConfigFactory config;
	
	public EnvestResponse getDashboardData(Long userKey,int type){

		try{
			List<UserAccessTokenDTO> list = daoAdapter.getAccesTokens(userKey);
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			List<BankBalance> balance = new ArrayList<BankBalance>(10);
			//list = new ArrayList<UserAccessTokenDTO>(1);
			for(UserAccessTokenDTO token : list){				
				try{
					//response = new UserDetails();					
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
			return createDashBoardResponse(userKey, accDetails, transactionsList, summaryMap, balance);
		}catch(Exception e){
			logger.error("Error occured while getting dashboarddata", e);
		}	
		return null;
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
	

	public EnvestResponse getAccountAndTransaction(Long userKey, int type){
		try{
			List<UserAccessTokenDTO> list = daoAdapter.getAccesTokens(userKey);
			List<AccountDetail> accDetails = new ArrayList<AccountDetail>(10);
			List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
			Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>(20);
			//list = new ArrayList<UserAccessTokenDTO>(1);
			for(UserAccessTokenDTO token : list){				
				try{
					GetOptions option = null;
					if(!commUtil.isTestUser(token.getAccessToken())){
						option = new GetOptions();
						option.setGte(commUtil.getGte(config.getResultString("transactionMonthRange")));
					}
					UpdateTransactionResult result = plaidGateway.updateTransactions(token.getAccessToken(),option,token.getUserBank());
					
					if(commUtil.isTestUser(token.getAccessToken())){
						createDummyData(result.transactionDetails);
					}
					
					extractDetails(type, accDetails, transactionsList, summaryMap, result);
									
				}catch(Exception e){
					return errorFactory.getServerErrorMessage(e.getMessage());
				}			
			}

			return createResponse(userKey, accDetails, transactionsList, summaryMap);
		}catch(Exception e){
			logger.error("Error occured while getting transactions", e);
		}		
		return null;
	}
	
	private void createDummyData(List<TransactionDetail> list){
		List<TransactionDetail> transactionList = list;
		Collections.sort(transactionList);
		LocalDate testDate = null;
		LocalDate dummyDate = null;
		int month = 0;
		for(TransactionDetail transaction : list){
			dummyDate = new LocalDate();
			if(null == testDate){
				testDate = transaction.getDate();
			}
			month = testDate.getMonthOfYear() - transaction.getDate().getMonthOfYear();
			dummyDate = dummyDate.minusMonths(month);
			transaction.setDate(dummyDate);
		}
	}
	
	public EnvestResponse getProfileData(Long userKey){
		List<UserProfileDataDTO> list = null;
		ProfileResponse response = null;
		try {
			UserInfo info = new UserInfo();
			
			info.setUserKey(userKey);
			Map<String,Object> input = new HashMap<String,Object>(10);
			input.put(EnvestConstants.ENVEST_RESPONSE, info);
			Map<String,Object> output = recommendationEngine.processRequest(input);
			response = (ProfileResponse)input.get(
					EnvestConstants.USER_PROFILE);
			
			response.setUserKey(userKey);
		} catch (EnvestException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
			logger.error("Error occurred during recomendationsengine" ,e);
		}		
		return response;
	}
	
	public Map<String,String> getCategories(){
		logger.info("getting categories");
		return plaidGateway.getCategories();			
	}
	
	public EnvestResponse getInfo(String userId, String password, String bank){
		logger.info("Starting to get user info");
		UserInfo userInfo;
		try{
		userInfo = plaidGateway.getInfoResponse(userId, password, bank, null);
		plaidGateway.addConnectProduct(null, userInfo.getAccessToken());
		}catch(PlaidMfaException e){
			logger.info("MFA required");
			return plaidGateway.handleMfaException(e.getMfaResponse(), bank);
	}catch(Exception e){
		logger.error("Error occured while retriving user info", e);
		return errorFactory.getServerErrorMessage(e.getMessage());
	}

		logger.info("Exiting user info method");
		return userInfo;
	}
	
	public EnvestResponse createUser(String userID,String password){
		EnvestResponse mes;
		long userKey = 0;
		try{
			//getCategories();
			String encodePassword = passwordEncoder.encode(password);
			userKey = userInfoDaoService.createUser(userID, encodePassword,message,errorFactory);
			mes = errorFactory.getSuccessMessage(message.getMessage("message.useraddedsuccess"));
			mes.setUserKey(userKey);
			User user = new User(userID.toUpperCase(), encodePassword);
			user.setId(userKey);
			String token = TokenUtils.createToken(user);
			mes.setAuthToken(token);
		}catch(EnvestException e){
			mes = e.getErrorMessage();
		}
		return mes;
	}
		
	public EnvestResponse saveUser(Long userKey, String userID,String password){
		int code = 0;
		EnvestResponse mes;
		
		try{
			code = userInfoDaoService.saveUser(userKey,userID, password);
			
		}catch (Exception e){
			logger.error("Error occured while saving user", e);
			return errorFactory.getServerErrorMessage(message.getMessage("message.serverError"));
		}
		
		if(code != EnvestConstants.RETURN_CODE_SUCCESS ){
			mes = errorFactory.getFailureMessage(code
					, message.getMessage("message.userAddFailure"));
		}else{
			mes =  errorFactory.getSuccessMessage(message.getMessage("message.useraddedsuccess"));

			mes.setUserKey(userKey);
		}
		return mes;
	}
	
	public EnvestResponse authenticate(String userID,String password){
		EnvestResponse mes = null;
		UserDetails userDetails = null;
		try{
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(userID, password);
			Authentication authentication = this.authManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			userDetails = userService.loadUserByUsername(userID);
			
			mes = errorFactory.getSuccessMessage(message.getMessage("message.userAuthenticated"));
			mes.setUserKey(((User)userDetails).getId());
			String token = TokenUtils.createToken(userDetails);
			mes.setAuthToken(token);
		} catch(AuthenticationException e){
			mes = errorFactory.getServerErrorMessage(e.getMessage());
		}catch (Exception e){
			mes = errorFactory.getServerErrorMessage(e.getMessage());
		}
		return mes;
	}
	
	public EnvestResponse linkAccounts(Long userKey,String userID,String password,String bank){
		EnvestResponse d = getInfo(userID, password, bank);
		d.setUserKey(userKey);
		if(!(d instanceof ErrorMessage)){
			if(d instanceof UserInfo){
				try {
					userInfoDaoService.saveUserInfo(d,errorFactory);
					/*WHat is purpose if nothing is returned*/
					/*Map<String,Object> input = new HashMap<String,Object>(10);
					input.put(EnvestConstants.ENVEST_RESPONSE, d);
					Map<String,Object> output = recommendationEngine.processRequest(input);*/
				} catch (EnvestException e) {
					d = e.getErrorMessage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Error occurred during recomendationsengine" ,e);
				}

			}else{
				userInfoDaoService.saveAccessToken(d);
			}
			
		}
		return d;
	}

	public EnvestResponse submitMFA(Long userKey,String mfa,String bank){
		UserInfo info = null;
		try{
			UserAccessTokenDTO  dto = userInfoDaoService.getAccesTokens(userKey,bank);
			if(dto==null)
			{
				logger.info("access token not found");
				return errorFactory.getServerErrorMessage("Access token not found");
			}
			
		    info = plaidGateway.executeMFARequest(mfa, dto.getAccessToken());
		    info.setUserKey(userKey);
		    plaidGateway.addConnectProduct(null, dto.getAccessToken());
		    userInfoDaoService.saveUserInfo(info,false,errorFactory);
		    /*Map<String,Object> input = new HashMap<String,Object>(10);
			input.put(EnvestConstants.ENVEST_RESPONSE, info);
			Map<String,Object> output = recommendationEngine.processRequest(input);*/
		}catch(PlaidMfaException e){
			logger.info("MFA required");
			return plaidGateway.handleMfaException(e.getMfaResponse(), bank);
					
		}catch(Exception e){
			return errorFactory.getServerErrorMessage(e.getMessage());
		}
		return info;
	}
	
	public EnvestResponse deleteUser(Long userKey){
		EnvestResponse mes = null;
		try{
			List<UserAccessTokenDTO> list = userInfoDaoService.getAccesTokens(userKey);
			for(UserAccessTokenDTO token : list){	
				plaidGateway.deleteAccount(token.getAccessToken());
			}
			
			userInfoDaoService.deleteUser(userKey, errorFactory);
			mes = errorFactory.getSuccessMessage(message.getMessage("message.userdelete"));			
		}catch(EnvestException e){
			mes = e.getErrorMessage();
		}
		return mes;
	}
	
	public EnvestResponse getUserInfo(Long userkey){
		return userInfoDaoService.getUserProfileData(userkey);		
	}
}
