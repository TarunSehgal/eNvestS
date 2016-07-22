package com.eNvestDetails.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.plaid.client.response.Account;
import com.plaid.client.response.Transaction;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccountCategoryTransactions {
	private String accountId;
    private String accountItem;
	private Map<String,CategoryTransactions> transactionsMap = new HashMap<String, CategoryTransactions>();
	
	public AccountCategoryTransactions(Account account)
	{
		this.accountId = account.getId();
		accountItem = account.getItem();
	}
	
	   public List<CategoryTransactions> getCategoryTransactions() {
	        return new ArrayList<CategoryTransactions>(transactionsMap.values());
	    }
	   
	   public String getAccountId() {
	        return accountId;
	    }
	   
	   public String getAccountItem() {
	        return accountItem;
	    }
	   
	   public void mergeTransactions(List<Transaction> transactions) {
	        if(transactions != null && !transactions.isEmpty())
	        {
	        	for(int i=0; i<transactions.size(); i++)
	        	{
	        		Transaction trans = transactions.get(i);
	        		String categoryId = trans.getCategoryId();
	        		if(!transactionsMap.containsKey(categoryId))
	        		{
	        			List<String> category = trans.getCategory();
	        			Category cat = new Category(category, categoryId);
	        			CategoryTransactions catTrans = new CategoryTransactions(cat);
	        			transactionsMap.put(categoryId, catTrans);
	        			
	        		}
	        		transactionsMap.get(categoryId).mergeTransaction(trans);
	        	}
	        }
	    }   
	   
	   public void mergeTransaction(Transaction transaction) {
	        if(transaction != null )
	        {	        		
	        		String categoryId = transaction.getCategoryId();
	        		if(!transactionsMap.containsKey(categoryId))
	        		{
	        			List<String> category = transaction.getCategory();
	        			Category cat = new Category(category, categoryId);
	        			CategoryTransactions catTrans = new CategoryTransactions(cat);
	        			transactionsMap.put(categoryId, catTrans);
	        			
	        		}
	        		transactionsMap.get(categoryId).mergeTransaction(transaction);
	        }
	    } 
}
