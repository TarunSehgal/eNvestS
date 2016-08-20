package com.eNvestDetails.util;

import com.eNvestDetails.Response.MfaResponseDetail;
import com.plaid.client.response.MfaResponse;

public class CommonUtil {
	
	public static MfaResponseDetail handleMfaException(MfaResponse mfa,String bank){
		MfaResponseDetail mfaRes = null;
		//MfaResponse mfa = e.getMfaResponse();
		if(mfa.getType().equals(mfa.DEVICE)){
			 mfaRes = new 
					MfaResponseDetail.DeviceChoiceMfaResponse();			
			MfaResponseDetail.Message msg = new MfaResponseDetail.Message();
			msg.setMessage(((MfaResponse.DeviceChoiceMfaResponse
					)mfa).getDeviceChoiceSentMessage().getMessage());
			((MfaResponseDetail.DeviceChoiceMfaResponse)mfaRes).setDeviceChoiceSentMessage(msg);
			
			
		}else if(mfa.getType().equals(mfa.QUESTIONS)){
			mfaRes = new 
					MfaResponseDetail.QuestionsMfaResponse();
			MfaResponseDetail.Question q = null;
			MfaResponseDetail.Question[] qArray = null;
			
			if(null != ((MfaResponse.QuestionsMfaResponse
					)mfa).getQuestions()){
				qArray= new MfaResponseDetail.Question[((MfaResponse.QuestionsMfaResponse
						)mfa).getQuestions().length];
				int i =0;
				for(MfaResponse.Question qResponse :((MfaResponse.QuestionsMfaResponse
						)mfa).getQuestions()){
					q = new MfaResponseDetail.Question();
					q.setQuestion(qResponse.getQuestion());
					qArray[i] = q;
					i++;
				}
			}			
			((MfaResponseDetail.QuestionsMfaResponse)mfaRes).setQuestions(qArray);
		}
		mfaRes.setAccessToken(mfa.getAccessToken());
		//mfaRes.setId(id); //to bet set			
		mfaRes.setResponseFor(bank);
		return mfaRes;
	}		
}
