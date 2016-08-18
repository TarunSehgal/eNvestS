package com.eNvestDetails.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;






import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.Factories.IErrorMessageFactory;
import com.eNvestDetails.Factories.IPlaidRequestFactory;
import com.eNvestDetails.Factories.PlaidRequestFactory;
import com.eNvestDetails.RecommendationEngine.InitiateRecommendation;
import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.MfaResponseDetail;
import com.eNvestDetails.Response.PlaidCategory;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.Response.UserProfileResponse;
import com.eNvestDetails.TransferService.PlaidGateway;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dao.UserInfoDao;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.eNvestDetails.security.TokenUtils;
import com.eNvestDetails.security.User;
import com.plaid.client.PlaidPublicClient;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.exception.PlaidMfaException;
import com.plaid.client.exception.PlaidServersideException;
import com.plaid.client.http.ApacheHttpClientHttpDelegate;
import com.plaid.client.http.HttpResponseWrapper;
import com.plaid.client.http.PlaidHttpRequest;
import com.plaid.client.request.Credentials;
import com.plaid.client.response.InfoResponse;

@Component
public class UserServiceUtil {
	
	@Autowired
	private ConfigFactory config = null;
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private ErrorMessageFactory errorFactory = null;
	
	@Autowired
	private PlaidGateway plaidGateway = null;
	
	private Logger logger = Logger.getLogger(UserServiceUtil.class.getName());
	
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	IPlaidRequestFactory plaidRequestFactory = new PlaidRequestFactory();
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
		private UserDetailsService userService;
		
		@Autowired
		@Qualifier("authenticationManager")
		private AuthenticationManager authManager;
	
	public Map<String,String> getCategories(){
		logger.info("getting categories");
		PlaidPublicClient plaidPublicClient = null;
		Map<String,String> cmap = new HashMap<String,String>(1000);
		try{
			/*plaidPublicClient = plaidClient.getPlaidPublicClient();
			res = plaidPublicClient.getAllCategories();*/
			 PlaidHttpRequest request = new PlaidHttpRequest("/categories");
/*			 ApacheHttpClientHttpDelegate httpDelegate =  new ApacheHttpClientHttpDelegate
					 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());*/
		    HttpResponseWrapper<PlaidCategory[]> response = plaidGateway.executeGetRequest(request, PlaidCategory[].class);
			for(PlaidCategory category : response.getResponseBody()){
				String path = "";
				for(String hierarchy: category.getHierarchy()){
					path = path +hierarchy +",";
				}
				cmap.put(category.getId(), path);	
			}
		}catch (Exception e){
			logger.error("error occured while getting categories",e);
		}
		return cmap;
	}
	
	public EnvestResponse getInfo(String userId, String password, String bank){
		logger.info("Starting to get user info");
		PlaidUserClient plaidUserClient = null;
		Credentials testCredentials = null;
		InfoResponse r = null;
		try{
/*		plaidUserClient = plaidClient.getPlaidClient();
		//plaidUserClient.setAccessToken("test_wells");
		testCredentials = new Credentials(userId, password);
		r = plaidUserClient.info(testCredentials, bank,
					null);*/
		r = plaidGateway.getInfoResponse(userId, password, bank, null);
		plaidGateway.addConnectProduct(null);
		//plaidUserClient.addProduct("connect", null);
		}catch(PlaidMfaException e){
			logger.info("MFA required");
			return CommonUtil.handleMfaException(e.getMfaResponse(), bank);
					
		}catch(PlaidServersideException e){
			logger.error("Error occured while retriving user info", e);
			return errorFactory.getServerErrorMessage(e.getErrorResponse().getResolve());
		}
		
		UserInfo userInfo = CommonUtil.parseInfoResponse(r, bank, userId);

		logger.info("Exiting user info method");
		return userInfo;
	}
	
