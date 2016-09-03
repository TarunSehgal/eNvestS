package com.eNvestDetails.util.Product;

import com.eNvestDetails.DAL.BankDTO;
import com.eNvestDetails.DAL.ProductDTO;
import com.eNvestDetails.DAL.UserProductDTO;

public class UserProductDTOtoProductConverter {
	
	public static Product getProductFromDTO(UserProductDTO productDTO, BankDTO bankDTO, ProductDTO productTemplate)
	{
		ProductType type = Enum.valueOf(ProductType.class, productTemplate.getProductName());
		switch(type)
		{
		case CertificateOfDeposit:
			return getCD(productDTO, bankDTO, productTemplate);
			case HighYieldAccount:
				return getHYA(productDTO, bankDTO, productTemplate);
			case MonthlyInvestmentPlan:
				return getMip(productDTO, bankDTO, productTemplate);
			case GoalSeek:
				return getGoalSeek(productDTO, bankDTO, productTemplate);
		default:
			break;
		}
		return null;
	}
	
	private static CDProduct getCD(UserProductDTO ProductDTO, BankDTO bankDTO, ProductDTO productTemplate)
	{
		CDProduct cdProduct = new CDProduct(bankDTO.getBankName(), ProductDTO.getInterest(),ProductDTO.getUserProductId(), ProductDTO.getProductId(), productTemplate.getMaturityYears());
				
		cdProduct.minimumAmount = productTemplate.getMinAmount();
		cdProduct.principle = ProductDTO.getPrinciple();
		cdProduct.valueAtMaturity = ProductDTO.getMaturityValue();
		cdProduct.interestRate = ProductDTO.getInterest();
		cdProduct.compoundingTenor = ProductDTO.getTenor();
		return cdProduct;		
	}
	
	
	
	private static MIProduct getMip(UserProductDTO ProductDTO, BankDTO bankDTO, ProductDTO productTemplate)
	{
		MIProduct mipProduct = new MIProduct(bankDTO.getBankName(), ProductDTO.getInterest(),ProductDTO.getUserProductId(), ProductDTO.getProductId());
			
		
		
		
		mipProduct.monthlyCashFlow = ProductDTO.getPrinciple();
		mipProduct.valueAtMaturity = ProductDTO.getMaturityValue();
		mipProduct.interestRate = ProductDTO.getInterest();
		mipProduct.compoundingTenor = ProductDTO.getTenor();
		
		
		mipProduct.maturityYears = productTemplate.getMaturityYears();
		mipProduct.minimumTenureYears = productTemplate.getMinTenure();
		mipProduct.maxTenureYears = productTemplate.getMaxTenure();
		mipProduct.preMatureClosureCharges = productTemplate.getPreMatureClosureCharges();
		mipProduct.emiDefaultCharges = productTemplate.getEmiDefaultCharge();

		return mipProduct;		
	}
	
	private static HighYieldProduct getHYA(UserProductDTO ProductDTO, BankDTO bankDTO, ProductDTO productTemplate)
	{
		HighYieldProduct hyaProduct = new HighYieldProduct(bankDTO.getBankName(), ProductDTO.getInterest(),ProductDTO.getUserProductId(), ProductDTO.getProductId());
				
		hyaProduct.principle = ProductDTO.getPrinciple();
		hyaProduct.interestRate = ProductDTO.getInterest();
		hyaProduct.compoundingTenor = ProductDTO.getTenor();
		return hyaProduct;		
	}
	
	private static GoalSeekProduct getGoalSeek(UserProductDTO ProductDTO, BankDTO bankDTO, ProductDTO productTemplate)
	{
		GoalSeekProduct goalSeekProduct = new GoalSeekProduct(bankDTO.getBankName(), ProductDTO.getInterest(),ProductDTO.getUserProductId(), ProductDTO.getProductId());
		
		goalSeekProduct.maturityYears = ProductDTO.getMaturityValue();
		goalSeekProduct.principle = ProductDTO.getPrinciple();
		goalSeekProduct.interestRate = ProductDTO.getInterest();
		goalSeekProduct.compoundingTenor = ProductDTO.getTenor();
		return goalSeekProduct;		
	}

}
