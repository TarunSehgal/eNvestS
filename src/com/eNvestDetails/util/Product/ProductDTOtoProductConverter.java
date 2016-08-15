package com.eNvestDetails.util.Product;

import com.eNvestDetails.dto.BankDTO;
import com.eNvestDetails.dto.UserProductDTO;
import com.eNvestDetails.dto.ProductDTO;

public class ProductDTOtoProductConverter {
	
	public static Product getProductFromDTO(ProductDTO productDTO, BankDTO bankDTO)
	{
		ProductType type = Enum.valueOf(ProductType.class, productDTO.getProductName());
		switch(type)
		{
		case CertificateOfDeposit:
			return getCD(productDTO, bankDTO);
			case HighYieldAccount:
				return getHYA(productDTO, bankDTO);
			case MonthlyInvestmentPlan:
				return getMip(productDTO, bankDTO);
			case GoalSeek:
				return getGoalSeek(productDTO, bankDTO);
		default:
			break;
		}
		return null;
	}
	
	private static CDProduct getCD(ProductDTO productDTO, BankDTO bankDTO)
	{
		CDProduct cdProduct = new CDProduct(bankDTO.getBankName(), productDTO.getProductInterest(),0, productDTO.getProductId(), productDTO.getMaturityYears());
				
		cdProduct.minimumAmount = productDTO.getMinAmount();
		
		return cdProduct;		
	}
	
	
	
	private static MIProduct getMip(ProductDTO productDTO, BankDTO bankDTO)
	{
		MIProduct mipProduct = new MIProduct(bankDTO.getBankName(), productDTO.getProductInterest(),0, productDTO.getProductId());		
		mipProduct.noOfYears = productDTO.getMaturityYears();
		mipProduct.minimumTenureYears = productDTO.getMinTenure();
		mipProduct.maxTenureYears = productDTO.getMaxTenure();
		mipProduct.preMatureClosureCharges = productDTO.getPreMatureClosureCharges();
		mipProduct.emiDefaultCharges = productDTO.getEmiDefaultCharge();

		return mipProduct;		
	}
	
	private static HighYieldProduct getHYA(ProductDTO productDTO, BankDTO bankDTO)
	{
		HighYieldProduct hyaProduct = new HighYieldProduct(bankDTO.getBankName(), productDTO.getProductInterest(),0, productDTO.getProductId());
		return hyaProduct;		
	}
	
	private static GoalSeekProduct getGoalSeek(ProductDTO productDTO, BankDTO bankDTO)
	{
		GoalSeekProduct goalSeekProduct = new GoalSeekProduct(bankDTO.getBankName(), productDTO.getProductInterest(),0, productDTO.getProductId());
		goalSeekProduct.noOfYears = productDTO.getMaturityYears();
		return goalSeekProduct;		
	}

}
