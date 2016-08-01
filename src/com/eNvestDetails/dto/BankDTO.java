package com.eNvestDetails.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ENVEST_BANKS")
public class BankDTO {
	@Id
	@Column
	private int BANK_ID;
	@Column
	private String BANK_NAME;
	@Column
	private Double BANK_INTEREST;
	
	
	public int getBankId() {
		return BANK_ID;
	}

	public void setBankId(int bankId) {
		this.BANK_ID = bankId;
	}
	
	public String getBankName() {
		return BANK_NAME;
	}

	public void setBankName(String bankName) {
		this.BANK_NAME = bankName;
	}

	public Double getInterest() {
		return BANK_INTEREST;
	}

	public void setInterest(Double interest) {
		this.BANK_INTEREST = interest;
	}
}
