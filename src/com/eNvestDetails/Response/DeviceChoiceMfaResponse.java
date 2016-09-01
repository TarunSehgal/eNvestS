package com.eNvestDetails.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class DeviceChoiceMfaResponse extends MfaResponseDetail {
    
    private Message deviceChoiceSentMessage;
    
    @JsonProperty("mfa")
    public Message getDeviceChoiceSentMessage() {
        return deviceChoiceSentMessage;
    }
    
    public void setDeviceChoiceSentMessage(Message deviceChoiceSentMessage) {
        this.deviceChoiceSentMessage = deviceChoiceSentMessage;
    }
    
    @Override
    public String getType() {
    	return DEVICE;
    }
}