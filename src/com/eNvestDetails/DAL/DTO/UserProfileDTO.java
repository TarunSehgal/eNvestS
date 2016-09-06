package com.eNvestDetails.DAL.DTO;
/*package com.eNvestDetails.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="envest_user_profile")

public class UserProfileDTO  {
	
	@Id @GeneratedValue
	@Column
	private Long userProfileKey;
		
	@Column
	private double utilityBills = 0.0;
	
	@Column
	private double creditBills = 0.0;
	
	@Column
	private String month = null;
	
	@Column
	private double salary = 0.0;
	
	@Column
	private double inflow = 0.0;
	
	@Column
	private double outflow =  0.0;
	
	@Column
	private double monthlyInterest = 0.0;
	
	@Column
	private double monthlyFee = 0.0;
	
	@Column
	private double loanPayment = 0.0;
	
	@Column
	private String employer = null;
	
	@Column
	private String accountId;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@Column
	private Long userKey;
	@Column
	private double otherInflow;
	
	@Column
	private double otherOutflow;

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
	
	public Long getUserKey() {
		return userKey;
	}
	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}

	public Long getUserProfileKey() {
		return userProfileKey;
	}
	public void setUserProfileKey(Long userProfileKey) {
		this.userProfileKey = userProfileKey;
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
*/