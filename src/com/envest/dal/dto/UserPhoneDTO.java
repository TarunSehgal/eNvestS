package com.envest.dal.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="envest_user_phone")
public class UserPhoneDTO {
	
	@Id @GeneratedValue
	@Column
	private Long phone_id;
	@Column
	private String isprimary;
	@Column
	private String type;
	@Column
	private String number;
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isdeleted;
	
	@Column
	private Long userKey;
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isActive;
	
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

	public Long getPhone_id() {
		return phone_id;
	}

	public void setPhone_id(Long phone_id) {
		this.phone_id = phone_id;
	}

	public String getIsprimary() {
		return isprimary;
	}

	public void setIsprimary(String isprimary) {
		this.isprimary = isprimary;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

}
