package com.eNvestDetails.TransferService;

public interface IPlaidAdapterFactory {
public IPlaidInfoAdapter getPlaidInfoAdapter();
public IPlaidAccountAdapter getPlaidAccountAdapter();
public IPlaidTransactionAdapter getPlaidTransactionAdapter();
}
