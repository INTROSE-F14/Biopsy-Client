package org.introse.gui.dialogbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.RecordConstants;
import org.introse.Constants.RecordTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.CytologyRecord;
import org.introse.core.GynecologyRecord;
import org.introse.core.HistopathologyRecord;
import org.introse.core.Record;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.MainMenu;

public class SearchRecordDialog extends JDialog implements SearchDialog
{
    private JTextField tf_refNum, tf_specimen, tf_pathologist, tf_physician, tf_room;
    private JLabel lbl_refNum, lbl_specimen, lbl_dReceived, lbl_dCompleted, lbl_pathologist, lbl_physician, lbl_room, lbl_filler;
    private JPanel p_overall, p_buttonHolder, p_container;
    private JComboBox<String> cb_type;
    private JComboBox<String>[] cb_month, cb_day, cb_year;
    private JButton b_search, b_clear;

	
   public SearchRecordDialog()
   {
		super(null, "Search Record", ModalityType.APPLICATION_MODAL);
		this.p_overall = new JPanel();
		this.p_buttonHolder = new JPanel();
		this.p_container = new JPanel();
		
		this.tf_refNum = new JTextField(20);
		this.tf_specimen = new JTextField(20);
		this.tf_pathologist = new JTextField(20);
		this.tf_physician = new JTextField(20);
                this.tf_room = new JTextField(5);
                
                this.tf_refNum.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
		this.tf_specimen.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.tf_pathologist.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.tf_physician.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.tf_room.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                
		this.lbl_refNum = new JLabel("Reference Number: ");
		this.lbl_specimen = new JLabel("Specimen: ");
		this.lbl_dReceived = new JLabel("Date Received: ");
		this.lbl_dCompleted = new JLabel("Date Completed: ");
		this.lbl_pathologist = new JLabel("Pathologist: ");
		this.lbl_physician = new JLabel("Physician: ");
                this.lbl_room = new JLabel("Room: ");
		this.lbl_filler = new JLabel("");
                
                this.lbl_refNum.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.lbl_specimen.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.lbl_dReceived.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.lbl_dCompleted.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.lbl_pathologist.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.lbl_physician.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                this.lbl_room.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                
		String[] month = {"Month", "January", "Febuary", "March", "April", "May", "June", "July", "August", "September", 
				"October", "November", "December"};
		String[] day = {"Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", 
	                        "17", "18", "19,", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] type = {"Type of Record", TitleConstants.HISTOPATHOLOGY, TitleConstants.GYNECOLOGY, TitleConstants.CYTOLOGY};
		
		this.cb_type = new JComboBox<>(type);
                this.cb_type.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                
		this.cb_month = new JComboBox[2];
		this.cb_day = new JComboBox[2];
		this.cb_year = new JComboBox[2];
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] years = new String[52];
		years[0] = "Year";
		int j = 1;
		for(int i = currentYear; i >= currentYear  - 50; i--)
		{
			years[j] = "" + i;
			j++;
		}
		
		for(int i=0; i<2; i++){
	         cb_month[i] = new JComboBox<>(month);
	         cb_day[i] = new JComboBox<>(day);
	         cb_year[i] = new JComboBox<>(years);
                 
                 cb_month[i].setBorder(null);
                 cb_day[i].setBorder(null);
                 cb_year[i].setBorder(null);
                 
                 cb_month[i].setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                 cb_day[i].setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
                 cb_year[i].setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
	        }
		
		this.b_search = new JButton("SEARCH");
		this.b_clear = new JButton("CLEAR");
                
