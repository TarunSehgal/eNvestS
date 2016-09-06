package com.eNvestDetails.DAL.DTO;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="envest_user_profiledata")
@JsonIgnoreProperties( { "userProfileKey","userKey" })
public class UserProfileDataDTO {

	@Id @GeneratedValue
	@Column
	private Long userProfileKey;
	
	@Column
	private String type;
	
	@Column
	private String subType;
	
	@Column
	private double amount;
	
	@Column
	private Long userKey;
	
	@Column
	private String id;
	
	@Column
	private String bank;
	
	@Column
	private String accountId;
	
	private int addCounter;
	
	private String addAlways;
	
	@Column
	private String employer;
	
	@Transient
	private List<String> plaidCategory = null;
	
	@Transient
	private Date inflowOutflowStartDate = null;


	@Column
	private Date interestAsOf;
	
	@Column
	@JsonFormat(pattern="MM-dd-yyyy")
	private Date startDate;
	
	@Column
	@JsonFormat(pattern="MM-dd-yyyy")
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}

	public Date getInflowOutflowStartDate() {
		return inflowOutflowStartDate;
	}

	public void setInflowOutflowStartDate(Date inflowOutflowStartDate) {
		this.inflowOutflowStartDate = inflowOutflowStartDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Date getInterestAsOf() {
		return interestAsOf;
	}

	public void setInterestAsOf(Date interestAsOf) {
		this.interestAsOf = interestAsOf;
	}

	public List<String> getPlaidCategory() {
		return plaidCategory;
	}

	public void setPlaidCategory(List<String> plaidCategory) {
		this.plaidCategory = plaidCategory;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getAddAlways() {
		return addAlways;
	}

	public void setAddAlways(String addAlways) {
		this.addAlways = addAlways;
	}

	public int getAddCounter() {
		return addCounter;
	}

	public void setAddCounter(int addCounter) {
		this.addCounter = addCounter;
	}

	public UserProfileDataDTO(){
		
	}
	
	public UserProfileDataDTO(String id,String type,String subType,String addAlways,int addCounter){
		this.id = id;
		this.type = type;
		this.subType = subType;
		this.addAlways = addAlways;
		this.addCounter = addCounter;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}

	public Long getUserProfileKey() {
		return userProfileKey;
	}

	public void setUserProfileKey(Long userProfileKey) {
		this.userProfileKey = userProfileKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}

