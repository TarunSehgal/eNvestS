package com.envest.services.components.userprofile;

import java.util.List;

import org.apache.log4j.Logger;

import com.envest.services.components.EnvestConstants;
import com.envest.services.components.EnvestMessageFactory;
import com.envest.services.components.exceptions.EnvestException;
import com.envest.services.components.userprofile.cashFlowDataElement.DataElement;
import com.envest.services.components.util.account.AccountList;
import com.envest.services.components.util.account.AccountType;
import com.envest.services.response.CashFlowAnalysisResponse;
import com.envest.services.response.EnvestUserProfile;

public class UserProfileBuildProfilFlag extends UserProfileBuilder {
	
	private static Logger log = Logger.getLogger(UserProfileBuildProfilFlag.class.getName());

	@Override
	public EnvestUserProfile buildProfile(Object obj
			,EnvestUserProfile userProfile) throws EnvestException{
		
		try{
			List<AccountList> list = userProfile.getLiabilities();
			
			for(AccountList acc : list){
				if(AccountType.creditcard.equals(acc.getAccountType())){
					userProfile.setHaveCreditCard(true);
				}
				if(AccountType.mortage.equals(acc.getAccountType())
						|| AccountType.personnelLoan.equals(acc.getAccountType())
						|| AccountType.studentLoan.equals(acc.getAccountType())){
					userProfile.setHaveLoanAccount(true);
				}
			}
			
			CashFlowAnalysisResponse res = (CashFlowAnalysisResponse)
					userProfile.getCashflowDataElements();
			for(DataElement element : res.getProfile()){
				if(EnvestConstants.KEY_CAHSFLOW_SALARY_DATA_ELEMENT.equals
						(element.getId())){
					userProfile.setEmployed(true);
				}
			}
						
		}catch (Exception e){
			log.error("Error occured while creating UserProfileBuildProfilFlag: "+ e);
			throw new EnvestException(new EnvestMessageFactory().
					getServerErrorMessage(e.getMessage())) ;
		}		
		return userProfile;		
	}
}
