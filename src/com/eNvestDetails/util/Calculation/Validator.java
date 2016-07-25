package com.eNvestDetails.util.Calculation;

import java.util.regex.Pattern;

public class Validator {
	
	public static Tenor validateCoumpoundingTenor(String tenor) throws Exception {
		if (!Pattern.matches("\\d+[mMdDyY]", tenor)) {
			throw new Exception("Tenor not valid.");
		}
		
		int tenorLen = tenor.length();
		int tenorVal = Integer.parseInt(tenor.substring(0, tenorLen -1));
		if(tenorVal<1)
		{
			throw new Exception("Tenor value can not be lesser than 1");
		}
		String tenorType = tenor.substring(tenorLen-1);
		TenorType type = null;
		switch(tenorType.toUpperCase())
		{
		case "M":
			type = TenorType.Monthly;
			break;
		case "Y":
			type = TenorType.Annual;
			break;
		case "D":
			type = TenorType.Daily;
			break;
		}
		
		if(type == null)
		{
			throw new Exception("Tenor type not valid");
		}
		return new Tenor(tenorVal, type);
	}
}
