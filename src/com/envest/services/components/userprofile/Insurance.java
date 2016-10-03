package com.envest.services.components.userprofile;

import com.envest.services.response.TransactionDetail;

public class Insurance extends DataElement {
	
	private String insurance;
	
	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	@Override
	public void extractAdditionalInformation(TransactionDetail transaction) {
		insurance = transaction.getName();
	
	}

}
