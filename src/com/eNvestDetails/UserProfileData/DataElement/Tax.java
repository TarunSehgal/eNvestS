package com.eNvestDetails.UserProfileData.DataElement;

import com.eNvestDetails.Response.TransactionDetail;

public class Tax extends DataElement {
	
	@Override
	public void extractAdditionalInformation(TransactionDetail transaction) {
		setAccount(transaction.getAccountId());
	}

}
