package com.envest.servicegateways.plaid;

import com.envest.services.response.DeviceChoiceMfaResponse;
import com.envest.services.response.Message;
import com.envest.services.response.MfaResponseDetail;
import com.envest.services.response.Question;
import com.envest.services.response.QuestionsMfaResponse;
import com.plaid.client.response.MfaResponse;

class MFAHandler {

	public static MfaResponseDetail handleMfaException(MfaResponse mfa,String bank){
		MfaResponseDetail mfaRes = null;
		if(mfa.getType().equals(mfa.DEVICE)){
			 mfaRes = handleDeviceChoiceMFA((MfaResponse.DeviceChoiceMfaResponse) mfa);
			
			
		}else if(mfa.getType().equals(mfa.QUESTIONS)){
			mfaRes = handleQuestionMfa((MfaResponse.QuestionsMfaResponse) mfa);
		}
		mfaRes.setAccessToken(mfa.getAccessToken());
		//mfaRes.setId(id); //to bet set			
		mfaRes.setResponseFor(bank);
		return mfaRes;
	}

	private static MfaResponseDetail handleQuestionMfa(MfaResponse.QuestionsMfaResponse mfa) {
		QuestionsMfaResponse mfaRes;
		mfaRes = new QuestionsMfaResponse();
		Question q = null;
		Question[] qArray = null;
		
		if(null != mfa.getQuestions()){
			qArray= new Question[mfa.getQuestions().length];
			int i =0;
			for(MfaResponse.Question qResponse :mfa.getQuestions()){
				q = new Question();
				q.setQuestion(qResponse.getQuestion());
				qArray[i] = q;
				i++;
			}
		}			
		mfaRes.setQuestions(qArray);
		return mfaRes;
	}

	private static MfaResponseDetail handleDeviceChoiceMFA(MfaResponse.DeviceChoiceMfaResponse mfa) {
		DeviceChoiceMfaResponse mfaRes;
		mfaRes = new 
				DeviceChoiceMfaResponse();			
		Message msg = new Message();
		msg.setMessage(mfa.getDeviceChoiceSentMessage().getMessage());
		mfaRes.setDeviceChoiceSentMessage(msg);
		return mfaRes;
	}	
}
