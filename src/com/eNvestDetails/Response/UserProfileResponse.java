package com.eNvestDetails.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserProfileResponse extends EnvestResponse {

	private double utilityBills = 0.0;
	private double creditBills = 0.0;
	private String month = null;
	private double salary = 0.0;
	private double inflow = 0.0;
	private double outflow =  0.0;
	private double monthlyInterest = 0.0;
	private double monthlyFee = 0.0;
	private double loanPayment = 0.0;
	private String employer = null;
	private double otherInflow;
	private double otherOutflow;
	
	private List<UserProfileResponse> profileData;
	

	public double getOtherInflow() {
		return otherInflow;
	}
	public void setOtherInflow(double otherInflow) {
		this.otherInflow = otherInflow;
	}
	public double getOtherOutflow() {
		return otherOutflow;
	}
	public void setOtherOutflow(double otherOutflow) {
		this.otherOutflow = otherOutflow;
	}
	public List<UserProfileResponse> getProfileData() {
		return profileData;
	}
	public void setProfileData(List<UserProfileResponse> profileData) {
		this.profileData = profileData;
	}
	private String accountId;
	

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	
   	public double getLoanPayment() {
		return loanPayment;
	}
	public void setLoanPayment(double loanPayment) {
		this.loanPayment = loanPayment;
	}
	public double getUtilityBills() {
		return utilityBills;
	}
	public void setUtilityBills(double utilityBills) {
		this.utilityBills = utilityBills;
	}
	public double getCreditBills() {
		return creditBills;
	}
	public void setCreditBills(double creditBills) {
		this.creditBills = creditBills;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getInflow() {
		return inflow;
	}
	public void setInflow(double inflow) {
		this.inflow = inflow;
	}
	public double getOutflow() {
		return outflow;
	}
	public void setOutflow(double outflow) {
		this.outflow = outflow;
	}
	public double getMonthlyInterest() {
		return monthlyInterest;
	}
	public void setMonthlyInterest(double monthlyInterest) {
		this.monthlyInterest = monthlyInterest;
	}
	public double getMonthlyFee() {
		return monthlyFee;
	}
	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	
}