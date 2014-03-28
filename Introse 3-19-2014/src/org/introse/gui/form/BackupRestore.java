package org.introse.gui.form;



import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackupRestore extends JPanel 
{
	final JFileChooser fc = new JFileChooser();
	
	int returnVal = fc.showOpenDialog(fc);

}
