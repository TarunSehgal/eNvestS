package com.envest.services.components.util.account;

import org.springframework.stereotype.Component;

import com.envest.services.response.AccountDetail;

@Component
public class AccountBuilder {
	
	public Account accountBuilder(AccountDetail accDetail){
		Account acc = null;
		
		switch (getAccountType(accDetail)){
		case checking: 
			acc = buildDepositoryAccount(AccountType.checking,accDetail);
			break;
		case saving:
			acc = buildDepositoryAccount(AccountType.saving,accDetail);
			break;
		case creditcard:
			acc = buildCreditCardAccount(AccountType.creditcard,accDetail);
			break;
		default:
			break;
		}
		return acc;
	}
	
	private Account buildDepositoryAccount(AccountType type,AccountDetail accDetail){
		DepositoryBankAccount account = new DepositoryBankAccount();
		if(accDetail.getNumbers() != null){
			account.setAccountNumber(accDetail.getNumbers().getAccount());
			account.setRoutingNumber(accDetail.getNumbers().getRouting());
		}
		account.setAccountId(accDetail.getAccountId());
		account.setItem(accDetail.getItem());
		account.setBankName(accDetail.getInstitutionType());
		if(accDetail.getBalance() != null){
			account.setBalance(accDetail.getBalance().getAvailable());
			
		}
		account.setAccountType(type);
		return account;
	}
	
	private Account buildCreditCardAccount (AccountType type,AccountDetail accDetail){
		CreditCardAccount account = new CreditCardAccount();
		account.setAccountId(accDetail.getAccountId());
		account.setItem(accDetail.getItem());
		account.setBankName(accDetail.getInstitutionType());
		if(accDetail.getNumbers() != null){
			account.setAccountNumber(accDetail.getNumbers().getAccount());			
		}
		if(accDetail.getBalance() != null){
			account.setBalance(accDetail.getBalance().getAvailable());			
		}
		account.setAccountType(type);
		return account;
	}
	
	private AccountType getAccountType(AccountDetail accDetail){
		AccountType type = AccountType.Invalid;
		if(null != accDetail.getSubtype()){
			switch (accDetail.getSubtype()){
			case "checking": 
				type = AccountType.checking;
				break;
			case "saving":
				type = AccountType.saving;
				break;
			case "credit":
				type = AccountType.creditcard;
				break;
			default:
				break;
			}
		}
		
		return type;
	}

}


	