package com.envest.services.components.util.account;

public class Employment {
String employer;
int yearsOfService;
int salaryFrequency;
double projectedIncomeAfterTax;

public Employment(String employer, int yearsOfService, int salaryFrequency, double projectedIncomeAfterTax)
{
	this.employer = employer;
	this.yearsOfService = yearsOfService;
	this.salaryFrequency = salaryFrequency;
	this.projectedIncomeAfterTax = projectedIncomeAfterTax;
}
}
