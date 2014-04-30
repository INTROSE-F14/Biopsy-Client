package org.introse.gui.dialogbox;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.StyleConstants;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.CustomDocument;
import org.introse.core.Patient;
import org.introse.core.Record;
import org.introse.core.ResultCriteria;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.LoginWindow;

public class SearchRecordDialog extends SearchDialog implements KeyListener, ActionListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tf_refNum, tf_specimen, tf_pathologist, tf_physician, tf_room,  tf_patient;
    private JLabel lbl_refNum, lbl_specimen, lbl_dReceived, lbl_dCompleted, 
    lbl_pathologist, lbl_physician, lbl_room, lbl_dash, lbl_patient, 
    diagnosisLabel, remarksLabel, grossDescLabel, microNoteLabel;
    private JTextArea diagnosisValue, remarksValue, grossDescValue, microNoteValue;
    private JScrollPane diagnosisScroller, remarksScroller, grossDescScroller, 
	microNoteScroller;
    private JComboBox<String> cb_type, cb_year;
    private JButton b_search, b_clear, b_load, b_patientClear;
    private JPanel pane, resultsPanel, recordPanel;
    private DatePicker dR, dC;
    private int patientID;
	
   public SearchRecordDialog()
   {
		super(TitleConstants.SEARCH_RECORD);
		patientID = -1;
		initializeComponents();
		layoutComponents();
		setContentPane(pane);	
   }
   
   private void initializeComponents()
   {
	   pane = new JPanel(new GridBagLayout());
	   pane.setBorder(new EmptyBorder(20,20,20,20));
	   pane.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
	   recordPanel = new JPanel(new GridBagLayout());
	   recordPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
	   resultsPanel = new JPanel(new GridBagLayout());
	   resultsPanel.setBackground(Color.decode(StyleConstants.PRIMARY_COLOR));
	   tf_refNum = new JTextField(4);
	   tf_specimen = new JTextField(25);
	   tf_pathologist = new JTextField(25);
	   tf_physician = new JTextField(25);
	   tf_patient = new JTextField(15);
	   tf_room = new JTextField(5);
	   tf_patient.setEditable(false);
               
       tf_refNum.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       tf_specimen.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       tf_pathologist.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       tf_physician.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       tf_room.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       tf_refNum.addKeyListener(this);
       lbl_refNum = new JLabel("Reference Number");
       lbl_specimen = new JLabel("Specimen");
       lbl_dReceived = new JLabel("Date Received");
       lbl_dCompleted = new JLabel("Date Completed");
       lbl_pathologist = new JLabel("Pathologist");
       lbl_physician = new JLabel("Physician");
       lbl_room = new JLabel("Room");
       lbl_dash = new JLabel("-");
       lbl_patient = new JLabel("Patient");
               
       lbl_refNum.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       lbl_specimen.setFont(lbl_refNum.getFont());
       lbl_dReceived.setFont(lbl_refNum.getFont());
       lbl_dCompleted.setFont(lbl_refNum.getFont());
       lbl_pathologist.setFont(lbl_refNum.getFont());
       lbl_physician.setFont(lbl_refNum.getFont());
       lbl_room.setFont(lbl_refNum.getFont());
       lbl_dash.setFont(lbl_refNum.getFont());
       lbl_patient.setFont(lbl_refNum.getFont());
       tf_patient.setFont(lbl_refNum.getFont());
		String[] type = {"Any", ""+RecordConstants.HISTOPATHOLOGY_RECORD, 
				""+RecordConstants.GYNECOLOGY_RECORD, ""+RecordConstants.CYTOLOGY_RECORD};
		dR = new DatePicker(50, true);
		dC = new DatePicker(50, true);
		cb_type = new JComboBox<>(type);
        cb_type.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
        cb_type.setBorder(null);
        int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
        int i = year;
        int j = 1;
        String [] recordYears = new String[year + 2];
        recordYears[0] = "Any";
        while(j < year + 2)
        {
        	if(i > 9)
        		recordYears[j] = ""+i;
        	else recordYears[j] = "0"+i;
        	i--;
        	j++;
        }
        cb_year = new JComboBox<String>(recordYears);
        cb_year.setFont(cb_type.getFont());
        cb_year.setBorder(null);
		dR.setPickerFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		dC.setPickerFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		b_search = new JButton("find");
		b_clear = new JButton("clear");
		b_load = new JButton("choose");
		b_patientClear = new JButton("clear");
               
        b_search.setFont(lbl_refNum.getFont());
        b_clear.setFont(b_search.getFont());
        b_load.setFont(b_search.getFont());
        b_patientClear.setFont(b_search.getFont());
        
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
		remarksValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		grossDescValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		microNoteValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		diagnosisValue.setDocument(new CustomDocument(RecordConstants.RESULTS_LENGTH));
		
        diagnosisScroller = new JScrollPane(diagnosisValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		remarksScroller = new JScrollPane(remarksValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		grossDescScroller = new JScrollPane(grossDescValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		microNoteScroller = new JScrollPane(microNoteValue, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   }
    @Override
	public void showGUI()
	{
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
   
   @Override
   public void addListener(CustomListener listener)
   {
		b_search.addActionListener(listener);
		b_clear.addActionListener(this);   
		b_search.setActionCommand(ActionConstants.SEARCH);
        b_load.addActionListener(listener);
        b_load.setActionCommand(ActionConstants.LOAD_PATIENT);
        b_patientClear.addActionListener(this);
   }
   
   private void layoutComponents()
   {
	   int y = 0;
	   GridBagConstraints c = new GridBagConstraints();
	   c.anchor = GridBagConstraints.WEST;
	   c.fill = GridBagConstraints.HORIZONTAL;
	   c.weightx = 1.0;
	   c.weighty = 1.0;
	   c.insets = new Insets(0,0,0,0);
	   resultsPanel.add(remarksLabel, c);
	   c.gridheight = 2;
	   c.gridy = ++y;
	   c.insets = new Insets(0,0,10,0);
	   resultsPanel.add(remarksScroller, c);
	   c.gridheight = 1;
	   y += 2;
	   c.gridy = y;
	   c.insets = new Insets(0,0,0,0);
	   resultsPanel.add(grossDescLabel, c);
	   c.gridy = ++y;
	   c.gridheight = 2;
	   c.insets = new Insets(0,0,10,0);
	   resultsPanel.add(grossDescScroller, c);
	   c.gridheight = 1;
	   y += 2;
	   c.gridy = y;
	   c.insets = new Insets(0,0,0,0);
	   resultsPanel.add(microNoteLabel, c);
	   c.gridheight = 2;
	   c.gridy = ++y;
	   c.insets = new Insets(0,0,10,0);
	   resultsPanel.add(microNoteScroller, c);
	   c.gridheight = 1;
	   y += 2;
	   c.gridy = y;
	   c.insets = new Insets(0,0,0,0);
	   resultsPanel.add(diagnosisLabel, c);
	   c.gridheight = 2;
	   c.gridy = ++y;
	   resultsPanel.add(diagnosisScroller, c);
	   
	   y = 0;
	   c = new GridBagConstraints();
	   c.anchor = GridBagConstraints.WEST;
	   c.fill = GridBagConstraints.HORIZONTAL;
	   c.insets = new Insets(0,0,5,20);
	   c.weightx = 1.0;
	   c.weighty = 1.0;
	   c.gridx = 0;
	   c.gridy = y++;
	   recordPanel.add(lbl_refNum, c);
	   c.gridx  = 1;
	   c.insets = new Insets(0,0,5,5);
	   recordPanel.add(cb_type, c);
	   c.gridx = 2;
	   recordPanel.add(cb_year, c);
	   c.gridx = 3;
	   c.weightx = 0.0;
	   c.fill = GridBagConstraints.NONE;
	   recordPanel.add(lbl_dash, c);
	   c.weightx = 1.0;
	   c.gridx = 4;
	   c.gridwidth =2;
	   c.insets = new Insets(0,0,5,0);
	   c.fill = GridBagConstraints.HORIZONTAL;
	   recordPanel.add(tf_refNum, c);
	   
	   c.gridwidth = 2;
	   c.weightx = 1.0;
	   c.gridy = y++;
	   c.gridx = 0;
	   c.insets = new Insets(0,0,5,20);
	   recordPanel.add(lbl_specimen, c);
	   c.gridx  = 1;
	   c.gridwidth = 5;
	   c.insets = new Insets(0,0,5,0);
	   recordPanel.add(tf_specimen, c);
	   
	   c.gridwidth = 1;
	   c.gridy = y++;
	   c.gridx = 0;
	   c.insets = new Insets(0,0,5,20);
	   recordPanel.add(lbl_pathologist, c);
	   c.gridx  = 1;
	   c.gridwidth = 5;
	   c.insets = new Insets(0,0,5,0);
	   recordPanel.add(tf_pathologist, c);
	   
	   c.gridwidth = 1;
	   c.gridy = y++;
	   c.gridx = 0;
	   c.insets = new Insets(0,0,5,20);
	   recordPanel.add(lbl_physician, c);
	   c.gridx  = 1;
	   c.gridwidth = 5;
	   c.insets = new Insets(0,0,5,0);
	   recordPanel.add(tf_physician, c);
	   
	   c.gridy = y++;
	   c.gridx = 0;
	   c.gridwidth = 1;
	   c.insets = new Insets(0,0,5,20);
	   recordPanel.add(lbl_room, c);
	   c.gridx  = 1;
	   c.gridwidth = 5;
	   c.insets = new Insets(0,0,5,0);
	   recordPanel.add(tf_room, c);
	   
	   c.gridy = y++;
	   c.gridwidth = 1;
	   c.gridx = 0;
	   c.insets = new Insets(0,0,5,20);
	   recordPanel.add(lbl_dReceived, c);
	   c.gridx  = 1;
	   c.gridwidth = 5;
	   c.insets = new Insets(0,0,5,0);
	   recordPanel.add(dR, c);
	   
	   c.gridwidth = 1;
	   c.gridy = y++;
	   c.gridx = 0;
	   c.insets = new Insets(0,0,5,20);
	   recordPanel.add(lbl_dCompleted, c);
	   c.gridx  = 1;
	   c.gridwidth = 5;
	   c.insets = new Insets(0,0,5,0);
	   recordPanel.add(dC, c);
	   
	   c.gridwidth = 1;
	   c.gridy = y++;
	   c.gridx = 0;
	   c.insets = new Insets(0,0,20,20);
	   recordPanel.add(lbl_patient, c);
	   c.gridx  = 1;
	   c.gridwidth = 3;
	   c.insets = new Insets(0,0,20,5);
	   recordPanel.add(tf_patient, c);
	   c.gridx = 4;
	   c.gridwidth = 1;
	   recordPanel.add(b_load, c);
	   c.gridx = 5;
	   c.insets = new Insets(0,0,20,0);
	   recordPanel.add(b_patientClear, c);
	   
	   c.fill = GridBagConstraints.NONE;
	   c.anchor = GridBagConstraints.CENTER;
	   c.insets = new Insets(0,0,5,5);
	   c.gridwidth = 1;
	   c.gridx = 1;
	   c.gridy = y;
	   recordPanel.add(b_search, c);
	   c.gridx = 2;
	   c.insets = new Insets(0,0,5,0);
	   recordPanel.add(b_clear, c);
	   
	   c = new GridBagConstraints();
	   c.anchor = GridBagConstraints.NORTH;
	   c.fill = GridBagConstraints.BOTH;
	   c.weightx = 1.0;
	   c.weighty = 1.0;
	   c.gridx = 0;
	   pane.add(recordPanel, c);
	   c.gridx = 1;
	   c.insets = new Insets(0,20,0,0);
	   pane.add(resultsPanel, c);
   }
   
   public void clear()
   {
		dR.reset();
		dC.reset();
		tf_refNum.setText("");
		tf_specimen.setText("");
		tf_pathologist.setText("");
		tf_physician.setText("");
        tf_room.setText("");
        setPatient(null);
   }
   
	@Override
	public Object getSearchCriteria() 
	{
		CustomCalendar calDR = new CustomCalendar();
		CustomCalendar calDC = new CustomCalendar();
		
		String rType = (String)cb_type.getSelectedItem();
		String rNum = tf_refNum.getText();
		String sName = tf_specimen.getText();
		String pathologistName = tf_pathologist.getText();
		String physicianName = tf_physician.getText();
        String roomName = tf_room.getText();
        int rYear = -1;
        if(cb_year.getSelectedIndex() != 0)
        	rYear = Integer.parseInt((String)cb_year.getSelectedItem());
		
		int dRMonth = dR.getMonth();
		int dRDay = dR.getDay();
		int dRYear = dR.getYear();
		
		int dCMonth = dC.getMonth();
		int dCDay = dC.getDay();
		int dCYear = dC.getYear();
		
		Record criteria = new Record();
		switch(rType)
		{
			case ""+RecordConstants.HISTOPATHOLOGY_RECORD :
		   									   criteria.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
			   break;
			case ""+RecordConstants.GYNECOLOGY_RECORD:
		   								   criteria.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
			   break;
			case ""+RecordConstants.CYTOLOGY_RECORD:
		   								   criteria.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.CYTOLOGY_RECORD);
		   								   break;
			default: criteria = new Record();
		}
		   
		if(rNum.length() > 0)
			criteria.putAttribute(RecordTable.RECORD_NUMBER, Integer.parseInt(rNum));
		if(rYear != -1)
			criteria.putAttribute(RecordTable.RECORD_YEAR, rYear);
		   
		if(sName.length() != 0)
			criteria.putAttribute(RecordTable.SPECIMEN, sName);
			    
		calDR.set(dRMonth, dRDay, dRYear);
		criteria.putAttribute(RecordTable.DATE_RECEIVED.toString(), calDR);
		calDC.set(dCMonth, dCDay, dCYear);
		criteria.putAttribute(RecordTable.DATE_COMPLETED.toString(), calDC);
		
		if(pathologistName.length() != 0)
		 	criteria.putAttribute(RecordTable.PATHOLOGIST.toString(), pathologistName);
		
		if(physicianName.length() != 0)
			criteria.putAttribute(RecordTable.PHYSICIAN.toString(), physicianName);
                
        if(roomName.length() != 0)
        	criteria.putAttribute(RecordTable.ROOM.toString(), roomName);
        
        if(patientID != -1)
        	criteria.putAttribute(RecordTable.PATIENT_ID, patientID);
        
		return criteria;
	}

	public void setPatient(Patient patient)
	{
		if(patient != null)
		{
			patientID = (int)patient.getAttribute(PatientTable.PATIENT_ID);
			String firstName = (String)patient.getAttribute(PatientTable.FIRST_NAME);
			String middleName = (String)patient.getAttribute(PatientTable.MIDDLE_NAME);
			String lastName = (String)patient.getAttribute(PatientTable.LAST_NAME);
			tf_patient.setText(lastName + ", " + firstName + " " + middleName);
		}
		else
		{
			patientID = -1;
			tf_patient.setText("");
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		if(!Character.isDigit(e.getKeyChar())  || tf_refNum.getText().length() >= 4)
				e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e){}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source.equals(b_clear))
			clear();
		else if(source.equals(b_patientClear))
			setPatient(null);
		
	}  
	
	public ResultCriteria getResultCriteria()
	{
		String diagnosis = null, remarks = null, microNotes = null, grossDesc = null;
		if(diagnosisValue.getText().replaceAll("\\s", "").length() > 0)
			diagnosis = diagnosisValue.getText();
		if(remarksValue.getText().replaceAll("\\s", "").length() > 0)
			remarks = remarksValue.getText();
		if(microNoteValue.getText().replaceAll("\\s", "").length() > 0)
			microNotes = microNoteValue.getText();
		if(grossDescValue.getText().replaceAll("\\s", "").length() > 0)
			grossDesc = grossDescValue.getText();
		return new ResultCriteria(diagnosis,remarks,grossDesc, microNotes);
	}
}

