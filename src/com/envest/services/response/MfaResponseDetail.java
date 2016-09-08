package com.envest.services.response;

import com.plaid.client.response.MfaResponse;


public abstract class MfaResponseDetail extends EnvestResponse {
	
	public final static String DEVICE = MfaResponse.DEVICE;
    public final static String LIST = MfaResponse.LIST;
    public final static String QUESTIONS = MfaResponse.QUESTIONS;
    public final static String SELECTIONS = MfaResponse.SELECTIONS;
    
    protected String type;

    public abstract String getType();
    
 /*   protected Long id;
    
    public Long getId() {
        return id;
     }

     
     public void setId(Long id) {
        this.id = id;
     }*/   
}
