package com.eNvestDetails.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.apache.log4j.Logger;
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

import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.DAL.IUserInfoDAOService;
import com.eNvestDetails.DAL.UserAccessTokenDTO;
import com.eNvestDetails.DAL.UserInfoDAOService;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.Factories.ErrorMessageFactory;
import com.eNvestDetails.RecommendationEngine.InitiateRecommendation;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.MfaResponseDetail;
import com.eNvestDetails.Response.PlaidCategory;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.TransferService.PlaidConnector;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.security.TokenUtils;
import com.eNvestDetails.security.User;
import com.plaid.client.exception.PlaidMfaException;
import com.plaid.client.exception.PlaidServersideException;
import com.plaid.client.http.HttpResponseWrapper;

@Component
public class UserServiceUtil {
	
	@Autowired
	private IUserInfoDAOService daoAdapter;
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private ErrorMessageFactory errorFactory = null;
	
	@Autowired
	private PlaidConnector plaidGateway = null;
	
	private Logger logger = Logger.getLogger(UserServiceUtil.class.getName());
	
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
		ErrorMessage mes;
		long userKey = 0;
		try{
			//getCategories();
			String encodePassword = passwordEncoder.encode(password);
			userKey = daoAdapter.createUser(userID, encodePassword,message,errorFactory);
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
			code = daoAdapter.saveUser(userKey,userID, password);
			
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
		ErrorMessage mes = null;
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
			UserAccessTokenDTO token = new UserAccessTokenDTO();
			token.setAccessToken(d.getAccessToken());
			token.setIsActive("Y");
			token.setIsdeleted("Y");
/*			if(d instanceof UserInfo){
				token.setIsActive("Y");
				token.setIsdeleted("Y");
			}else if(d instanceof MfaResponseDetail){
				token.setIsActive("Y");
				token.setIsdeleted("Y");
			}*/		
			token.setUserBank(d.getResponseFor());
			token.setUserKey(userKey);
			if(d instanceof UserInfo){
				try {
					daoAdapter.saveUserInfo(d,errorFactory);
					Map<String,Object> input = new HashMap<String,Object>(10);
					input.put(EnvestConstants.ENVEST_RESPONSE, d);
					Map<String,Object> output = recommendationEngine.processRequest(input);
				} catch (EnvestException e) {
					d = e.getErrorMessage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Error occurred during recomendationsengine" ,e);
				}

			}else{
				daoAdapter.saveAccessToken(token);
			}
			
		}
		return d;
	}

	public EnvestResponse submitMFA(Long userKey,String mfa,String bank){
		UserInfo info = null;
		try{
			UserAccessTokenDTO  dto = daoAdapter.getAccesTokens(userKey,bank);
			if(dto==null)
			{
				logger.info("access token not found");
				return errorFactory.getServerErrorMessage("Access token not found");
			}
			
		    info = plaidGateway.executeMFARequest(mfa, dto.getAccessToken());
		    info.setUserKey(userKey);
		    plaidGateway.addConnectProduct(null, dto.getAccessToken());
		    daoAdapter.saveUserInfo(info,false,errorFactory);
		    Map<String,Object> input = new HashMap<String,Object>(10);
			input.put(EnvestConstants.ENVEST_RESPONSE, info);
			Map<String,Object> output = recommendationEngine.processRequest(input);
		}catch(PlaidMfaException e){
			logger.info("MFA required");
			return plaidGateway.handleMfaException(e.getMfaResponse(), bank);
					
		}catch(Exception e){
			return errorFactory.getServerErrorMessage(e.getMessage());
		}
		return info;
	}
	
	public EnvestResponse deleteUser(Long userKey){
		ErrorMessage mes = null;
		try{
			List<UserAccessTokenDTO> list = daoAdapter.getAccesTokens(userKey);
			for(UserAccessTokenDTO token : list){	
				plaidGateway.deleteAccount(token.getAccessToken());
			}
			
			daoAdapter.deleteUser(userKey, errorFactory);
			mes = errorFactory.getSuccessMessage(EnvestConstants.RETURN_CODE_SUCCESS
					, message.getMessage("message.userdelete"));			
		}catch(EnvestException e){
			mes = e.getErrorMessage();
		}
		return mes;
	}
	
	public EnvestResponse getUserInfo(Long userkey){
		return userInfoDaoService.getUserProfileData(userkey);		
	}
}