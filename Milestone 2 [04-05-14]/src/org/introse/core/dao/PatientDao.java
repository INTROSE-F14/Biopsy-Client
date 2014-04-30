package org.introse.core.dao;

import java.util.List;

import org.introse.core.Patient;

public interface PatientDao 
{
	public int add(Patient patient);
	public void delete(Patient patient);
	public void update(Patient patient);
	public List<Patient> search(Patient patient, int start, int range);
	public Patient get(Patient patient);
	public List<Patient> getAll(int start, int range);
	public int getCount(Patient patient);
	public int getCount();
	public List<Patient> get(char start, char end, boolean isInRange);
	public List<Patient> get(char start, char end, char gender, boolean isInRange);
}
