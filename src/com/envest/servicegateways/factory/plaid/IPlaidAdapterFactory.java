package com.envest.servicegateways.factory.plaid;

import com.envest.servicegateways.adapter.plaid.IPlaidAccountAdapter;
import com.envest.servicegateways.adapter.plaid.IPlaidInfoAdapter;
import com.envest.servicegateways.adapter.plaid.IPlaidTransactionAdapter;

public interface IPlaidAdapterFactory {
public IPlaidInfoAdapter getPlaidInfoAdapter();
public IPlaidAccountAdapter getPlaidAccountAdapter();
public IPlaidTransactionAdapter getPlaidTransactionAdapter();
}
