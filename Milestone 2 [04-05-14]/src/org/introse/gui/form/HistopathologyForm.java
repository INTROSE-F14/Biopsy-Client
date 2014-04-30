package org.introse.gui.form;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.introse.Constants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.ResultCategoriesConstants;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomDocument;
import org.introse.core.Result;
import org.introse.core.Patient;
import org.introse.core.Preferences;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.panel.RecordOverview;
import org.introse.gui.window.LoginWindow;

public class HistopathologyForm extends JPanel implements RecordForm, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel findings1Panel, findings2Panel, cardPanel, bottomPanel;
	private JTextArea diagnosisValue, remarksValue, grossDescValue, microNoteValue;
	private JScrollPane diagnosisScroller, remarksScroller, grossDescScroller, 
	microNoteScroller;
	private JLabel diagnosisLabel, remarksLabel, grossDescLabel, microNoteLabel, pageLabel,
	findings1Label, findings2Label;
	private RecordOverview overviewPanel;
	private JButton nextButton, previousButton;
	private int page;
	
	public HistopathologyForm()
	{
		super(new GridBagLayout());
		setBackground(Color.white);
		initializeComponents();
		layoutComponents();
		page = 1;
	}
	
	private void layoutComponents()
	{
		GridBagConstraints c = new GridBagConstraints();
		int y = 0;
		JSeparator divider1 = new JSeparator(SwingConstants.HORIZONTAL);
		JSeparator divider2 = new JSeparator(SwingConstants.HORIZONTAL);
		divider1.setPreferredSize(new Dimension(1,1));
		divider2.setPreferredSize(divider1.getPreferredSize());
		divider1.setBackground(Color.LIGHT_GRAY);
		divider2.setBackground(divider1.getBackground());
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y++;
		findings1Panel.add(findings1Label, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,30,0);
		findings1Panel.add(divider1, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		findings1Panel.add(diagnosisLabel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y++;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,20,0);
		findings1Panel.add(diagnosisScroller, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.weighty = 0.0;
		c.insets = new Insets(0,0,0,0);
		findings1Panel.add(remarksLabel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y++;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,0,0);
		findings1Panel.add(remarksScroller, c);
		
		y = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = y++;
		findings2Panel.add(findings2Label, c);
		c.gridy = y++;
		c.insets = new Insets(0,0,30,0);
		findings2Panel.add(divider2, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.insets = new Insets(0,0,0,0);
		findings2Panel.add(grossDescLabel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y++;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,20,0);
		findings2Panel.add(grossDescScroller, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = y++;
		c.weighty = 0.0;
		c.insets = new Insets(0,0,0,0);
		findings2Panel.add(microNoteLabel, c);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = y++;
		c.weighty = 1.0;
		c.insets = new Insets(0,0,0,0);
		findings2Panel.add(microNoteScroller, c);
		
		cardPanel.add("OVERVIEW", overviewPanel);
		cardPanel.add("FINDINGS1", findings1Panel);
		cardPanel.add("FINDINGS2", findings2Panel);
		
		bottomPanel.add(previousButton);
		bottomPanel.add(pageLabel);
		bottomPanel.add(nextButton);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(cardPanel, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = 1;
		c.weighty = 0.0;
		c.weightx = 0.0;
		add(bottomPanel, c);
	}
	
	private void initializeComponents()
	{	
		findings1Label = new JLabel("Interpretation and Results");
		findings2Label = new JLabel("Interpretation and Results");
		findings1Label.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		findings2Label.setBackground(findings1Label.getBackground());
		findings1Label.setFont(LoginWindow.SECONDARY_FONT.deriveFont(StyleConstants.CATEGORY));
		findings2Label.setFont(findings1Label.getFont());
		findings1Label.setForeground(Color.gray);
		findings2Label.setForeground(findings1Label.getForeground());
		bottomPanel = new JPanel(new GridLayout(1, 3, 1, 1));
		bottomPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		overviewPanel = new RecordOverview(RecordConstants.HISTOPATHOLOGY_RECORD);
		cardPanel = new JPanel(new CardLayout());
		findings1Panel = new JPanel(new GridBagLayout());
		findings2Panel = new JPanel(new GridBagLayout());
		cardPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		overviewPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		findings1Panel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		findings2Panel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		
		diagnosisLabel = new JLabel("Diagnosis");
		remarksLabel = new JLabel("Remarks");
		grossDescLabel = new JLabel("Gross Description");
		microNoteLabel = new JLabel("Microscopic Notes");
		diagnosisValue= new JTextArea(5,32);
		grossDescValue = new JTextArea(5,32);
		microNoteValue = new JTextArea(5,32);
		remarksValue= new JTextArea(5,32);
		
		diagnosisValue.setLineWrap(true);
		diagnosisValue.setWrapStyleWord(true);
		grossDescValue.setLineWrap(true);
		grossDescValue.setWrapStyleWord(true);
		microNoteValue.setLineWrap(true);
		microNoteValue.setWrapStyleWord(true);
		remarksValue.setLineWrap(true);
		remarksValue.setWrapStyleWord(true);

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
		
		remarksValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		grossDescValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		microNoteValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		diagnosisValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		
		pageLabel = new JLabel("1 of 3");
		pageLabel.setOpaque(true);
		pageLabel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
		pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		nextButton = new JButton();
		nextButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_next.png")));
		nextButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_next_hover.png")));
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);
		nextButton.setOpaque(true);
		nextButton.setBackground(Color.white);
		previousButton = new JButton();
		previousButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_previous.png")));
		previousButton.setRolloverIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_previous_hover.png")));
		previousButton.setContentAreaFilled(false);
		previousButton.setBorderPainted(false);
		previousButton.setOpaque(true);
		previousButton.setBackground(Color.white);
		previousButton.setVisible(false);
		
		nextButton.addActionListener(this);
		previousButton.addActionListener(this);
	}
	
	@Override
	public void setFields(Record record, Patient patient)
	{	
		overviewPanel.setRecordFields(record);
		overviewPanel.setPatientFields(patient);
		
		List<Result> diagnosis = (List<Result>)record.getAttribute(RecordTable.RESULTS);
		if(diagnosis != null)
		{
			Iterator<Result> i = diagnosis.iterator();
			while(i.hasNext())
			{
				Result curDiagnosis = i.next();
				String value = curDiagnosis.getValue();
				int category = curDiagnosis.getCategory();
				switch(category)
				{
				case ResultCategoriesConstants.REMARKS: remarksValue.setText(value);
				break;
				case ResultCategoriesConstants.MICROSCOPIC_NOTES: microNoteValue.setText(value);
				break;
				case ResultCategoriesConstants.GROSS_DESCRIPTION: grossDescValue.setText(value);
				break;
				case ResultCategoriesConstants.DIAGNOSIS: diagnosisValue.setText(value);
				}
			}
		}
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
		Record record = overviewPanel.getRecord();
		record.putAttribute(RecordTable.SPEC_TYPE, TitleConstants.OTHERS);
		
		List<Result> results = new Vector<Result>();
		if(diagnosisValue.getText().length() > 0)
		{
			Result diagnosis = new Result(ResultCategoriesConstants.DIAGNOSIS, diagnosisValue.getText());
			results.add(diagnosis);
		}
		if(remarksValue.getText().length() > 0)
		{
			Result remark = new Result(ResultCategoriesConstants.REMARKS, remarksValue.getText());
			results.add(remark);
		}
		if(grossDescValue.getText().length() > 0)
		{
			Result gd = new Result(ResultCategoriesConstants.GROSS_DESCRIPTION, grossDescValue.getText());
			results.add(gd);
		}
		if(microNoteValue.getText().length() > 0)
		{
			Result mn = new Result(ResultCategoriesConstants.MICROSCOPIC_NOTES, microNoteValue.getText());
			results.add(mn);
		}
		record.putAttribute(RecordTable.RESULTS, results);
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
		overviewPanel.getPatientForm().setClearExisting(isEnabled);
	}

	@Override
	public PatientForm getPatientForm() 
	{
		return overviewPanel.getPatientForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		CardLayout cl = (CardLayout)cardPanel.getLayout();
		Object source = e.getSource();
		if(source.equals(nextButton))
		{
			if(page < 3)
			{
				page++;
				cl.next(cardPanel);
			}
		}
		else
		{
			if(page > 1)
			{
				page--;
				cl.previous(cardPanel);
			}
		}
		updateGUI();
	}
	
	private void updateGUI()
	{
		if(page > 1)
			previousButton.setVisible(true);
		else previousButton.setVisible(false);
		if(page < 3)
			nextButton.setVisible(true);
		else nextButton.setVisible(false);
		
		pageLabel.setText(page + " of 3");
	}
}