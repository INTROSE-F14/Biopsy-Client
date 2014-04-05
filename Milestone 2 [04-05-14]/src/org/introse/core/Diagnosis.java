package org.introse.core;

public class Diagnosis 
{
	private int categoryID;
	private String value;
	private String referenceNumber;
	private char recordType;
	private int recordYear;
	private int recordNumber;
	
	public Diagnosis(int categoryID, String value, char recordType, int recordYear, int recordNumber)
	{
		this.categoryID = categoryID;
		this.value = value;
		this.recordType = recordType;
		this.recordYear = recordYear;
		this.recordNumber = recordNumber;
	}
	

	public Diagnosis(int categoryID, String value)
	{
		this.categoryID = categoryID;
		this.value = value;
	}
	
	public int getCategory()
	{
		return categoryID;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public char getRecordType()
	{
		return recordType;
	}
	
	public int getRecordYear()
	{
		return recordYear;
	}
	
	public int getRecordNumber()
	{
		return recordNumber;
	}
	
	public void setReferenceNumber(char recordType, int recordYear, int recordNumber)
	{
		this.recordType = recordType;
		this.recordYear = recordYear;
		this.recordNumber = recordNumber;
	}
}