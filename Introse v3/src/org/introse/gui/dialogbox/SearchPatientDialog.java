package org.introse.gui.dialogbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import org.introse.Constants.PatientTable;
import org.introse.core.CustomCalendar;
import org.introse.core.Patient;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.MainMenu;

public class SearchPatientDialog extends JDialog implements SearchDialog
{
   
   private JTextField tf_fname,tf_mname, tf_lname, tf_room;
   private JLabel lbl_fname, lbl_mname, lbl_lname, lbl_birthday, lbl_gender, lbl_room;
   private JComboBox<String> cb_month, cb_day, cb_year, cb_gender;
   private JPanel p_overall, p_buttonHolder, p_container;
   private JButton b_search, b_clear;
   
   public SearchPatientDialog()
   {
	    super(null, "Search Patient", ModalityType.MODELESS);
	    
            this.p_overall = new JPanel();
	    this.p_buttonHolder = new JPanel();
	    this.p_container = new JPanel();
            
	    this.tf_fname = new JTextField(20);
	    this.tf_mname = new JTextField(20);
	    this.tf_lname = new JTextField(20);
	    this.tf_room = new JTextField(5);
            
            this.tf_fname.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.tf_mname.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.tf_lname.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.tf_room.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
	    
	    this.lbl_fname = new JLabel("First Name: ");
	    this.lbl_mname = new JLabel("Middle Name: ");
	    this.lbl_lname = new JLabel("Last Name: ");
	    this.lbl_birthday = new JLabel("Birthday: ");
	    this.lbl_gender = new JLabel("Gender: ");
	    this.lbl_room = new JLabel("Room: ");
            
            this.lbl_fname.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.lbl_mname.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.lbl_lname.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.lbl_birthday.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.lbl_gender.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.lbl_room.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
	    
	    String[] month = {"Month", "January", "Febuary", "March", "April", "May", "June", "July", "August", 
	    		"September", "October", "November", "December"};
	    String[] day = {"Day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", 
	                   "17", "18", "19,", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	    String[] gender = {"Any", "M", "F"};
	    
	    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		String[] years = new String[102];
		years[0] = "Year";
		int j = 1;
		for(int i = currentYear; i >= currentYear - 100; i--)
		{
			years[j] = "" + i;
			j++;
		}
	    this.cb_month = new JComboBox<String>(month);
	    this.cb_day = new JComboBox<String>(day);
	    this.cb_year = new JComboBox<String>(years);
	    this.cb_gender = new JComboBox<String>(gender);
            
            this.cb_month.setBorder(null);
            this.cb_day.setBorder(null);
            this.cb_year.setBorder(null);
            this.cb_gender.setBorder(null);
            
            this.cb_month.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.cb_day.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.cb_year.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
            this.cb_gender.setFont(MainMenu.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
	    
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
	    this.p_overall.add(lbl_fname, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 0;
	    c.gridwidth = 3;
	    this.p_overall.add(tf_fname, c);

//Second Line
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 1;
	    this.p_overall.add(lbl_mname, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 1;
	    c.gridwidth = 3;
	    this.p_overall.add(tf_mname, c);

//Third Line            
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 2;
	    this.p_overall.add(lbl_lname, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 2;
	    c.gridwidth = 3;
	    this.p_overall.add(tf_lname, c);
	    
//Fourth Line            
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 3;
	    c.gridwidth = 1;
	    this.p_overall.add(lbl_birthday, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 3;
	    c.gridwidth = 1;
	    this.p_overall.add(cb_month, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 2;
	    c.gridy = 3;
	    c.gridwidth = 1;
	    this.p_overall.add(cb_day, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 3;
	    c.gridy = 3;
	    c.gridwidth = 1;
	    this.p_overall.add(cb_year, c);
	
//Fifth Line            
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 4;
	    c.gridwidth = 1;
	    this.p_overall.add(lbl_gender, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 4;
	    c.gridwidth = 1;
	    this.p_overall.add(cb_gender, c);
            
//Sixth Line	
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 0;
	    c.gridy = 5;
	    c.gridwidth = 1;
	    this.p_overall.add(lbl_room, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridx = 1;
	    c.gridy = 5;
	    c.gridwidth = 1;
	    this.p_overall.add(tf_room, c);

//Seventh Line            
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
	    this.p_overall.setBackground(Color.white);
                
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
            c.insets = new Insets(0,0,-210,0);
            this.p_container.add(p_buttonHolder,c);
            
            this.p_container.setBorder(new EmptyBorder(20,20,20,20));
            this.p_container.setBackground(Color.white);
	    setContentPane(p_container);
   }

    public void showGUI()
    {
        this.pack();
        this.setMinimumSize(new Dimension(600,400));
        this.setVisible(true);
    }

    public void addListener(CustomListener listener)
    {
        this.b_search.addActionListener(listener);
        this.b_clear.addActionListener(listener);   
        b_search.setActionCommand(ActionConstants.SEARCH);
        b_clear.setActionCommand(ActionConstants.CLEAR);
    }
   
    public void clear()
    {
        this.cb_month.setSelectedIndex(0);
        this.cb_day.setSelectedIndex(0);
        this.cb_year.setSelectedIndex(0);
        this.cb_gender.setSelectedIndex(0);

        this.tf_fname.setText("");
        this.tf_mname.setText("");
        this.tf_lname.setText("");
        this.tf_room.setText("");
    }

	@Override
	public Object getSearchCriteria() 
	{
	    Patient criteria = new Patient();
	    CustomCalendar cal = new CustomCalendar();
	
	    String gType = (String)this.cb_gender.getSelectedItem();
	    String pFName = this.tf_fname.getText();
	    String pMName = this.tf_mname.getText();
	    String pLName = this.tf_lname.getText();
	    int bMonth = this.cb_month.getSelectedIndex();
	    int bDay = this.cb_day.getSelectedIndex();
	    int bYear = this.cb_year.getSelectedIndex();
	    int bMo = -1; 
            int bD= -1;
            int bY  = -1;

	     if(pFName.length() != 0)
	        criteria.putAttribute(PatientTable.FIRST_NAME.toString(), pFName);
	
	     if(pMName.length() != 0)
	        criteria.putAttribute(PatientTable.MIDDLE_NAME.toString(), pMName);
	
	     if(pLName.length() != 0)
	        criteria.putAttribute(PatientTable.LAST_NAME.toString(), pLName);
	
	     if(bMonth != 0)
	        bMo = bMonth;
	
	     if(bDay != 0)
	        bD = bDay;
	     
	     if(bYear != 0)
	        bY = Integer.parseInt((String)cb_year.getSelectedItem());
	
	     cal.set(bMo, bD, bY);
	     criteria.putAttribute(PatientTable.BIRTHDAY.toString(), cal);
	
	     if(gType != "Any")
	        criteria.putAttribute(PatientTable.GENDER.toString(), gType);
     
	     return criteria;
	}
}
