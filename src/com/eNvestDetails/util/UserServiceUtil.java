package com.eNvestDetails.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eNvestDetails.Config.ConfigFactory;
import com.eNvestDetails.Config.MessageFactory;
import com.eNvestDetails.Exception.EnvestException;
import com.eNvestDetails.Exception.ErrorMessage;
import com.eNvestDetails.RecommendationEngine.InitiateRecommendation;
import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.EnvestResponse;
import com.eNvestDetails.Response.MfaResponseDetail;
import com.eNvestDetails.Response.PlaidCategory;
import com.eNvestDetails.Response.UserInfo;
import com.eNvestDetails.constant.EnvestConstants;
import com.eNvestDetails.dao.UserInfoDao;
import com.eNvestDetails.dto.UserAccessTokenDTO;
import com.eNvestDetails.dto.UserInfoDTO;
import com.plaid.client.PlaidPublicClient;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.exception.PlaidMfaException;
import com.plaid.client.exception.PlaidServersideException;
import com.plaid.client.http.ApacheHttpClientHttpDelegate;
import com.plaid.client.http.HttpResponseWrapper;
import com.plaid.client.http.PlaidHttpRequest;
import com.plaid.client.request.Credentials;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.InfoResponse.Address;
import com.plaid.client.response.InfoResponse.Email;
import com.plaid.client.response.InfoResponse.Info;
import com.plaid.client.response.InfoResponse.PhoneNumber;

@Component
public class UserServiceUtil {
	
	@Autowired
	private ConfigFactory config = null;
	
	@Autowired
	private MessageFactory message = null;
	
	@Autowired
	private PlaidClient plaidClient = null;
	
	private Logger logger = Logger.getLogger(UserServiceUtil.class.getName());
	
	@Autowired
	private InitiateRecommendation recommendationEngine = null;
	
	public Map<String,String> getCategories(){
		logger.info("getting categories");
		PlaidPublicClient plaidPublicClient = null;
		Map<String,String> cmap = new HashMap<String,String>(1000);
		try{
			/*plaidPublicClient = plaidClient.getPlaidPublicClient();
			res = plaidPublicClient.getAllCategories();*/
			 PlaidHttpRequest request = new PlaidHttpRequest("/categories");
			 ApacheHttpClientHttpDelegate httpDelegate =  new ApacheHttpClientHttpDelegate
					 (PlaidClient.BASE_URI_PRODUCTION, HttpClientBuilder.create().disableContentCompression().build());
		    HttpResponseWrapper<PlaidCategory[]> response = httpDelegate.doGet(request, PlaidCategory[].class);
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
		plaidUserClient = plaidClient.getPlaidClient();
		//plaidUserClient.setAccessToken("test_wells");
		testCredentials = new Credentials(userId, password);
		r = plaidUserClient.info(testCredentials, bank,
					null);
		plaidUserClient.addProduct("connect", null);
		}catch(PlaidMfaException e){
			logger.info("MFA required");
			return CommonUtil.handleMfaException(e.getMfaResponse(), bank);
					
		}catch(PlaidServersideException e){
			logger.error("Error occured while retriving user info", e);
			ErrorMessage em = new ErrorMessage();
			em = new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
					,e.getErrorResponse().getResolve()
					,null
					,message.getMessage("message.failure"));			
			return em;
		}
		
		UserInfo userInfo = new UserInfo();
		Info f = r.getInfo();
		
		//userInfo.setId("1"); //this will be DB generated key
		userInfo.setAccessToken(r.getAccessToken());
		userInfo.setResponseFor(bank);
		userInfo.setUserId(userId);
		UserInfo.Info info = new UserInfo.Info();
		info.setNames(f.getNames());
		
		UserInfo.Address address = null;
		List<UserInfo.Address> addList = new ArrayList<UserInfo.Address>(10);
		
		for (Address ad : f.getAddresses()) {
			address = new UserInfo.Address();	
			address.setPrimary(ad.getPrimary());
			if (null != ad.getAddressDetails()){
				address.setCity(ad.getAddressDetails().getCity());
				address.setState(ad.getAddressDetails().getState());
				address.setStreet(ad.getAddressDetails().getStreet());
				address.setZip(ad.getAddressDetails().getZip());				
			}		
			addList.add(address);
		}
		info.setAddresses(addList);
		
		UserInfo.PhoneNumber userPhone = null;
		List<UserInfo.PhoneNumber> userPhoneList = new ArrayList<UserInfo.PhoneNumber>(10);
		for(PhoneNumber pn : f.getPhoneNumbers()){
			userPhone = new UserInfo.PhoneNumber();
			userPhone.setNumber(pn.getData());
			userPhone.setType(pn.getType());
			userPhone.setPrimary(pn.isPrimary());
			userPhoneList.add(userPhone);
		}
		info.setPhoneNumbers(userPhoneList);
		
