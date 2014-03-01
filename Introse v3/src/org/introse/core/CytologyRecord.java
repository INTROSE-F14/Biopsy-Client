package org.introse.core;


import java.util.HashMap;

public class CytologyRecord extends Record
{
	
	public CytologyRecord()
	{
		super();
	}
	public CytologyRecord(Object refNumber)
	{
		super(refNumber);
	}
	
	public CytologyRecord(HashMap<String, Object> attributes) {
		super(attributes);
	}
}