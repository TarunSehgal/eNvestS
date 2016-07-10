package com.eNvestDetails.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="envest_user_address")
public class AddressDTO {
	
	@Id @GeneratedValue
	@Column
	private Long address_id;
	@Column
	private String isprimary;
	@Column
	private String street;
	@Column
	private String city;
	@Column
	private String addressstate;
	@Column
	private String zip;
	
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

	public Long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}

	public String getIsprimary() {
		return isprimary;
	}

	public void setIsprimary(String isprimary) {
		this.isprimary = isprimary;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressstate() {
		return addressstate;
	}

	public void setAddressstate(String addressstate) {
		this.addressstate = addressstate;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	
	
}
