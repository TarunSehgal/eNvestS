package com.eNvestDetails.Response;

import java.util.List;

import com.eNvestDetails.UserProfileData.DataElement.DataElement;
import com.eNvestDetails.dto.UserProfileDataDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProfileResponse extends EnvestResponse {
	
	private List<DataElement> profile;

	public List<DataElement> getProfile() {
		return profile;
	}

	public void setProfile(List<DataElement> profile) {
		this.profile = profile;
	}

}
