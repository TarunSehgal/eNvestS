package com.envest.servicegateways.plaid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class PlaidAdapterFactory implements IPlaidAdapterFactory {

	private static IPlaidInfoAdapter infoAdapter;
	private static IPlaidTransactionAdapter transactionAdapter;
	private static IPlaidAccountAdapter accountAdapter;
	@Override
	public IPlaidInfoAdapter getPlaidInfoAdapter() {
		if(infoAdapter == null)
		{
			infoAdapter = new PlaidInfoAdapter();
		}
		return infoAdapter;
	}

	@Override
	public IPlaidAccountAdapter getPlaidAccountAdapter() {
		if(accountAdapter == null)
		{
			accountAdapter = new PlaidAccountAdapter();
		}
		return accountAdapter;
	}

	@Override
	public IPlaidTransactionAdapter getPlaidTransactionAdapter() {
		if(transactionAdapter == null)
		{
			transactionAdapter = new PlaidTransactionAdapter();
		}
		return transactionAdapter;
	}

}
