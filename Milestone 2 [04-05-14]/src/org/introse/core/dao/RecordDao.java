package org.introse.core.dao;

import java.util.List;

import org.introse.core.Record;


public interface RecordDao 
{
	public void add(Record record);
	public void delete(Record record);
	public void update(Record record);
	public List<Record> search(Record record);
	public Record get(Record record);
	public List<Record> getAll();
	public void assignReferenceNumber(Record record);
}