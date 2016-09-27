package com.envest.services.components.util.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileData {

	long userKey;
	PersonnelInfo personnelinfo;
	List<Asset> assets;
	List<Liabilities> liabilities;
	Insurance insurance;
	Cashflow cashflow;
	Tax tax;
	Employment employment;
}
