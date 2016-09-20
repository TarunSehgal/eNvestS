package com.envest.servicegateways.factory.plaid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.envest.servicegateways.adapter.plaid.IPlaidAccountAdapter;
import com.envest.servicegateways.adapter.plaid.IPlaidInfoAdapter;
import com.envest.servicegateways.adapter.plaid.IPlaidTransactionAdapter;
import com.envest.servicegateways.adapter.plaid.PlaidAccountAdapter;
import com.envest.servicegateways.adapter.plaid.PlaidInfoAdapter;
import com.envest.servicegateways.adapter.plaid.PlaidTransactionAdapter;

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
