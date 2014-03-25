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
import org.introse.core.Diagnosis;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.PageViewer;
import org.introse.gui.panel.RecordOverview;
import org.introse.gui.window.MainMenu;

public class HistopathologyForm extends JPanel implements RecordForm
{
	private PageViewer pv;
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
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(diagnosisLabel, c);
		c.gridy = y++;
		c.gridx = 1;
		c.insets = new Insets(0,0,0,0);
		findingsPanel.add(remarksLabel, c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = y;
		c.insets = new Insets(0,0,10,10);
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
		c.insets = new Insets(0,0,10,10);
		findingsPanel.add(grossDescScroller, c);
		c.gridx = 1;
		c.gridy = y++;
		c.insets = new Insets(0,0,10,0);
		findingsPanel.add(microNoteScroller, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(pv, c);
	}
	
	private void initializeComponents()
	{	
		overviewPanel = new RecordOverview(RecordConstants.HISTOPATHOLOGY_RECORD);
		findingsPanel = new JPanel(new GridBagLayout());
		
		overviewPanel.setBackground(Color.white);
		findingsPanel.setBackground(Color.white);
		List<JPanel> pages = new Vector<JPanel>();
		List<String> titles = new Vector<String>();
		pages.add(overviewPanel);
		pages.add(findingsPanel);
		titles.add(TitleConstants.RECORD_OVERVIEW);
		titles.add(TitleConstants.RESULTS);
		pv = new PageViewer(pages, titles, 0);
		
		diagnosisLabel = new JLabel("Diagnosis");
		remarksLabel = new JLabel("Remarks");
		grossDescLabel = new JLabel("Gross Description");
		microNoteLabel = new JLabel("Microscopic Notes");
		diagnosisValue= new JTextArea(5,40);
		grossDescValue = new JTextArea(5,40);
		microNoteValue = new JTextArea(5,40);
		remarksValue= new JTextArea(5,40);
		
		diagnosisScroller = new JScrollPane(diagnosisValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		remarksScroller = new JScrollPane(remarksValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		grossDescScroller = new JScrollPane(grossDescValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		microNoteScroller = new JScrollPane(microNoteValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		diagnosisValue.setFont(MainMenu.PRIMARY_FONT.deriveFont(Constants.StyleConstants.MENU));
		remarksValue.setFont(diagnosisValue.getFont());
		grossDescValue.setFont(diagnosisValue.getFont());
		microNoteValue.setFont(diagnosisValue.getFont());
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
	}
	
	public boolean areFieldsValid()
	{
		if(!overviewPanel.areFieldsValid())
			return false;
		if(!(diagnosisValue.getText().length() > 0) ||
				diagnosisValue.getText().length() > RecordConstants.DIAGNOSIS_LENGTH)
			return false;
		if(!(remarksValue.getText().length() > 0) ||
				remarksValue.getText().length() > RecordConstants.REMARKS_LENGTH)
			return false;
		return true;
	}

	@Override
	public Record getRecord() 
	{
		Record record = (HistopathologyRecord)overviewPanel.getRecord();
		Diagnosis diagnosis = new Diagnosis(CategoriesConstants.OTHERS, diagnosisValue.getText());
		List<Diagnosis> newDiagnosis = new Vector<Diagnosis>();
		newDiagnosis.add(diagnosis);
		record.putAttribute(RecordTable.DIAGNOSIS, newDiagnosis);
		record.putAttribute(RecordTable.SPEC_TYPE, TitleConstants.OTHERS);
		record.putAttribute(RecordTable.REMARKS, remarksValue.getText());
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