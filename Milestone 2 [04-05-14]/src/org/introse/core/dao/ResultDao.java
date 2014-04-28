package org.introse.core.dao;

import java.util.List;

import org.introse.core.Result;
import org.introse.core.Record;

public interface ResultDao 
{
	public List<Result> getDiagnosis(Record record);
	public void add(Result diagnosis);
	public List<Result> getAll();
	public Result get(Result diagnosis);
	public void delete(Record record);
}
