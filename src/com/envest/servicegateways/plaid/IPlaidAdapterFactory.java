package com.envest.servicegateways.plaid;

public interface IPlaidAdapterFactory {
public IPlaidInfoAdapter getPlaidInfoAdapter();
public IPlaidAccountAdapter getPlaidAccountAdapter();
public IPlaidTransactionAdapter getPlaidTransactionAdapter();
}
