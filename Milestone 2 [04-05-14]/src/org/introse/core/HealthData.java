package org.introse.core;

import java.util.HashMap;

public class HealthData
{
	protected HashMap<String, Object> attributes;
	
	public HealthData()
	{
		super();
		attributes = new HashMap<>();
	}

	public Object getAttribute(String key)
	{
		return attributes.get(key);
	}
		
	public void putAttribute(String key, Object newValue)
	{
		if(attributes.containsKey(key))
		attributes.remove(attributes.get(key));
		attributes.put(key, newValue);
	}
}
