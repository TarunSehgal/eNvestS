package com.eNvestDetails.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.plaid.client.response.Transaction;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CategoryTransactions {
	private Category category;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public CategoryTransactions(Category category)
	{
		this.category = category;
	}
	
	   public Category getCategory() {
	        return category;
	    }
	   
	   public void mergeTransactions(List<Transaction> transactionList) {
	        if(transactionList != null && !transactionList.isEmpty())
	        {
	        	transactions.addAll(transactionList);
	        }
	    }
	   
	   public void mergeTransaction(Transaction transaction) {
	        if(transaction != null)
	        {
	        	transactions.add(transaction);
	        }
	    }
	   
	   public List<Transaction> getTransactions() {
	        return transactions;
	    }	   
}
