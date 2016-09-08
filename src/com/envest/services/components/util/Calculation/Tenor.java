package com.envest.services.components.util.Calculation;

public class Tenor {
	public Tenor(int numericValue, TenorType tenorType)
	{
		this.numericValue = numericValue;
		this.tenorType = tenorType;
	}
	public int numericValue;
	public TenorType tenorType;
}
