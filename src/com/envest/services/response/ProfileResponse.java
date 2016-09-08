package com.envest.services.response;

import java.util.List;

import com.envest.dal.dto.UserProfileDataDTO;
import com.envest.services.components.userprofile.DataElement;
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
