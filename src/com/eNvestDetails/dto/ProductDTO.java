package com.eNvestDetails.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ENVEST_PRODUCTS")
public class ProductDTO {

	@Id
	@Column
	private int PRODUCT_ID;
	
	@Column
	private String PRODUCT_NAME;

	@Column
	private Double PRODUCT_INTEREST;
	
	@Column
	private int BANK_ID;
	
	@Column
	private int MATURITY_YEARS;
	
	@Column
	private Double MIN_AMOUNT;
	
	@Column
	private int MIN_TENURE;
	
	@Column
	private int MAX_TENURE;
	
	@Column 
	private Double PRE_MATURE_CLOSE_CHARGE;
	
	@Column
	private Double EMI_DEFAULT_CHARGE;	
		
	public int getProductId() {
		return PRODUCT_ID;
	}	

	public void setProductId(int productId) {
		PRODUCT_ID = productId;
	}	
	public String getProductName() {
		return PRODUCT_NAME;
	}

	public void setProductName(String productName) {
		this.PRODUCT_NAME = productName;
	}

	public Double getProductInterest() {
		return PRODUCT_INTEREST;
	}
	
	public void setProductInterest(Double productInterest) {
		this.PRODUCT_INTEREST = productInterest;
	}

	public int getMaturityYears() {
		return MATURITY_YEARS;
	}
	
	public void setMaturityYears(int maturityYears) {
		this.MATURITY_YEARS = maturityYears;
	}
	public int  getBankId() {
		return BANK_ID;
	}

	public void setBankId(int  bankId) {
		this.BANK_ID = bankId;
	}

	public Double getMinAmount() {
		return MIN_AMOUNT;
	}

	public void setMinAmount(Double minAmount) {
		this.MIN_AMOUNT = minAmount;
	}

	
	public int getMinTenure() {
		return MIN_TENURE;
	}

	public void setMinTenure(int minTenure) {
		this.MIN_TENURE = minTenure;
	}

	public int getMaxTenure() {
		return MAX_TENURE;
	}

	public void setMaxTenure(int maxTenure) {
		this.MAX_TENURE = maxTenure;
	}
	public Double getPreMatureClosureCharges() {
		return PRE_MATURE_CLOSE_CHARGE;
	}

	public void setPreMatureClosureCharges(Double preMatureClosureCharges) {
		this.PRE_MATURE_CLOSE_CHARGE = preMatureClosureCharges;
	}
	public Double getEmiDefaultCharge() {
		return EMI_DEFAULT_CHARGE;
	}

	public void setEmiDefaultCharge(Double emiDefaultCharge) {
		this.EMI_DEFAULT_CHARGE = emiDefaultCharge;
	}	
}