	public EnvestResponse createUser(String userID,String password){
		ErrorMessage mes;
		long userKey = 0;
		try{
			//getCategories();
			String encodePassword = passwordEncoder.encode(password);
			userKey = UserInfoDao.createUser(userID, encodePassword,message,errorFactory);
			mes = errorFactory.getSuccessMessage(EnvestConstants.RETURN_CODE_SUCCESS
					, message.getMessage("message.useraddedsuccess"));
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
		ErrorMessage mes;
		
		try{
			code = UserInfoDao.saveUser(userKey,userID, password);
			
		}catch (Exception e){
			logger.error("Error occured while saving user", e);
			return errorFactory.getServerErrorMessage(message.getMessage("message.serverError"));
		}
		
		if(code != EnvestConstants.RETURN_CODE_SUCCESS ){
			mes = errorFactory.getFailureMessage(code
					, message.getMessage("message.userAddFailure"));
		}else{
			mes =  errorFactory.getSuccessMessage(code
					,message.getMessage("message.useraddedsuccess"));

			mes.setUserKey(userKey);
		}
		return mes;
	}
	
	public EnvestResponse authenticate(String userID,String password){
		int code = EnvestConstants.RETURN_CODE_INVALID_USERID_PASSWORD;
		ErrorMessage mes = null;
		UserInfoDTO userInfo = null;
		UserDetails userDetails = null;
		try{
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(userID, password);
			Authentication authentication = this.authManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			userDetails = userService.loadUserByUsername(userID);
			
			mes = errorFactory.getSuccessMessage(EnvestConstants.RETURN_CODE_SUCCESS
					,message.getMessage("message.userAuthenticated"));
			mes.setUserKey(((User)userDetails).getId());
			String token = TokenUtils.createToken(userDetails);
			mes.setAuthToken(token);
		/*	userInfo = UserInfoDao.authenticateUser(userID, password);
			if(null != userInfo && password.equals(userInfo.getPassword())){
				code =EnvestConstants.RETURN_CODE_SUCCESS;
			}*/
		} catch(AuthenticationException e){
			mes = errorFactory.getServerErrorMessage(e.getMessage());
		}catch (Exception e){
			mes = errorFactory.getServerErrorMessage(e.getMessage());
		}
		/*if(code != EnvestConstants.RETURN_CODE_SUCCESS ){
			mes =  ErrorMessage.getMessage(code
					,message.getMessage("message.loginFailure"),
					message.getMessage("message.failure"));
			}else{
			mes = ErrorMessage.getMessage(code
					,message.getMessage("message.userAuthenticated"),
					message.getMessage("message.success"));

			mes.setUserKey(userInfo.getUserkey());
		}*/
		return mes;
	}
	
	public EnvestResponse linkAccounts(Long userKey,String userID,String password,String bank){
		EnvestResponse d = getInfo(userID, password, bank);
		d.setUserKey(userKey);
		if(!(d instanceof ErrorMessage)){
			UserAccessTokenDTO token = new UserAccessTokenDTO();
			token.setAccessToken(d.getAccessToken());
			if(d instanceof UserInfo){
				token.setIsActive("Y");
				token.setIsdeleted("Y");
			}else if(d instanceof MfaResponseDetail){
				token.setIsActive("Y");
				token.setIsdeleted("Y");
			}		
			token.setUserBank(d.getResponseFor());
			token.setUserKey(userKey);
			if(d instanceof UserInfo){
				try {
					UserInfoDao.saveUserInfo(d,errorFactory);
					Map<String,Object> input = new HashMap<String,Object>(10);
					input.put(EnvestConstants.ENVEST_RESPONSE, d);
					Map<String,Object> output = recommendationEngine.processRequest(input);
					/*List<AccountDetail> accountsDetailList = ((UserInfo)d).getAccounts();
					Map<String,List<UserProfileResponse>> profileData = (Map)output.get(EnvestConstants.USER_PROFILE);
					if(null != profileData){
						for(AccountDetail acc :accountsDetailList){

							acc.setAccProfile(profileData.get(acc.getAccountId()));
						}
					}*/
				} catch (EnvestException e) {
					d = e.getErrorMessage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Error occurred during recomendationsengine" ,e);
				}

			}else{
				UserInfoDao.saveAccessToken(token);
			}
			
		}
		return d;
	}

	public EnvestResponse submitMFA(Long userKey,String mfa,String bank){
		UserInfo info = null;
		try{
			UserAccessTokenDTO  dto = UserInfoDao.getAccesTokens(userKey,bank);
			if(dto==null)
			{
				logger.info("access token not found");
				return errorFactory.getServerErrorMessage("Access token not found");
			}
			
			PlaidHttpRequest request  = plaidRequestFactory.GetPlaidMFARequest(mfa, dto.getAccessToken());			 
		    HttpResponseWrapper<InfoResponse> response = plaidGateway.executePostRequest(request, InfoResponse.class);
		    info = CommonUtil.parseInfoResponse(response.getResponseBody(), null, null);
		    info.setUserKey(userKey);
		    plaidGateway.addConnectProduct(null, dto.getAccessToken());
		    UserInfoDao.saveUserInfo(info,false,errorFactory);
		}catch(PlaidMfaException e){
			logger.info("MFA required");
			return CommonUtil.handleMfaException(e.getMfaResponse(), bank);
					
		}catch(PlaidServersideException e){
			logger.error("Error occured while retriving user info", e);
			return errorFactory.getServerErrorMessage(e.getErrorResponse().getResolve());
		}catch (EnvestException e) {
			return e.getErrorMessage();
		}
		return info;
	}
	
	public EnvestResponse deleteUser(Long userKey){
		ErrorMessage mes = null;
		try{
			List<UserAccessTokenDTO> list = UserInfoDao.getAccesTokens(userKey);
			for(UserAccessTokenDTO token : list){	
				plaidGateway.deleteAccount(token.getAccessToken());
			}
			
			UserInfoDao.deleteUser(userKey, errorFactory);
			mes = errorFactory.getSuccessMessage(EnvestConstants.RETURN_CODE_SUCCESS
					, message.getMessage("message.userdelete"));			
		}catch(EnvestException e){
			mes = e.getErrorMessage();
		}
		return mes;
	}
}