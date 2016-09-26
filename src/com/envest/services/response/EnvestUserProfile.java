package com.envest.services.response;

import java.util.ArrayList;

import com.envest.services.components.userprofile.DataElement;

public class EnvestUserProfile extends EnvestResponse {
	
	private AssetLiabilityAccount assets;
	
	private AssetLiabilityAccount liability;
	
	private ArrayList<DataElement> cashFlow;
	
	public AssetLiabilityAccount getAssets() {
		return assets;
	}

	public void setAssets(AssetLiabilityAccount assets) {
		this.assets = assets;
	}

	public AssetLiabilityAccount getLiability() {
		return liability;
	}

	public void setLiability(AssetLiabilityAccount liability) {
		this.liability = liability;
	}

	public ArrayList<DataElement> getCashFlow() {
		return cashFlow;
	}

	public void setCashFlow(ArrayList<DataElement> cashFlow) {
		this.cashFlow = cashFlow;
	}

	public final static class PersonalInfo{
		private String emailID;
		private String phone;
		private String isMarried;
		private String isHaveChilderen;
		private Address address;
		
		public String getEmailID() {
			return emailID;
		}
		public void setEmailID(String emailID) {
			this.emailID = emailID;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getIsMarried() {
			return isMarried;
		}
		public void setIsMarried(String isMarried) {
			this.isMarried = isMarried;
		}
		public String getIsHaveChilderen() {
			return isHaveChilderen;
		}
		public void setIsHaveChilderen(String isHaveChilderen) {
			this.isHaveChilderen = isHaveChilderen;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		
		
	}
	
}

