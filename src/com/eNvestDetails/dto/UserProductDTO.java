package com.eNvestDetails.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ENVEST_USER_PRODUCTS")
public class UserProductDTO {
	@Id @GeneratedValue
	@Column
	private int USER_PRODUCT_ID;
	
	@Column
	private int PRODUCT_ID;
	
	@Column
	private Double INTEREST_RATE;
	@Column
	private Double PRINCIPLE;
	@Column
	private String COMPOUNDING_TENOR;
	@Column 
	private Double MATURITY_VALUE;
	
	@Column
	private Date PURCHASE_DATE;
	
	@Column
	private String USER_ID;
	
	public int getUserProductId() {
		return USER_PRODUCT_ID;
	}
	
	public int getProductId() {
		return PRODUCT_ID;
	}
	
	public void setProductId(int productId) {
		PRODUCT_ID = productId;
	}
	
	public Double getInterest() {
		return INTEREST_RATE;
	}

	public void setInterest(Double interestRate) {
		this.INTEREST_RATE = interestRate;
	}

	public Double getPrinciple() {
		return PRINCIPLE;
	}

	public void setPrinciple(Double principle) {
		this.PRINCIPLE = principle;
	}

	public String getTenor() {
		return COMPOUNDING_TENOR;
	}

	public void setTenor(String tenor) {
		this.COMPOUNDING_TENOR = tenor;
	}

	public Double getMaturityValue() {
		return MATURITY_VALUE;
	}

	public void setMaturityValue(Double maturityValue) {
		this.MATURITY_VALUE = maturityValue;
	}

	public Date getPurchaseDate() {
		return PURCHASE_DATE;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.PURCHASE_DATE = purchaseDate;
	}

	public String getUserId() {
		return USER_ID;
	}

	public void setUserId(String userId) {
		this.USER_ID = userId;
	}
}
