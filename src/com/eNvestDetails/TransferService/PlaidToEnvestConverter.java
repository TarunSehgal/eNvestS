package com.eNvestDetails.TransferService;

import java.util.List;

import org.springframework.stereotype.Component;

import com.plaid.client.response.Account;
import com.plaid.client.response.TransactionsResponse;

@Component
public class PlaidToEnvestConverter implements IPlaidToEnvestConverter {

	@Override
	public UpdateTransactionResult convertTransactionResponse(TransactionsResponse transactionResponse) {
		List<Account> acc = null;
/*		if(null !=transactionResponse.getAccounts()){
			acc = transactionResponse.getAccounts();
		}
		
		if (type == EnvestConstants.GET_ACCOUNTS|| type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
			accDetails.addAll(CommonUtil.parseAccounts(acc, token.getUserBank()));
		}
			
		if (type == EnvestConstants.GET_TRANSACTIONS || type == EnvestConstants.GET_ACCOUNT_TRANSACTIONS){						
			transactionsList.addAll(CommonUtil.parseTransaction(transactionResponse.getTransactions(),summaryMap,serviceUtil.getCategories()));
		}*/
		return null;
	}

}
