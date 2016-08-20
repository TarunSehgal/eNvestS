package com.eNvestDetails.TransferService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.eNvestDetails.Response.AccountDetail;
import com.plaid.client.response.Account;

class PlaidAccountAdapter implements IPlaidAccountAdapter {

	public List<AccountDetail> convertPlaidAccountsToAccountDetails(List<Account> acc,String bank){
		List<AccountDetail> accDetails = null;
		accDetails = new ArrayList<AccountDetail>(10);
		for (Account a : acc) {			
			accDetails.add(convertPlaidAccountToAccountDetail(a, bank));
		}
		return accDetails;
	}

	public AccountDetail convertPlaidAccountToAccountDetail(Account account,String bank)
	{
		AccountDetail accounts = new AccountDetail();
		accounts.setInstitutionType(account.getInstitutionType());
		accounts.setType(account.getType());

		accounts.setBalance(getAccountBalance(account));
		
		accounts.setMeta(getAccountMetadata(account));
		if(null != account.getNumbers()){
			accounts.setNumbers(getAccountNumbers(account));
		}
							
		accounts.setType(account.getType());
		accounts.setItem(account.getItem());
		accounts.setAccountId(account.getId());
		accounts.setResponseFor(bank);
		accounts.setYield(getRandmonInterst());
		
		return accounts;
	}
	
	public AccountDetail.AccountNumbers getAccountNumbers(Account account) {
		AccountDetail.AccountNumbers accNumber = new AccountDetail.AccountNumbers();
		accNumber.setAccount(account.getNumbers().getAccount());
		accNumber.setRouting(account.getNumbers().getRouting());
		return accNumber;
	}

	public AccountDetail.Balance getAccountBalance(Account account) {
		AccountDetail.Balance balance = new AccountDetail.Balance();
		balance.setAvailable(account.getBalance().getAvailable());
		balance.setCurrent(account.getBalance().getCurrent());
		return balance;
	}

	public AccountDetail.AccountMeta getAccountMetadata(Account account) {
		AccountDetail.AccountMeta meta = new AccountDetail.AccountMeta();
		
		meta.setName(account.getMeta().getName());
		meta.setNumber(account.getMeta().getNumber());
		meta.setLimit(account.getMeta().getLimit());
		return meta;
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
}