		UserInfo.Email userEmails = null;
		List<UserInfo.Email> userEmailList = new ArrayList<UserInfo.Email>(10);
		for(Email em : f.getEmails()){
			userEmails = new UserInfo.Email();
			userEmails.setEmail(em.getData());
			userEmails.setType(em.getType());
			//userEmails.setPrimary(em.);
			userEmailList.add(userEmails);
		}
		info.setEmails(userEmailList);
		userInfo.setInfo(info);
		userInfo.setAccounts(CommonUtil.parseAccounts(r.getAccounts(), bank));
		//userInfo.setUserKey(UserInfoDao.saveUserInfo(userInfo));
		
		logger.info("Exiting user info method");
		return userInfo;
	}
	
	public EnvestResponse createUser(String userID,String password){
		ErrorMessage mes;
		long userKey = 0;
		try{
			getCategories();
			userKey = UserInfoDao.createUser(userID, password,message);
			mes = new ErrorMessage(EnvestConstants.RETURN_CODE_SUCCESS
					,message.getMessage("message.useraddedsuccess")
					,null
					,message.getMessage("message.success"));
			mes.setUserKey(userKey);
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
			mes = new ErrorMessage(EnvestConstants.RETURN_CODE_SERVER_ERROR
					,message.getMessage("message.serverError")
					,null
					,message.getMessage("message.failure"));
			/*mes.setCode(EnvestConstants.RETURN_CODE_SERVER_ERROR);
			mes.setMessage(message.getMessage("message.serverError"));
			mes.setStatus(EnvestConstants.RETURN_STATUS_FAILURE);*/
			//mes.setType("Error");
			return mes;
		}
		
		if(code != EnvestConstants.RETURN_CODE_SUCCESS ){
			mes = new ErrorMessage(code
					,message.getMessage("message.userAddFailure")
					,null
					,message.getMessage("message.failure"));
		/*	mes.setCode(code);
			mes.setMessage(message.getMessage("message.userAddFailure"));
			mes.setStatus(EnvestConstants.RETURN_STATUS_FAILURE);
			//mes.setType(message.getMessage("message.failure"));
*/		}else{
			mes = new ErrorMessage(code
					,message.getMessage("message.useraddedsuccess")
					,null
					,message.getMessage("message.success"));
			/*mes.setCode(code);
			mes.setMessage(message.getMessage("message.useraddedsuccess"));
			mes.setStatus(EnvestConstants.RETURN_STATUS_SUCCESS);*/
			//mes.setType(message.getMessage("message.success"));
			mes.setUserKey(userKey);
		}
		return mes;
	}
	
	public EnvestResponse authenticate(String userID,String password){
		int code = EnvestConstants.RETURN_CODE_INVALID_USERID_PASSWORD;
		ErrorMessage mes;
		UserInfoDTO userInfo = null;
		try{
			
			userInfo = UserInfoDao.authenticateUser(userID, password);
			if(null != userInfo && userInfo.getPassword().equals(password)){
				code =EnvestConstants.RETURN_CODE_SUCCESS;
			}
		}catch (Exception e){
			code = EnvestConstants.RETURN_CODE_SERVER_ERROR;
			logger.error("Error ouccured while authenticate user",e);
		}
		if(code != EnvestConstants.RETURN_CODE_SUCCESS ){
			mes =  new ErrorMessage(code
					,message.getMessage("message.loginFailure")
					,null
					,message.getMessage("message.failure"));
		}else{
			mes = new ErrorMessage(code
					,message.getMessage("message.userAuthenticated")
					,null
					,message.getMessage("message.success"));
			mes.setUserKey(userInfo.getUserkey());
		}
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
					UserInfoDao.saveUserInfo(d);
				} catch (EnvestException e) {
					d = e.getErrorMessage();
				}
			}
			//UserInfoDao.saveAccessToken(token);
			try {
				Map<String,Object> input = new HashMap<String,Object>(10);
				input.put(EnvestConstants.ENVEST_RESPONSE, d);
				Map<String,Object> output = recommendationEngine.processRequest(input);
				List<AccountDetail> accountsDetailList = ((UserInfo)d).getAccounts();
				Map<String,AccountDetail.AccountProfile> profileData = (Map)output.get(EnvestConstants.USER_PROFILE);
				if(null != profileData){
					for(AccountDetail acc :accountsDetailList){
						List<AccountDetail.AccountProfile> list = new ArrayList<AccountDetail.AccountProfile>();
						list.add(profileData.get(acc.getAccountId()));
						acc.setAccProfile(list);
					}
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Error occurred during recomendationsengine" ,e);
			}
		}
		return d;
	}

}
