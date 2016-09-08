package com.envest.servicegateways.plaid;

import java.util.Map;

import com.envest.services.response.UserInfo;
import com.plaid.client.response.InfoResponse;
import com.plaid.client.response.TransactionsResponse;

public interface IPlaidToEnvestConverter {
public UpdateTransactionResult convertTransactionResponse(TransactionsResponse response, String userBank, Map<String, String> categories);
public UserInfo convertInforesponseToUserinfo(InfoResponse response, String bank, String userId);
}
