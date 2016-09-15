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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.envest.dal.UserInfoDAOService;
import com.envest.dal.dto.UserAccessTokenDTO;
import com.envest.security.TokenUtils;
import com.envest.security.User;
import com.envest.servicegateways.plaid.PlaidConnector;
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
	public PlaidConnector plaidGateway;
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
	public UserInfoDAOService userInfoDaoService;
	@Autowired
	public UserInfoDAOService daoAdapter;
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
		return plaidGateway.getCategories();
	}

	public EnvestResponse getInfo(String userId, String password, String bank) {
		logger.info("Starting to get user info");
		UserInfo userInfo;
		try {
			userInfo = plaidGateway.getInfoResponse(userId, password, bank, null);
			plaidGateway.addConnectProduct(null, userInfo.getAccessToken());
		} catch (PlaidMfaException e) {
			logger.info("MFA required");
			return plaidGateway.handleMfaException(e.getMfaResponse(), bank);
		} catch (Exception e) {
			logger.error("Error occured while retriving user info", e);
			return errorFactory.getServerErrorMessage(e.getMessage());
		}

		logger.info("Exiting user info method");
		return userInfo;
	}

	public EnvestResponse createUser(String userID, String password) {
		EnvestResponse mes;
		try {
			String encodePassword = passwordEncoder.encode(password);
			userInfoDaoService.createUser(userID, encodePassword, message, errorFactory);
			mes = getSuccessMessage(userID, "message.useraddedsuccess");
		} catch (EnvestException e) {
			mes = e.getErrorMessage();
		}
		return mes;
	}

	public EnvestResponse saveUser(Long userKey, String userID, String password) {
		int code = 0;
		EnvestResponse mes;

		try {
			code = userInfoDaoService.saveUser(userKey, userID, password);
			
			if (code != EnvestConstants.RETURN_CODE_SUCCESS) {
				mes = errorFactory.getFailureMessage(code, message.getMessage("message.userAddFailure"));
			} else {
				mes = getSuccessMessage(userID, "message.useraddedsuccess");
			}

		} catch (Exception e) {
			logger.error("Error occured while saving user", e);
			return errorFactory.getServerErrorMessage(message.getMessage("message.serverError"));
		}
		return mes;
	}

	public EnvestResponse authenticate(String userID, String password) {
		EnvestResponse mes = null;
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userID,
					password);
			Authentication authentication = this.authManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			mes = getSuccessMessage(userID, "message.userAuthenticated");
		} catch (AuthenticationException e) {
			mes = errorFactory.getServerErrorMessage(e.getMessage());
		} catch (Exception e) {
			mes = errorFactory.getServerErrorMessage(e.getMessage());
		}
		return mes;
	}

	public EnvestResponse linkAccounts(Long userKey, String userID, String password, String bank) {
		EnvestResponse d = getInfo(userID, password, bank);
		d.setUserKey(userKey);
		if (!(d instanceof ErrorMessage)) {
			if (d instanceof UserInfo) {
				try {
					userInfoDaoService.saveUserInfo(d, errorFactory);
				} catch (EnvestException e) {
					d = e.getErrorMessage();
				} catch (Exception e) {
					logger.error("Error occurred during recomendationsengine", e);
				}

			} else {
				userInfoDaoService.saveAccessToken(d);
			}
		}
		return d;
	}

	public EnvestResponse submitMFA(Long userKey, String mfa, String bank) {
		UserInfo info = null;
		try {
			UserAccessTokenDTO dto = userInfoDaoService.getAccesTokens(userKey, bank);
			if (dto == null) {
				logger.info("access token not found");
				return errorFactory.getServerErrorMessage("Access token not found");
			}

			info = plaidGateway.executeMFARequest(mfa, dto.getAccessToken());
			info.setUserKey(userKey);
			plaidGateway.addConnectProduct(null, dto.getAccessToken());
			userInfoDaoService.saveUserInfo(info, false, errorFactory);
		} catch (PlaidMfaException e) {
			logger.info("MFA required");
			return plaidGateway.handleMfaException(e.getMfaResponse(), bank);

		} catch (Exception e) {
			return errorFactory.getServerErrorMessage(e.getMessage());
		}
		return info;
	}

	public EnvestResponse deleteUser(Long userKey) {
		EnvestResponse mes = null;
		try {
			List<UserAccessTokenDTO> list = userInfoDaoService.getAccesTokens(userKey);
			for (UserAccessTokenDTO token : list) {
				plaidGateway.deleteAccount(token.getAccessToken());
			}

			userInfoDaoService.deleteUser(userKey, errorFactory);
			mes = errorFactory.getSuccessMessage(message.getMessage("message.userdelete"));
		} catch (EnvestException e) {
			mes = e.getErrorMessage();
		}
		return mes;
	}

	public EnvestResponse getUserInfo(Long userkey) {
		return userInfoDaoService.getUserProfileData(userkey);
	}
	
	private EnvestResponse getSuccessMessage(String userID, String mes) {
		UserDetails userDetails = userService.loadUserByUsername(userID);
		EnvestResponse response;
		response = errorFactory.getSuccessMessage(message.getMessage(mes));
		response.setUserKey(((User) userDetails).getId());
		String token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getPassword());
		response.setAuthToken(token);
		
		return response;
	}
}
