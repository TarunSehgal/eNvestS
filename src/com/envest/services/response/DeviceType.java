package com.envest.services.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class DeviceType {
    private String mask;
    private String type;
    
    public String getMask() {
        return mask;
    }
    
    public void setMask(String mask) {
        this.mask = mask;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}