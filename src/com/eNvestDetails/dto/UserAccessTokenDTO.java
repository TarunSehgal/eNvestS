package com.eNvestDetails.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="envest_user_accestoken")
public class UserAccessTokenDTO {

	@Id @GeneratedValue
	@Column
	private Long authAccessKey;
	
	@Column
	private String accessToken;
	
	

	@Column
	private Long userKey;
	
	@Column
	private String userBank;
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isdeleted;
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isActive;

	public Long getAuthAccessKey() {
		return authAccessKey;
	}

	public void setAuthAccessKey(Long authAccessKey) {
		this.authAccessKey = authAccessKey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}

	public String getUserBank() {
		return userBank;
	}

	public void setUserBank(String userBank) {
		this.userBank = userBank;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
