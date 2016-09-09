package com.envest.services.response;

import java.util.Date;
import java.util.List;

import com.envest.dal.dto.UserProfileDataDTO;
import com.envest.services.components.userprofile.DataElement;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProfileResponse extends EnvestResponse {
	
	private List<DataElement> profile;
	
	@JsonFormat(pattern="MM-dd-yyyy")
	private Date startDate;
	
	@JsonFormat(pattern="MM-dd-yyyy")
	private Date endDate;

	public Date getStartDate() {
		return startDate;
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

	public List<DataElement> getProfile() {
		return profile;
	}

	public void setProfile(List<DataElement> profile) {
		this.profile = profile;
	}

}
