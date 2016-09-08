package com.envest.servicegateways.plaid;

import com.envest.services.response.UserInfo;
import com.plaid.client.response.InfoResponse;

public interface IPlaidInfoAdapter {
	UserInfo convertToUserInfo(InfoResponse r,String bank,String userId);
}
