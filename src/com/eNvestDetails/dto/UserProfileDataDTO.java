package com.eNvestDetails.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

