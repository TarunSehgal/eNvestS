package com.eNvestDetails.TransferService;

import com.eNvestDetails.Response.UserInfo;
import com.plaid.client.response.InfoResponse;

public interface IPlaidInfoAdapter {
	UserInfo convertToUserInfo(InfoResponse r,String bank,String userId);
}
