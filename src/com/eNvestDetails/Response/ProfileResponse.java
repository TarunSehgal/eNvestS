package com.eNvestDetails.Response;

import java.util.List;

import com.eNvestDetails.dto.UserProfileDataDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProfileResponse extends EnvestResponse {
	
	private List<UserProfileDataDTO> profile;

	public List<UserProfileDataDTO> getProfile() {
		return profile;
	}

	public void setProfile(List<UserProfileDataDTO> profile) {
		this.profile = profile;
	}

}
