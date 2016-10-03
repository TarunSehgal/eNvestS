package com.envest.services.components.util.account;

import com.envest.services.components.util.Product.Product;
import com.envest.services.response.ProfileResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileData {

	long userKey;
	PersonnelInfo personnelinfo;
	Accounts assets;
	Accounts liabilities;
	Insurance insurance;
	Cashflow cashflow;
	Tax tax;
	Employment employment;
	
	//added temporarily so that code does not break.
	//Has to be removed
	ProfileResponse profileResponse;
	
	public ProfileResponse getProfile()
	{
		return profileResponse;
	}
	
	public void setProfile(ProfileResponse profileResponse)
	{
		this.profileResponse = profileResponse;
	}
	
	public Accounts getAssets()
	{
		return assets;
	}
	
	public void setAssets(Accounts asset)
	{
		this.assets = asset;
	}
	
	public void addAsset(Product asset)
	{
		if(asset == null)
		{
		this.assets = new Accounts();
		}
		this.assets.addAccount(asset);
	}
	
	public Accounts getLiabilitys()
	{
		return liabilities;
	}
	
	public void setLiabilitys(Accounts asset)
	{
		this.liabilities = asset;
	}
	
	public void addLiability(Product asset)
	{
		if(asset == null)
		{
		this.liabilities = new Accounts();
		}
		this.liabilities.addAccount(asset);
	}
	
	public long getUserKey()
	{
		return userKey;
	}
	
	public void setUserKey(long userKey)
	{
		this.userKey = userKey;
	}
}
