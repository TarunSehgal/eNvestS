package com.eNvestDetails.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties( { "accessToken" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnvestResponse {
	private String status = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String authToken = null;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	private String accessToken = null;

	private String responseFor = null;
	
	private String responseType = null;
	
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEnvestUserid() {
		return envestUserid;
	}

	public void setEnvestUserid(String envestUserid) {
		this.envestUserid = envestUserid;
	}

	private String envestUserid;

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	private Long userKey;

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}
	
	public String getResponseFor() {
		return responseFor;
	}

	public void setResponseFor(String responseFor) {
		this.responseFor = responseFor;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public EnvestResponse() {

	}

}
