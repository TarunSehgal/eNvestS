package com.envest.services.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.envest.dal.UserDataService;
import com.envest.dal.dto.UserAccessTokenDTO;
import com.envest.security.TokenUtils;
import com.envest.security.User;
import com.envest.servicegateways.plaid.PlaidClientService;
import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.config.ConfigFactory;
import com.envest.services.components.config.MessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.exceptions.ErrorMessage;
import com.envest.services.components.recommendationengine.InitiateRecommendation;
import com.envest.services.components.util.CommonUtil;
import com.envest.services.response.EnvestResponse;
import com.envest.services.response.ProfileResponse;
import com.envest.services.response.UserInfo;
import com.plaid.client.exception.PlaidMfaException;

@Component
public class UserServiceFacade {

	@Autowired
	public MessageFactory message;
	@Autowired
	public EnvestMessageFactory errorFactory;
	@Autowired
	public PlaidClientService plaidConnector;
	public Logger logger = Logger.getLogger(UserServiceFacade.class.getName());;
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
	public UserDataService userDataService;

	@Autowired
	public CommonUtil commUtil;
	@Autowired
	public ConfigFactory config;

	public EnvestResponse getProfileData(Long userKey) {
		ProfileResponse response = null;
		try {
			UserInfo info = new UserInfo();

			info.setUserKey(userKey);
			Map<String, Object> input = new HashMap<String, Object>(10);
			input.put(EnvestConstants.ENVEST_RESPONSE, info);
			recommendationEngine.processRequest(input);
			response = (ProfileResponse) input.get(EnvestConstants.USER_PROFILE);

			response.setUserKey(userKey);
		} catch (EnvestException e) {
			e.printStackTrace();
		} catch (Exception e) {

			logger.error("Error occurred during recomendationsengine", e);
		}
		return response;
	}

	public Map<String, String> getCategories() {
		logger.info("getting categories");
		return plaidConnector.getCategories();
	}

	public EnvestResponse getInfo(String userId, String password, String bank) {
		logger.info("Starting to get user info");
		UserInfo userInfo;
		try {
			userInfo = plaidConnector.getUserAccountDetails(userId, password, bank, null);
			registerPlaidProducts(userInfo.getAccessToken());
		} catch (PlaidMfaException e) {
			logger.info("MFA required");
			return plaidConnector.handleMfaException(e.getMfaResponse(), bank);
		} catch (Exception e) {
			logger.error("Error occured while retriving user info", e);
			return getErrorMessage(e);
		}

		logger.info("Exiting user info method");
		return userInfo;
	}
	
	private void registerPlaidProducts(String accessToken) {
		plaidConnector.addConnectProduct(accessToken);
	}

	public EnvestResponse createUser(String userID, String password) {
		EnvestResponse mes;
		try {
			String encodePassword = passwordEncoder.encode(password);
			userDataService.createUser(userID, encodePassword, message, errorFactory);
			mes = getSucessMessageWithAuthToken(userID, "message.useraddedsuccess");
		} catch (EnvestException e) {
			mes = e.getErrorMessage();
		}
		return mes;
	}

	public EnvestResponse saveUser(Long userKey, String userID, String password) {
		int code = 0;
		EnvestResponse response;

		try {
			code = userDataService.saveUser(userKey, userID, password);
			
			response = code == EnvestConstants.RETURN_CODE_SUCCESS 
			      ? getSucessMessageWithAuthToken(userID, "message.useraddedsuccess")
			      : errorFactory.getFailureMessage(code, message.getMessage("message.userAddFailure"));			

		} catch (Exception e) {
			logger.error("Error occured while saving user", e);
			return getErrorMessage("message.serverError");
		}
		return response;
	}

	// SG - ToDo
	// Need to simplify authentication process
	public EnvestResponse authenticate(String userID, String password) {
		EnvestResponse response = null;
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userID,
					password);
			Authentication authentication = this.authManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			response = getSucessMessageWithAuthToken(userID, "message.userAuthenticated");		
		} catch (Exception e) {
			response = getErrorMessage(e);
		}
		return response;
	}

	public EnvestResponse linkUserBank(Long userKey, String userID, String password, String bank) {
		EnvestResponse response = getInfo(userID, password, bank);
		response.setUserKey(userKey);
		try {
			if (response instanceof UserInfo) {
				userDataService.saveUserInfo(response, errorFactory);
			} else if (!(response instanceof ErrorMessage)) {
				userDataService.saveAccessToken(response);
			}
		} catch (Exception e) {
			logger.error("Error occurred during recomendationsengine", e);
			response = getErrorMessage(e);
		}
		return response;
	}

	public EnvestResponse submitMFA(Long userKey, String mfa, String bank) {
		UserInfo info = null;
		try {
			// SG - ToDo Return accessToken in string from DataService rather than returning a DTO object
			UserAccessTokenDTO dto = userDataService.getAccesTokens(userKey, bank);
			
			// SG - ToDo - Introduce layer above plaidGateway which sets userKey on response.
			info = plaidConnector.getUserInfoDetails(mfa, dto.getAccessToken());
			info.setUserKey(userKey);
			registerPlaidProducts(dto.getAccessToken());
			userDataService.saveUserInfo(info, false, errorFactory);
		} catch (PlaidMfaException e) {
			logger.info("MFA required");
			return plaidConnector.handleMfaException(e.getMfaResponse(), bank);

		} catch (Exception e) {
			return getErrorMessage(e);
		}
		return info;
	}


	public EnvestResponse deleteUser(Long userKey) {
		EnvestResponse response = null;
		try {
			// SG - ToDo Return List of accessTokens in string from DataService rather than returning a List of DTOs
			List<UserAccessTokenDTO> tokens = userDataService.getAccesTokens(userKey);
			for (UserAccessTokenDTO token : tokens) {
				plaidConnector.deleteAccount(token.getAccessToken());
			}			

			userDataService.deleteUser(userKey, errorFactory);			
			response = getSuccessMessage("message.userdelete");
		} catch (EnvestException e) {			
			response = getErrorMessage(e);
		}
		return response;
	}

	public EnvestResponse getUserInfo(Long userkey) {
		return userDataService.getUserProfileData(userkey);
	}

	private EnvestResponse getSuccessMessage(String msg) {
		return errorFactory.getSuccessMessage(message.getMessage(msg));
	}

	private EnvestResponse getErrorMessage(Exception e) {
		return errorFactory.getServerErrorMessage(e.getMessage());
	}

	private EnvestResponse getErrorMessage(String msg) {
		return errorFactory.getServerErrorMessage(message.getMessage(msg));
	}
	
	private EnvestResponse getSucessMessageWithAuthToken(String userID, String mes) {
		UserDetails userDetails = userService.loadUserByUsername(userID);
		EnvestResponse response;
		response = errorFactory.getSuccessMessage(message.getMessage(mes));
		response.setUserKey(((User) userDetails).getId());
		String token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getPassword());
		response.setAuthToken(token);
		
		return response;
	}
}
