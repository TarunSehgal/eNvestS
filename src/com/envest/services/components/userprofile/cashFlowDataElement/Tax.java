package com.envest.services.components.userprofile.cashFlowDataElement;

import com.envest.services.response.TransactionDetail;

public class Tax extends DataElement {
	
	@Override
	public void extractAdditionalInformation(TransactionDetail transaction) {
		setAccount(transaction.getAccountId());
	}

}
