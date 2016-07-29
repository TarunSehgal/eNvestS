package com.eNvestDetails.Response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccountDetail extends EnvestResponse {

    private String accountId;
    private String item;
    private String userId;
    
    private Balance balance;
    private AccountMeta meta;
    private AccountNumbers numbers;
    
    private String institutionType;
    private String type;
    private String subtype;
    
    private double yield;
    
	private boolean isPrimary = false;
	private boolean isSalaryAccount = false;
	
	private double totalInterestIncurred = 0.0;
	private double totalFeeIncurred = 0.0;
	
	private List<AccountProfile> accProfile = null;
        
    public List<AccountProfile> getAccProfile() {
		return accProfile;
	}
	public void setAccProfile(List<AccountProfile> accProfile) {
		this.accProfile = accProfile;
	}
	public boolean isPrimary() {
		return isPrimary;
	}
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	public boolean isSalaryAccount() {
		return isSalaryAccount;
	}
	public void setSalaryAccount(boolean isSalaryAccount) {
		this.isSalaryAccount = isSalaryAccount;
	}
	
	public double getTotalInterestIncurred() {
		return totalInterestIncurred;
	}
	public void setTotalInterestIncurred(double totalInterestIncurred) {
		this.totalInterestIncurred = totalInterestIncurred;
	}
	public double getTotalFeeIncurred() {
		return totalFeeIncurred;
	}
	public void setTotalFeeIncurred(double totalFeeIncurred) {
		this.totalFeeIncurred = totalFeeIncurred;
	}

  
    public double getYield() {
		return yield;
	}
	public void setYield(double yield) {
		this.yield = yield;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@JsonProperty("_item")
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    @JsonProperty("_user")
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Balance getBalance() {
        return balance;
    }
    public void setBalance(Balance balance) {
        this.balance = balance;
    }
    public AccountMeta getMeta() {
        return meta;
    }
    public void setMeta(AccountMeta meta) {
        this.meta = meta;
    }
    public AccountNumbers getNumbers() {
        return numbers;
    }
    public void setNumbers(AccountNumbers numbers) {
        this.numbers = numbers;
    }
    @JsonProperty("institution_type")
    public String getInstitutionType() {
        return institutionType;
    }
    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSubtype() {
		return subtype;
	}   
    public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class Balance {
        private Double available;
        private Double current;

        public Double getAvailable() {
            return available;
        }
        public void setAvailable(Double available) {
            this.available = available;
        }
        public Double getCurrent() {
            return current;
        }
        public void setCurrent(Double current) {
            this.current = current;
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class AccountMeta {
        private Double limit;
        private String name;
        private String number;

        public Double getLimit() {
            return limit;
        }
        public void setLimit(Double limit) {
            this.limit = limit;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getNumber() {
            return number;
        }
        public void setNumber(String number) {
            this.number = number;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class AccountNumbers {
        private String routing;
        private String account;
        private String wireRouting;

        public String getRouting() {
            return routing;
        }

        public void setRouting(String routing) {
            this.routing = routing;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
        
        public String getWireRouting() {
			return wireRouting;
		}
        
        public void setWireRouting(String wireRouting) {
			this.wireRouting = wireRouting;
		}
    }
   
    
    @JsonIgnoreProperties(ignoreUnknown=true)
    public final static class AccountProfile {
    	
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
    
}
