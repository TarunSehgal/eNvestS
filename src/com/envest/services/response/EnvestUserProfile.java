package com.envest.services.response;

import java.util.List;

import com.envest.services.components.util.account.AccountList;
import com.envest.services.components.util.account.Employment;
import com.envest.services.components.util.account.Insurance;
import com.envest.services.components.util.account.Tax;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnvestUserProfile extends EnvestResponse{


	private Info personnelinfo;
	private List<AccountList> assets;
	private List<AccountList> liabilities;
	private Insurance insurance;
	private EnvestResponse cashflowDataElements;
	private Tax tax;
	private Employment employment;	
	
	private boolean isHaveLoanAccount;
	private boolean isHaveCreditCard;
	private boolean isEmployed;
	
	
	public Info getPersonnelinfo() {
		return personnelinfo;
	}
	public void setPersonnelinfo(Info personnelinfo) {
		this.personnelinfo = personnelinfo;
	}
	public List<AccountList> getAssets() {
		return assets;
	}
	public void setAssets(List<AccountList> assets) {
		this.assets = assets;
	}
	public List<AccountList> getLiabilities() {
		return liabilities;
	}
	public void setLiabilities(List<AccountList> liabilities) {
		this.liabilities = liabilities;
	}
	public Insurance getInsurance() {
		return insurance;
	}
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}
	public EnvestResponse getCashflowDataElements() {
		return cashflowDataElements;
	}
	public void setCashflowDataElements(EnvestResponse cashflowDataElements) {
		this.cashflowDataElements = cashflowDataElements;
	}
	public Tax getTax() {
		return tax;
	}
	public void setTax(Tax tax) {
		this.tax = tax;
	}
	public Employment getEmployment() {
		return employment;
	}
	public void setEmployment(Employment employment) {
		this.employment = employment;
	}
	public boolean isHaveLoanAccount() {
		return isHaveLoanAccount;
	}
	public void setHaveLoanAccount(boolean isHaveLoadAccount) {
		this.isHaveLoanAccount = isHaveLoadAccount;
	}
	public boolean isHaveCreditCard() {
		return isHaveCreditCard;
	}
	public void setHaveCreditCard(boolean isHaveCreditCard) {
		this.isHaveCreditCard = isHaveCreditCard;
	}
	public boolean isEmployed() {
		return isEmployed;
	}
	public void setEmployed(boolean isEmployed) {
		this.isEmployed = isEmployed;
	}		
}
