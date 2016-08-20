package com.eNvestDetails.TransferService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eNvestDetails.Response.TransactionDetail;
import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.response.Transaction;

class PlaidTransactionAdapter implements IPlaidTransactionAdapter {

public List<TransactionDetail> convertTransactionsToTransactionDetail(List<Transaction> transactions){
		
		List<TransactionDetail> transactionsList = new ArrayList<TransactionDetail>();
		for(Transaction t : transactions){
			
			TransactionDetail transactionBean = convertTransactionToTransactionDetail(t);
			transactionsList.add(transactionBean);			
		}
		return transactionsList;
	}

public Map<String,UserInfo.Summary> convertTransactionsToSummaries(List<Transaction> transactions,Map<String, String> categories){
	
	Map<String,UserInfo.Summary> summaryMap = new HashMap<String,UserInfo.Summary>();
	for(Transaction t : transactions){
		String categoryHierarchy = categories.get(t.getCategoryId());
		UserInfo.Summary summary = getAccountSummaryFromTransaction(categoryHierarchy, t);
		summaryMap.put(t.getAccountId(), summary);		
	}
	return summaryMap;
}

	public TransactionDetail convertTransactionToTransactionDetail(Transaction transaction) {
		TransactionDetail transactionBean = new TransactionDetail();
		transactionBean.setAccountId(transaction.getAccountId());
		transactionBean.setAmount(transaction.getAmount());
		transactionBean.setCategory(transaction.getCategory());
		transactionBean.setCategoryId(transaction.getCategoryId());
		transactionBean.setEntityId(transaction.getEntityId());
		transactionBean.setDate(transaction.getDate());
		transactionBean.setTransactionId(transaction.getId());
		TransactionDetail.TransactionMeta meta = getMetadataForTransaction(transaction);
		transactionBean.setMeta(meta);
		transactionBean.setName(transaction.getName());
		transactionBean.setPending(transaction.isPending());
		transactionBean.setPendingTransactionId(transaction.getPendingTransactionId());
		transactionBean.setType(transaction.getType());
		return transactionBean;
	}

	public TransactionDetail.TransactionMeta getMetadataForTransaction(Transaction transaction) {
		TransactionDetail.TransactionMeta meta = null;
		if(transaction.getMeta() != null){
			meta = new TransactionDetail.TransactionMeta();
			meta.setContact(transaction.getMeta().getContact());
			meta.setIds(transaction.getMeta().getIds());
			TransactionDetail.Location location = new TransactionDetail.Location();
			location.setAddress(transaction.getMeta().getLocation().getAddress());
			location.setCity(transaction.getMeta().getLocation().getCity());
			TransactionDetail.Coordinates coordinates = null;
			
			if(null != transaction.getMeta().getLocation().getCoordinates()){
				coordinates = new TransactionDetail.Coordinates();
				coordinates.setLatitude(transaction.getMeta().getLocation().getCoordinates()
						.getLatitude());
				coordinates.setLongitude(transaction.getMeta().getLocation().getCoordinates()
						.getLongitude());
			}							
			location.setCoordinates(coordinates);
			location.setState(transaction.getMeta().getLocation().getState());
			location.setZip(transaction.getMeta().getLocation().getZip());
			meta.setLocation(location);
		}
		return meta;
	}
	

	public UserInfo.Summary getAccountSummaryFromTransaction(String categoryHierarchy, Transaction transaction) {
		UserInfo.Summary summary =  new UserInfo.Summary();
		
		if(transaction.getAmount() < 0.0){
			summary.setInflow((null != summary.getInflow()? summary.getInflow():0.0) + (transaction.getAmount() * (-1.0)));
			summary.setOutflow(0.0);
		}else{
			summary.setOutflow((null != summary.getOutflow()?summary.getOutflow():0.0) + (transaction.getAmount()* (-1.0)));
			summary.setInflow(0.0);
		}

		if(null != categoryHierarchy && categoryHierarchy.contains("Bank Fees")){
			summary.setTotalBankFee(transaction.getAmount());								
		}
		if(null != categoryHierarchy && categoryHierarchy.contains("Interest")){
			summary.setTotalInterest( transaction.getAmount());			
		}
					
		summary.setAccountNumber(transaction.getAccountId());
		return summary;
	}
}
