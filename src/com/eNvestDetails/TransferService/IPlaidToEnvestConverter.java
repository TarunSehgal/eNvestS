package com.eNvestDetails.TransferService;

import com.plaid.client.response.TransactionsResponse;

public interface IPlaidToEnvestConverter {
public UpdateTransactionResult convertTransactionResponse(TransactionsResponse transactionResponse);
}
