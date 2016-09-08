package com.envest.services.components.userprofile;

import com.envest.services.response.TransactionDetail;

public class Salary extends DataElement {
	
	private String employer;

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}
	
	private Double recentSalary;

	public Double getRecentSalary() {
		return recentSalary;
	}

	public void setRecentSalary(Double recentSalary) {
		this.recentSalary = recentSalary;
	}

	@Override
	public void extractAdditionalInformation(TransactionDetail transaction) {
		employer = transaction.getName();
		
		if(null == recentSalary){
			recentSalary = getAmount(transaction);
		}
		
	}
}
