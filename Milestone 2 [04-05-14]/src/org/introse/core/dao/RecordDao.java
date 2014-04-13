package org.introse.core.dao;

import java.util.List;

import org.introse.core.Record;


public interface RecordDao 
{
	public int add(Record record);
	public void delete(Record record);
	public void delete(int patient);
	public void update(Record record);
	public List<Record> search(Record record, int start, int end);
	public Record get(Record record);
	public List<Record> getAll();
	public int getCount(Record record);
}