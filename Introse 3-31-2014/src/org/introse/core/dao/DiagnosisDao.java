package org.introse.core.dao;

import java.util.List;

import org.introse.core.Diagnosis;
import org.introse.core.Record;

public interface DiagnosisDao 
{
	public List<Diagnosis> getDiagnosis(Record record);
	public void add(Diagnosis diagnosis);
	public List<Diagnosis> getAll();
	public Diagnosis get(Diagnosis diagnosis);
	public void update(Diagnosis diagnosis);
}
