package com.envest.services.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class DeviceListMfaResponse extends MfaResponseDetail {
    
    private DeviceType[] deviceTypes;
    
    @JsonProperty("mfa")
    public DeviceType[] getDeviceTypes() {
        return deviceTypes;
    }
    
    public void setDeviceTypes(DeviceType[] deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
    
    @Override
    public String getType() {
    	return LIST;
    }
}