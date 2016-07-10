package com.eNvestDetails.Response;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.plaid.client.response.MfaResponse;
import com.plaid.client.response.MfaResponse.DeviceType;
import com.plaid.client.response.MfaResponse.Message;
import com.plaid.client.response.MfaResponse.Question;
import com.plaid.client.response.MfaResponse.Selection;


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

    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class DeviceChoiceMfaResponse extends MfaResponseDetail {
        
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
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class QuestionsMfaResponse extends MfaResponseDetail {
        
        private Question[] questions;
        
        @JsonProperty("mfa")
        public Question[] getQuestions() {
            return questions;
        }
        
        public void setQuestions(Question[] questions) {
            this.questions = questions;
        }
        
        @Override
        public String getType() {
        	return QUESTIONS;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class SelectionsMfaResponse extends MfaResponseDetail {
    	
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
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class DeviceListMfaResponse extends MfaResponseDetail {
        
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
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class Message {
        private String message;
        
        public String getMessage() {
            return message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class Question {
        private String question;
        
        public String getQuestion() {
            return question;
        }
        
        public void setQuestion(String question) {
            this.question = question;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class Selection {
    	private String question;
    	private String[] answers;
    	
    	public String[] getAnswers() {
			return answers;
		}
    	
    	public String getQuestion() {
			return question;
		}
    	
    	public void setAnswers(String[] answers) {
			this.answers = answers;
		}
    	
    	public void setQuestion(String question) {
			this.question = question;
		}
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class DeviceType {
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
}
