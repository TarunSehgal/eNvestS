package com.eNvestDetails.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.math.DoubleRange;

import com.eNvestDetails.Response.AccountDetail;
import com.eNvestDetails.Response.MfaResponseDetail;
import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.response.Account;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.MfaResponse;
import com.plaid.client.response.Transaction;
import com.plaid.client.response.InfoResponse.Address;
import com.plaid.client.response.InfoResponse.Email;
import com.plaid.client.response.InfoResponse.Info;
import com.plaid.client.response.InfoResponse.PhoneNumber;

public class CommonUtil {
	
	public static MfaResponseDetail handleMfaException(MfaResponse mfa,String bank){
		MfaResponseDetail mfaRes = null;
		//MfaResponse mfa = e.getMfaResponse();
		if(mfa.getType().equals(mfa.DEVICE)){
			 mfaRes = new 
					MfaResponseDetail.DeviceChoiceMfaResponse();			
			MfaResponseDetail.Message msg = new MfaResponseDetail.Message();
			msg.setMessage(((MfaResponse.DeviceChoiceMfaResponse
					)mfa).getDeviceChoiceSentMessage().getMessage());
			((MfaResponseDetail.DeviceChoiceMfaResponse)mfaRes).setDeviceChoiceSentMessage(msg);
			
			
		}else if(mfa.getType().equals(mfa.QUESTIONS)){
			mfaRes = new 
					MfaResponseDetail.QuestionsMfaResponse();
			MfaResponseDetail.Question q = null;
			MfaResponseDetail.Question[] qArray = null;
			
			if(null != ((MfaResponse.QuestionsMfaResponse
					)mfa).getQuestions()){
				qArray= new MfaResponseDetail.Question[((MfaResponse.QuestionsMfaResponse
						)mfa).getQuestions().length];
				int i =0;
				for(MfaResponse.Question qResponse :((MfaResponse.QuestionsMfaResponse
						)mfa).getQuestions()){
					q = new MfaResponseDetail.Question();
					q.setQuestion(qResponse.getQuestion());
					qArray[i] = q;
					i++;
				}
			}			
			((MfaResponseDetail.QuestionsMfaResponse)mfaRes).setQuestions(qArray);
		}
		mfaRes.setAccessToken(mfa.getAccessToken());
		//mfaRes.setId(id); //to bet set			
		mfaRes.setResponseFor(bank);
		return mfaRes;
	}	
	
	public static List<AccountDetail> parseAccounts(List<Account> acc,String bank){
		List<AccountDetail> accDetails = null;
		accDetails = new ArrayList<AccountDetail>(10);
		AccountDetail accounts = null;
		for (Account a : acc) {
			accounts = new AccountDetail();
			accounts.setInstitutionType(a.getInstitutionType());
			accounts.setType(a.getType());
			AccountDetail.Balance balance = new AccountDetail.Balance();
			balance.setAvailable(a.getBalance().getAvailable());
			balance.setCurrent(a.getBalance().getCurrent());
			//accounts.setAvailableBalance(a.getBalance().getAvailable());
			//accounts.setAvailableBalance(a.getBalance().getCurrent());
			accounts.setBalance(balance);
			AccountDetail.AccountMeta meta = new AccountDetail.AccountMeta();
			
			meta.setName(a.getMeta().getName());
			meta.setNumber(a.getMeta().getNumber());
			meta.setLimit(a.getMeta().getLimit());
			accounts.setMeta(meta);
			if(null != a.getNumbers()){
				AccountDetail.AccountNumbers accNumber = new AccountDetail.AccountNumbers();
				accNumber.setAccount(a.getNumbers().getAccount());
				accNumber.setRouting(a.getNumbers().getRouting());
				accounts.setNumbers(accNumber);
			}
								
			accounts.setType(a.getType());
			accounts.setItem(a.getItem());
			accounts.setAccountId(a.getId());
			accounts.setResponseFor(bank);
			accounts.setYield(getRandmonInterst());
			accDetails.add(accounts);
		}
		return accDetails;
	}
	
