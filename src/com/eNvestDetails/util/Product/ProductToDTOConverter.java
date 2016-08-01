package com.eNvestDetails.util.Product;

import java.util.Date;

import javax.persistence.Column;

import com.eNvestDetails.dto.UserProductDTO;

public class ProductToDTOConverter {

	public static UserProductDTO convertProductToDTO(Product product, String userId)
	{
		UserProductDTO productDTO = new UserProductDTO();
		
		productDTO.setUserId(userId);
		productDTO.setProductId(product.productId);
		productDTO.setInterest(product.interestRate);

		switch(product.productType)
		{
		case CertificateOfDeposit:
			setCDDetails(productDTO, (CDProduct) product);
			break;
			case HighYieldAccount:
				setHYADetails(productDTO, (HighYieldProduct) product);
				break;
			case MonthlyInvestmentPlan:
				setMIPDetails(productDTO, (MIProduct) product);
				break;
			case GoalSeek:
				setGoalSeekDetails(productDTO, (GoalSeekProduct) product);
				break;
		default:
			break;
		}
		
		return productDTO;		
	}
	
	private static void setCDDetails(UserProductDTO productDTO, CDProduct product)
	{
		productDTO.setPrinciple(product.principle);
		productDTO.setTenor(product.compoundingTenor);
		productDTO.setMaturityValue(product.valueAtMaturity);
	}
	
	private static void setHYADetails(UserProductDTO productDTO, HighYieldProduct product)
	{
		productDTO.setPrinciple(product.principle);
		productDTO.setTenor(product.compoundingTenor);
	}
	
	private static void setGoalSeekDetails(UserProductDTO productDTO, GoalSeekProduct product)
	{
		productDTO.setPrinciple(product.principle);
		productDTO.setTenor(product.compoundingTenor);		
	}
	
	private static void setMIPDetails(UserProductDTO productDTO, MIProduct product)
	{
		productDTO.setPrinciple(product.monthlyCashFlow);
		productDTO.setTenor(product.compoundingTenor);
		productDTO.setMaturityValue(product.valueAtMaturity);
	}
}
