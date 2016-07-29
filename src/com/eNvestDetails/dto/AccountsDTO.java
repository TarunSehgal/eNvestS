package com.eNvestDetails.dto;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="envest_user_account")
public class AccountsDTO {

	@Id @GeneratedValue
	@Column
	private Long accountKey;
	
	@Column
	private String institutiontype;

	@Column
	private Double availableBalance;
	
	@Column
	private Double currentBalance;
	
	@Column
	private String name;
	
	@Column
	private String accountNumber;
	
	@Column
	private String subType;
	
	@Column
	private String type;
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isdeleted;
	
	@Column
	private Long userKey;
	
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isActive;
	
	@Column
	private Date balanceAsOF;
	
	@Column
	private boolean isPrimary;
	
	@Column
	private String accountId;
	
	@Column
	private String itemId;
	
	/*@Column
	private Date balanceAsOfDate;
	
	
	public Date getBalanceAsOfDate() {
		return balanceAsOfDate;
	}

	public void setBalanceAsOfDate(Date balanceAsOfDate) {
		this.balanceAsOfDate = balanceAsOfDate;
	}*/

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Date  getBalanceAsOF() {
		return balanceAsOF;
	}

	public void setBalanceAsOF(Date  balanceAsOF) {
		this.balanceAsOF = balanceAsOF;
	}

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}

	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getInstitutiontype() {
		return institutiontype;
	}

	public void setInstitutiontype(String institutiontype) {
		this.institutiontype = institutiontype;
	}
	public Long getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(Long accountKey) {
		this.accountKey = accountKey;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	
	
}
