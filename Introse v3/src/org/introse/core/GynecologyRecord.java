package org.introse.core;


import java.util.HashMap;

public class GynecologyRecord extends Record
{

	public GynecologyRecord()
	{
		super();
	}
	public GynecologyRecord(Object refNumber) 
	{
		super(refNumber);
	}
	
	public GynecologyRecord(HashMap<String, Object> attributes)
	{	
		super(attributes);
	}
}