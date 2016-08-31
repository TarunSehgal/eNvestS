package com.eNvestDetails.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class SelectionsMfaResponse extends MfaResponseDetail {
	
	private Selection[] selections;
	
	@JsonProperty("mfa")
	public Selection[] getSelections() {
		return selections;
	}
	
	public void setSelections(Selection[] selections) {
		this.selections = selections;
	}
	
    @Override
    public String getType() {
    	return SELECTIONS;
    }
}