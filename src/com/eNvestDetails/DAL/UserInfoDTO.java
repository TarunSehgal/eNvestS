
package com.eNvestDetails.DAL;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate4.type.EncryptedStringType;

@TypeDef(
	    name="encryptedString", 
	    typeClass=EncryptedStringType.class, 
	    parameters= {
	        @Parameter(name="encryptorRegisteredName", value="myHibernateStringEncryptor")
	    }
	)
@Entity
@Table(name="envest_user_info")
public class UserInfoDTO implements Serializable{
	

	
	@Id @GeneratedValue
	@Column
	private Long userkey;
	
	public Long getUserkey() {
		return userkey;
	}

	public void setUserkey(Long userkey) {
		this.userkey = userkey;
	}

	@Column
	//(unique=true)
	private String userID;

	@Column
	private String userName;
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isDeleted;
	
	@Column (columnDefinition="VARCHAR(1) default 'N'" )
	private String isActive;
	
	@Column(unique = true)
	private String envestUserID;
	
	@Column (columnDefinition="VARCHAR(1000) default 'N'" )
	//@Type(type="encryptedString")
	private String password;
	
	@Column
	 private String accessToken;


	  public String getAccessToken() {
	  	return accessToken;
	  }

	  public void setAccessToken(String accessToken) {
	  	this.accessToken = accessToken;
	  }
	
	public String getEnvestUserID() {
		return envestUserID;
	}

	public void setEnvestUserID(String envestUserID) {
		this.envestUserID = envestUserID;
	}
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
		
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}


}
