package org.introse.gui.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.introse.Constants;
import org.introse.Constants.CategoriesConstants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomDocument;
import org.introse.core.Diagnosis;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.RecordOverview;
import org.introse.gui.window.LoginWindow;

public class HistopathologyForm extends JPanel implements RecordForm
{
	private JPanel findingsPanel;
	private JTextArea diagnosisValue, remarksValue, grossDescValue, microNoteValue;
	private JScrollPane diagnosisScroller, remarksScroller, grossDescScroller, 
	microNoteScroller;
	private JLabel diagnosisLabel, remarksLabel, grossDescLabel, microNoteLabel;
	private RecordOverview overviewPanel;
	
	public HistopathologyForm()
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		initializeComponents();
		layoutComponents();
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		int y = 0;
		
		y = 0;
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = y++;
		add(overviewPanel, c);
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(diagnosisLabel, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(remarksLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,30);
		findingsPanel.add(diagnosisScroller, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		findingsPanel.add(remarksScroller, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(grossDescLabel, c);
		c.gridy = y++;
		c.gridx = 1;
		findingsPanel.add(microNoteLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,30);
		findingsPanel.add(grossDescScroller, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		findingsPanel.add(microNoteScroller, c);
		
		c.gridx = 0;
		c.gridy = 1;
		add(findingsPanel, c);
	}
	
	private void initializeComponents()
	{	
		overviewPanel = new RecordOverview(RecordConstants.HISTOPATHOLOGY_RECORD);
		findingsPanel = new JPanel(new GridBagLayout());
		
		overviewPanel.setBackground(Color.white);
		findingsPanel.setBackground(Color.white);
		
		diagnosisLabel = new JLabel("Diagnosis");
		remarksLabel = new JLabel("Remarks");
		grossDescLabel = new JLabel("Gross Description");
		microNoteLabel = new JLabel("Microscopic Notes");
		diagnosisValue= new JTextArea(5,32);
		grossDescValue = new JTextArea(5,32);
		microNoteValue = new JTextArea(5,32);
		remarksValue= new JTextArea(5,32);
		
		diagnosisScroller = new JScrollPane(diagnosisValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		remarksScroller = new JScrollPane(remarksValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		grossDescScroller = new JScrollPane(grossDescValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		microNoteScroller = new JScrollPane(microNoteValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		diagnosisScroller.setPreferredSize(new Dimension((int)(Preferences.getScreenWidth() * 0.32), 
				(int)(Preferences.getScreenHeight() * 0.15)));
		remarksScroller.setPreferredSize(diagnosisScroller.getPreferredSize());
		grossDescScroller.setPreferredSize(diagnosisScroller.getPreferredSize());
		microNoteScroller.setPreferredSize(diagnosisScroller.getPreferredSize());
		
		diagnosisValue.setFont(LoginWindow.PRIMARY_FONT.deriveFont(Constants.StyleConstants.MENU));
		remarksValue.setFont(diagnosisValue.getFont());
		grossDescValue.setFont(diagnosisValue.getFont());
		microNoteValue.setFont(diagnosisValue.getFont());
		
		remarksValue.setDocument(new CustomDocument(RecordConstants.REMARKS_LENGTH));
		grossDescValue.setDocument(new CustomDocument(RecordConstants.GROSS_LENGTH));
		microNoteValue.setDocument(new CustomDocument(RecordConstants.MICRO_LENGTH));
		diagnosisValue.setDocument(new CustomDocument(RecordConstants.DIAGNOSIS_LENGTH));
	}
	
	@Override
	public void setFields(Record record, Patient patient)
	{	
		overviewPanel.setRecordFields(record);
		overviewPanel.setPatientFields(patient);
		
		List<Diagnosis> diagnosis = (List<Diagnosis>)record.getAttribute(RecordTable.DIAGNOSIS);
		if(diagnosis != null && diagnosis.size() > 0)
			diagnosisValue.setText(diagnosis.get(0).getValue());
		
		String remarks = (String)record.getAttribute(RecordTable.REMARKS);
		if(remarks != null)
			remarksValue.setText(remarks);
		
		String microNote = (String)record.getAttribute(RecordTable.MICRO_NOTE);
		if(microNote != null)
			microNoteValue.setText(microNote);
		
		String grossdesc = (String)record.getAttribute(RecordTable.GROSS_DESC);
		if(grossdesc != null)
			grossDescValue.setText(grossdesc);
	}
	
	public boolean areFieldsValid()
	{
		boolean isValid = true;
		if(!overviewPanel.areFieldsValid())
			isValid = false;
		return isValid;
	}

	@Override
	public Record getRecord() 
	{
		Record record = (HistopathologyRecord)overviewPanel.getRecord();
		if(diagnosisValue.getText().length() > 0)
		{
			Diagnosis diagnosis = new Diagnosis(CategoriesConstants.OTHERS, diagnosisValue.getText());
			List<Diagnosis> newDiagnosis = new Vector<Diagnosis>();
			newDiagnosis.add(diagnosis);
			record.putAttribute(RecordTable.DIAGNOSIS, newDiagnosis);
		}
		record.putAttribute(RecordTable.SPEC_TYPE, TitleConstants.OTHERS);
		if(remarksValue.getText().length() > 0)
			record.putAttribute(RecordTable.REMARKS, remarksValue.getText());
		if(grossDescValue.getText().length() > 0)
			record.putAttribute(RecordTable.GROSS_DESC, grossDescValue.getText());
		if(microNoteValue.getText().length() > 0)
			record.putAttribute(RecordTable.MICRO_NOTE, microNoteValue.getText());
		return record;
	}

	@Override
	public Patient getPatient() 
	{
		return overviewPanel.getPatient();
	}

	@Override
	public void setPatient(Patient patient) 
	{
		overviewPanel.setPatientFields(patient);
	}

	@Override
	public void addListener(CustomListener listener) 
	{
		overviewPanel.addListener(listener);
	}

	@Override
	public void setRecordEditable(boolean isEditable) 
	{
		overviewPanel.setRecordEditable(isEditable);
		diagnosisValue.setEditable(isEditable);
		remarksValue.setEditable(isEditable);
		grossDescValue.setEditable(isEditable);
		microNoteValue.setEditable(isEditable);
	}

	@Override
	public void setPatientEditable(boolean isEditable) 
	{
		overviewPanel.setPatientEditable(isEditable);
	} 

	@Override
	public void setLoadPatientEnabled(boolean isEnabled) 
	{
		overviewPanel.getPatientForm().setLoadExisting(isEnabled);
	}
}