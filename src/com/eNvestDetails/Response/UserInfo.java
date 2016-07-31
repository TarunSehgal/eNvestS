package com.eNvestDetails.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
public class UserInfo extends EnvestResponse {

	private List<AccountDetail> accounts;
	private Info info;
	
	private List<Summary> summary; 
	 
	private List<TransactionDetail> transaction = null;
	
	private DashBoardSummary dashBoardSummary;
	    
	public DashBoardSummary getDashBoardSummary() {
		return dashBoardSummary;
	}

	public void setDashBoardSummary(DashBoardSummary dashBoardSummary) {
		this.dashBoardSummary = dashBoardSummary;
	}

	public List<Summary> getSummary() {
		return summary;
	}

	public void setSummary(List<Summary> summary) {
		this.summary = summary;
	}
	
	public List<AccountDetail> getAccounts() {
		return accounts;
	}
	
	

	public List<TransactionDetail> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<TransactionDetail> transaction) {
		this.transaction = transaction;
	}

	public void setAccounts(List<AccountDetail> accounts) {
		this.accounts = accounts;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
	
	@Override
	public String getResponseType() {
		return "userInfo";
	}

	@JsonIgnoreProperties(ignoreUnknown=true)
	public static final class Info {
		private List<String> names;
		private List<Email> emails;
		private List<PhoneNumber> phoneNumbers;
		private List<Address> addresses;
		private String userId;
	

		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public List<String> getNames() {
			return names;
		}
		public void setNames(List<String> names) {
			this.names = names;
		}
		public List<Email> getEmails() {
			return emails;
		}
		public void setEmails(List<Email> emails) {
			this.emails = emails;
		}
		@JsonProperty("phone_numbers")
		public List<PhoneNumber> getPhoneNumbers() {
			return phoneNumbers;
		}
		public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
			this.phoneNumbers = phoneNumbers;
		}
		public List<Address> getAddresses() {
			return addresses;
		}
		public void setAddresses(List<Address> addresses) {
			this.addresses = addresses;
		}
	} 
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static final class Email {
		private String email;
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		private String type;
		

		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}		
	}
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static final class PhoneNumber {
		private String number;
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}

		private String type;
		private Boolean primary;
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Boolean isPrimary() {
			return primary;
		}
		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}		
	}
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static final class Address {
		private Boolean primary;
		

		public Boolean getPrimary() {
			return primary;
		}
		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}
		private String street;
		private String city;
		private String state;
		private String zip;
		
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}			
	}
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static final class AddressDetails {
		private String street;
		private String city;
		private String state;
		private String zip;
		
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}		
	}
	
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class Summary {
    	private Double inflow;
    	private Double outflow;
    	private String accountNumber; 
    	private double totalInterest;
    	private double totalBankFee;
    	public double getTotalInterest() {
			return totalInterest;
		}
		public void setTotalInterest(double totalInterest) {
			this.totalInterest = totalInterest;
		}
		public double getTotalBankFee() {
			return totalBankFee;
		}
		public void setTotalBankFee(double totalBankFee) {
			this.totalBankFee = totalBankFee;
		}
		
		public String getAccountNumber() {
			return accountNumber;
		}
		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}
		public Double getInflow() {
			return inflow;
		}
		public void setInflow(Double inflow) {
			this.inflow = inflow;
		}
		public Double getOutflow() {
			return outflow;
		}
		public void setOutflow(Double outflow) {
			this.outflow = outflow;
		}
    	
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class DashBoardSummary {
    	private double totalInflow;
    	private double totalOutflow;
    	private double totalInterest;
    	private double totalBankFee;
    	private List<BankBalance> bankBalances;
    	
    	public List<BankBalance> getBankBalances() {
			return bankBalances;
		}
		public void setBankBalances(List<BankBalance> bankBalances) {
			this.bankBalances = bankBalances;
		}
		public double getTotalInflow() {
			return totalInflow;
		}
		public void setTotalInflow(double totalInflow) {
			this.totalInflow = totalInflow;
		}
		public double getTotalOutflow() {
			return totalOutflow;
		}
		public void setTotalOutflow(double totalOutflow) {
			this.totalOutflow = totalOutflow;
		}
		public double getTotalInterest() {
			return totalInterest;
		}
		public void setTotalInterest(double totalInterest) {
			this.totalInterest = totalInterest;
		}
		public double getTotalBankFee() {
			return totalBankFee;
		}
		public void setTotalBankFee(double totalBankFee) {
			this.totalBankFee = totalBankFee;
		}	
    	
    }
    
    public final static class BankBalance{
      	private String bankName;
    	private double currentBalance;
    	private double availableBalance;
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public double getCurrentBalance() {
			return currentBalance;
		}
		public void setCurrentBalance(double currentBalance) {
			this.currentBalance = currentBalance;
		}
		public double getAvailableBalance() {
			return availableBalance;
		}
		public void setAvailableBalance(double availableBalance) {
			this.availableBalance = availableBalance;
		}
    }
	
}
