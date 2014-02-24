package org.introse.core.dao;

import java.util.Date;
import java.util.List;

import org.introse.core.network.Client;

public abstract class Dao
{
	protected List<Object> data;
	protected Client client;
	protected static Date dataVersion;
	
	public Dao(Client client)
	{
		this.client = client;
		updateList();
	}
	
	public List<Object> getAll()
	{
		if(!client.isUpdateAvailable(dataVersion))
			return data;
		updateList();
		return data;
	}
	
	public abstract Object get(Object specificObject);
	public abstract void delete(Object specificObject);
	public abstract void delete(List<Object> toBeDeleted);
	public abstract void updateList();
	public abstract List<Object> search(Object criteria);
}