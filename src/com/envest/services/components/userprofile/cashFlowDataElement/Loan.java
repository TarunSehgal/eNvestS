package com.envest.services.components.userprofile.cashFlowDataElement;

import com.envest.services.response.TransactionDetail;

public class Loan extends DataElement {
	
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public void extractAdditionalInformation(TransactionDetail transaction) {
		description = transaction.getName();
	}

}
