package com.envest.services.components.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plaid.client.response.Account;
import com.plaid.client.response.Transaction;
import com.plaid.client.response.TransactionsResponse;

public class Helper {
	 public static List<AccountCategoryTransactions> getAccountTransactions(TransactionsResponse response)
	 {
		 Map<String, AccountCategoryTransactions> accountsCatTrans= new HashMap<String, AccountCategoryTransactions>();
	 if(response != null)
	 {
		 List<Account> accounts = response.getAccounts();
		 for(int index = 0; index < accounts.size(); index++)
		 {
			 Account account = accounts.get(index);
			 String accountId = account.getId();
			 accountsCatTrans.put(accountId, new AccountCategoryTransactions(account));
		 }
		 
		 List<Transaction> transactions = response.getTransactions();
		 
		 for(int index = 0; index < transactions.size(); index++)
		 {
			 Transaction transaction = transactions.get(index);
			 String accountId = transaction.getAccountId();
			 accountsCatTrans.get(accountId).mergeTransaction(transaction);
		 }
	 }
	 return new ArrayList<AccountCategoryTransactions>(accountsCatTrans.values());
	 }

}