	/*
	 * temp method will be removed later
	 */
	private static double getRandmonInterst(){
		DecimalFormat df = new DecimalFormat("#.####");
		double returnV = 0.0;
		double minV = .01;
		double maxV = .05;
		returnV = (new Random().nextDouble()* (maxV -minV)) + minV;
		BigDecimal bd = new BigDecimal(returnV);
		
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static  List<TransactionDetail> parseTransaction(List<Transaction> transactions,Map<String,UserInfo.Summary> summaryMap,Map<String, String> categories){
		TransactionDetail transactionBean = null;		
		List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
		//Map<String, String> categories = userServiceUtil.getCategories();
		for(Transaction t : transactions){
			
			transactionBean = new TransactionDetail();
			transactionBean.setAccountId(t.getAccountId());
			transactionBean.setAmount(t.getAmount());
			transactionBean.setCategory(t.getCategory());
			transactionBean.setCategoryId(t.getCategoryId());
			transactionBean.setEntityId(t.getEntityId());
			transactionBean.setDate(t.getDate());
			transactionBean.setTransactionId(t.getId());
			TransactionDetail.TransactionMeta meta = null;
			if(t.getMeta() != null){
				meta = new TransactionDetail.TransactionMeta();
				meta.setContact(t.getMeta().getContact());
				meta.setIds(t.getMeta().getIds());
				TransactionDetail.Location location = new TransactionDetail.Location();
				location.setAddress(t.getMeta().getLocation().getAddress());
				location.setCity(t.getMeta().getLocation().getCity());
				TransactionDetail.Coordinates coordinates = null;
				
				if(null != t.getMeta().getLocation().getCoordinates()){
					coordinates = new TransactionDetail.Coordinates();
					coordinates.setLatitude(t.getMeta().getLocation().getCoordinates()
							.getLatitude());
					coordinates.setLongitude(t.getMeta().getLocation().getCoordinates()
							.getLongitude());
				}							
				location.setCoordinates(coordinates);
				location.setState(t.getMeta().getLocation().getState());
				location.setZip(t.getMeta().getLocation().getZip());
				meta.setLocation(location);
			}
			transactionBean.setMeta(meta);
			transactionBean.setName(t.getName());
			transactionBean.setPending(t.isPending());
			transactionBean.setPendingTransactionId(t.getPendingTransactionId());
			transactionBean.setType(t.getType());
			transactionsList.add(transactionBean);
			if(null != summaryMap.get(t.getAccountId())){
				UserInfo.Summary summary = (UserInfo.Summary) summaryMap.get(t.getAccountId());
				if(t.getAmount() < 0.0){
					summary.setInflow(summary.getInflow() + (t.getAmount() * (-1.0)));
				}else{
					summary.setOutflow(summary.getOutflow() + (t.getAmount()* (-1.0)));	
				}			
				String categoryHierarchy = categories.get(t.getCategoryId());
				if(null != categoryHierarchy && categoryHierarchy.contains("Bank Fees")){
					summary.setTotalBankFee(summary.getTotalBankFee() + t.getAmount());								
				}
				if(null != categoryHierarchy && categoryHierarchy.contains("Interest")){
					summary.setTotalInterest(summary.getTotalInterest() + t.getAmount());			
				}
			}else{
				UserInfo.Summary summary =  new UserInfo.Summary();
				
				if(t.getAmount() < 0.0){
					summary.setInflow((null != summary.getInflow()? summary.getInflow():0.0) + (t.getAmount() * (-1.0)));
					summary.setOutflow(0.0);
				}else{
					summary.setOutflow((null != summary.getOutflow()?summary.getOutflow():0.0) + (t.getAmount()* (-1.0)));
					summary.setInflow(0.0);
				}
				String categoryHierarchy = categories.get(t.getCategoryId());
				if(null != categoryHierarchy && categoryHierarchy.contains("Bank Fees")){
					summary.setTotalBankFee(t.getAmount());								
				}
				if(null != categoryHierarchy && categoryHierarchy.contains("Interest")){
					summary.setTotalInterest( t.getAmount());			
				}
							
				summary.setAccountNumber(t.getAccountId());
				summaryMap.put(t.getAccountId(), summary);				
			}
			
		}
		return transactionsList;
	}
	
	public static UserInfo parseInfoResponse(InfoResponse r,String bank,String userId){
		UserInfo userInfo = new UserInfo();
		Info f = r.getInfo();
		
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
		return userInfo;
	}

}
