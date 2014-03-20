package org.introse.core;

public class Diagnosis 
{
	private int categoryID;
	private String value;
	private String referenceNumber;
	
	public Diagnosis(int categoryID, String value, String referenceNumber)
	{
		this.categoryID = categoryID;
		this.value = value;
		this.referenceNumber = referenceNumber;
	}
	
	public int getCategory()
	{
		return categoryID;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public String getReferenceNumber()
	{
		return referenceNumber;
	}
}