                this.b_search.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.MENU));
                this.b_clear.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.MENU));
	      
		this.p_container.setLayout(new GridBagLayout());
		this.p_overall.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(4,4,20,20);
	
	//First Line
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		this.p_overall.add(cb_type, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		this.p_overall.add(lbl_filler, c);
		
	//Second Line
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		this.p_overall.add(lbl_refNum, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		this.p_overall.add(tf_refNum, c);
		
	//Third Line
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		this.p_overall.add(lbl_specimen, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		this.p_overall.add(tf_specimen, c);
	
	//Fourth Line
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		this.p_overall.add(lbl_dReceived, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		this.p_overall.add(cb_month[0], c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		this.p_overall.add(cb_day[0], c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		this.p_overall.add(cb_year[0], c);
		
	//Fifth Line
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		this.p_overall.add(lbl_dCompleted, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		this.p_overall.add(cb_month[1], c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		this.p_overall.add(cb_day[1], c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 4;
		c.gridwidth = 1;
		this.p_overall.add(cb_year[1], c);
		
	//Sixth Line
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 5;
		this.p_overall.add(lbl_pathologist, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 3;
		this.p_overall.add(tf_pathologist, c);
		
	//Seventh Line
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 6;
		this.p_overall.add(lbl_physician, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 3;
		this.p_overall.add(tf_physician, c);
	      
	//Eighth Line
                c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 7;
		this.p_overall.add(lbl_room, c);
	      
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		this.p_overall.add(tf_room, c);
                
        //Ninth Line
	        this.p_buttonHolder.setLayout(new GridBagLayout());
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 0;
		c.insets = new Insets(10,0,0,10);
		this.p_buttonHolder.add(b_search,c);
	      
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10,0,0,10);
		this.p_buttonHolder.add(b_clear,c);
	      
		this.p_overall.setBackground(Color.white);
                this.p_buttonHolder.setBackground(Color.white);
                
                c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,125,0);
                this.p_container.add(p_overall,c);
		
                c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,-325,0);
                this.p_container.add(p_buttonHolder,c);
		p_container.setBorder(new EmptyBorder(20,20,20,20));
	        this.p_container.setBackground(Color.white);
		setContentPane(p_container);	
   }
    @Override
	public void showGUI()
	{
		this.pack();
		this.setMinimumSize(new Dimension(500,400));
		this.setVisible(true);
	}
   
   @Override
   public void addListener(CustomListener listener)
   {
	this.b_search.addActionListener(listener);
	this.b_clear.addActionListener(listener);   
	b_search.setActionCommand(ActionConstants.SEARCH);
        b_clear.setActionCommand(ActionConstants.CLEAR);
   }
   
   public void clear()
   {
		this.cb_type.setSelectedIndex(0);
		for(int i=0;i<2;i++)
		{
			this.cb_month[i].setSelectedIndex(0);
			this.cb_day[i].setSelectedIndex(0);
			this.cb_year[i].setSelectedIndex(0);
		}
		
		this.tf_refNum.setText("");
		this.tf_specimen.setText("");
		this.tf_pathologist.setText("");
		this.tf_physician.setText("");
                this.tf_room.setText("");
   }
   
	@Override
	public Object getSearchCriteria() 
	{
		CustomCalendar calDR = new CustomCalendar();
		CustomCalendar calDC = new CustomCalendar();
		
		String rType = (String)cb_type.getSelectedItem();
		String rNum = this.tf_refNum.getText();
		String sName = this.tf_specimen.getText();
		String pathologistName = this.tf_pathologist.getText();
		String physicianName = this.tf_physician.getText();
                String roomName = this.tf_room.getText();
		
		int dRMonth = this.cb_month[0].getSelectedIndex();
		int dRDay = this.cb_day[0].getSelectedIndex();
		int dRYear = this.cb_year[0].getSelectedIndex();
		
		int dCMonth = this.cb_month[1].getSelectedIndex();
		int dCDay = this.cb_day[1].getSelectedIndex();
		int dCYear = this.cb_year[1].getSelectedIndex();
		int dRMo = -1;
		int dRD = -1;
		int dRY = -1;
		int dCMo = -1;
		int dCD = -1;
		int dCY = -1;
		
		Record criteria;
		switch(rType)
		{
			case TitleConstants.HISTOPATHOLOGY: criteria = new HistopathologyRecord();
		   									   criteria.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.HISTOPATHOLOGY_RECORD);
			   break;
			case TitleConstants.GYNECOLOGY: criteria = new GynecologyRecord();
		   								   criteria.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.GYNECOLOGY_RECORD);
			   break;
			case TitleConstants.CYTOLOGY: criteria = new CytologyRecord();
		   								   criteria.putAttribute(RecordTable.RECORD_TYPE, RecordConstants.CYTOLOGY_RECORD);
		   								   break;
			default: criteria = new Record();
		}
		   
		if (rNum.length() != 0)
			criteria.putAttribute(RecordTable.REF_NUM, rNum);
		   
		if(sName.length() != 0)
			criteria.putAttribute(RecordTable.SPECIMEN, sName);
				
		if(dRMonth != 0)
			dRMo = dRMonth;
			    
		if(dRDay != 0)
		    dRD = dRDay;
			    
		if(dRYear != 0)
		    dRY = Integer.parseInt((String)cb_year[0].getSelectedItem());
			    
		if(dCMonth != 0)
		    dCMo = dCMonth;
			    
		if(dCDay != 0)
		    dCD = dCDay;
			    
		if(dCYear != 0)
		    dCY = Integer.parseInt((String)cb_year[1].getSelectedItem());
			    
		calDR.set(dRMo, dRD, dRY);
		criteria.putAttribute(RecordTable.DATE_RECEIVED.toString(), calDR);
		calDC.set(dCMo, dCD, dCY);
		criteria.putAttribute(RecordTable.DATE_COMPLETED.toString(), calDC);
		
		if(pathologistName.length() != 0)
		 	criteria.putAttribute(RecordTable.PATHOLOGIST.toString(), pathologistName);
		
		if(physicianName.length() != 0)
			criteria.putAttribute(RecordTable.PHYSICIAN.toString(), physicianName);
                
                if(roomName.length() != 0)
                        criteria.putAttribute(RecordTable.ROOM.toString(), roomName);
		
		return criteria;
	}
   
}

