package org.introse.gui.form;

import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.RecordOverview;

public interface RecordForm 
{
	public Record getRecord();
	public Patient getPatient();
	public void setRecordEditable(boolean isEditable);
	public void setPatientEditable(boolean isEditable);
	public boolean areFieldsValid();
	public void setPatient(Patient patient);
	public void setFields(Record record, Patient patient);
	public void addListener(CustomListener listener);
	public void setLoadPatientEnabled(boolean isEnabled);
}
