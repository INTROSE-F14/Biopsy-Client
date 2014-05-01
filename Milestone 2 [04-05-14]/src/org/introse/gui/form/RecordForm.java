package org.introse.gui.form;

import java.awt.CardLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.RecordOverview;

public abstract class RecordForm extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected RecordOverview overviewPanel;
	protected JPanel cardPanel;
	public static final int HISTOPATHOLOGY_PAGES = 3;
	public static final int CYTOLOGY_PAGES = 3;
	public static final int GYNECOLOGY_PAGES = 3;
	
	public RecordForm(LayoutManager manager)
	{
		super(manager);
	}

	public abstract boolean areFieldsValid();
	public abstract Record getRecord();
	public abstract void setFields(Record record, Patient patient);
	public abstract void addListener(CustomListener listener);
	public abstract void setRecordEditable(boolean isEditable);
	
	public Patient getPatient()
	{
		return overviewPanel.getPatient();
	}
	
	public void setPatientEditable(boolean isEditable)
	{
		overviewPanel.setPatientEditable(isEditable);
	}
	
	public void setPatient(Patient patient)
	{
		overviewPanel.setPatientFields(patient);
	}
	

	public void setLoadPatientEnabled(boolean isEnabled)
	{
		overviewPanel.getPatientForm().setClearExisting(isEnabled);
		overviewPanel.getPatientForm().setLoadExisting(isEnabled);
	}
	
	public PatientForm getPatientForm()
	{
		return overviewPanel.getPatientForm();
	}
	
	public void nextPage()
	{
		CardLayout cl = (CardLayout)cardPanel.getLayout();
		cl.next(cardPanel);
	}
	
	public void previousPage()
	{
		CardLayout cl = (CardLayout)cardPanel.getLayout();
		cl.previous(cardPanel);
	}
}
