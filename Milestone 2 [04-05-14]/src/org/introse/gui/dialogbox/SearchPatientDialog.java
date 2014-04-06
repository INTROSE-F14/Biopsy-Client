package org.introse.gui.dialogbox;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.introse.Constants;
import org.introse.Constants.ActionConstants;
import org.introse.Constants.PatientTable;
import org.introse.Constants.TitleConstants;
import org.introse.core.CustomCalendar;
import org.introse.core.Patient;
import org.introse.gui.combobox.DatePicker;
import org.introse.gui.event.CustomListener;
import org.introse.gui.window.LoginWindow;

public class SearchPatientDialog extends SearchDialog implements ActionListener
{
   
   private JTextField tf_fname,tf_mname, tf_lname;
   private JLabel lbl_fname, lbl_mname, lbl_lname, lbl_birthday, lbl_gender;
   private JComboBox<String> cb_gender;
   private JPanel pane;
   private JButton b_search, b_clear;
   private DatePicker bDay;
   
   public SearchPatientDialog()
   {
	    super(TitleConstants.SEARCH_PATIENT);
	    initializeComponents();
	    layoutComponents();
	    setContentPane(pane);
   }

   private void initializeComponents()
   {
	   pane = new JPanel(new GridBagLayout());
	    pane.setBackground(Color.white);
	    pane.setBorder(new EmptyBorder(20,20,20,20));
           
	    tf_fname = new JTextField(20);
	    tf_mname = new JTextField(20);
	    tf_lname = new JTextField(20);
           
       tf_fname.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       tf_mname.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       tf_lname.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
	    
	    lbl_fname = new JLabel("First Name: ");
	    lbl_mname = new JLabel("Middle Name: ");
	    lbl_lname = new JLabel("Last Name: ");
	    lbl_birthday = new JLabel("Birthday: ");
	    lbl_gender = new JLabel("Gender: ");
           
       lbl_fname.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       lbl_mname.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       lbl_lname.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       lbl_birthday.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
       lbl_gender.setFont(LoginWindow.SECONDARY_FONT.deriveFont(Constants.StyleConstants.SUBHEADER));
	    
	    String[] gender = {"Any", "M", "F"};
	    cb_gender = new JComboBox<String>(gender);
	    cb_gender.setBorder(null);
	    cb_gender.setBackground(Color.white);
	    cb_gender.setFont(lbl_fname.getFont());
	    bDay = new DatePicker(100, true);
	    bDay.setPickerFont(lbl_fname.getFont());
	    b_search = new JButton("search");
	    b_clear = new JButton("clear");
           
       b_search.setFont(lbl_fname.getFont());
       b_clear.setFont(lbl_fname.getFont());
	
   }
   
   private void layoutComponents()
   {
	   
       int y = 0;
	    GridBagConstraints c = new GridBagConstraints();
	    c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = y++;
		c.insets = new Insets(0,0,5,20);
		pane.add(lbl_fname, c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,5,0);
		pane.add(tf_fname, c);
		
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,20);
		pane.add(lbl_mname, c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,5,0);
		pane.add(tf_mname, c);
		
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,20);
		pane.add(lbl_lname, c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,5,0);
		pane.add(tf_lname, c);
		
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,5,20);
		pane.add(lbl_gender, c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,5,0);
		pane.add(cb_gender, c);
		
		c.gridx = 0;
		c.gridy = y++;
		c.gridwidth = 1;
		c.insets = new Insets(0,0,20,20);
		pane.add(lbl_birthday, c);
		c.gridx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(0,0,20,0);
		pane.add(bDay, c);
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = y;
		pane.add(b_search, c);
		c.gridx = 2;
		pane.add(b_clear, c);
   }
    public void showGUI()
    {
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void addListener(CustomListener listener)
    {
        b_search.addActionListener(listener);
        b_clear.addActionListener(this);   
        b_search.setActionCommand(ActionConstants.SEARCH);
    }
   
    public void clear()
    {
        bDay.reset();
        cb_gender.setSelectedIndex(0);
        tf_fname.setText("");
        tf_mname.setText("");
        tf_lname.setText("");
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
	    int bMonth = bDay.getMonth();
	    int bDay = this.bDay.getDay();
	    int bYear =this.bDay.getYear();

	     if(pFName.length() != 0)
	        criteria.putAttribute(PatientTable.FIRST_NAME.toString(), pFName);
	
	     if(pMName.length() != 0)
	        criteria.putAttribute(PatientTable.MIDDLE_NAME.toString(), pMName);
	
	     if(pLName.length() != 0)
	        criteria.putAttribute(PatientTable.LAST_NAME.toString(), pLName);
	
	     cal.set(bMonth, bDay, bYear);
	     criteria.putAttribute(PatientTable.BIRTHDAY.toString(), cal);
	
	     if(gType != "Any")
	        criteria.putAttribute(PatientTable.GENDER.toString(), gType);
     
	     return criteria;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		clear();
	}
}
