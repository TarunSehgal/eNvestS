package com.envest.services.response;

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
}
