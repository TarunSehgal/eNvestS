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
	private double interest = 0.0;
	private double fee = 0.0;

	private double loanPayment = 0.0;
	private String employer = null;
	private double otherInflow;
	private double otherOutflow;
	private double expense30Days;
	private double expense90Days;
	private double income30Days;
	private double income90Days;
	
	private List<UserProfileResponse> profileData;
	
	public double getExpense30Days() {
		return expense30Days;
	}
	public void setExpense30Days(double expense30Days) {
		this.expense30Days = expense30Days;
	}
	public double getExpense90Days() {
		return expense90Days;
	}
	public void setExpense90Days(double expense90Days) {
		this.expense90Days = expense90Days;
	}
	public double getIncome30Days() {
		return income30Days;
	}
	public void setIncome30Days(double income30Days) {
		this.income30Days = income30Days;
	}
	public double getIncome90Days() {
		return income90Days;
	}
	public void setIncome90Days(double income90Days) {
		this.income90Days = income90Days;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

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
	
}