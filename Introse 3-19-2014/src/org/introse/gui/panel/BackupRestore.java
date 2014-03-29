package org.introse.gui.panel;



import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BackupRestore extends JPanel {
	
	private JTextField textField, textField2;
	
	public BackupRestore() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblBackup = new JLabel("      Backup: ");
		lblBackup.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblBackup = new GridBagConstraints();
		gbc_lblBackup.insets = new Insets(0, 0, 5, 5);
		gbc_lblBackup.anchor = GridBagConstraints.EAST;
		gbc_lblBackup.gridx = 0;
		gbc_lblBackup.gridy = 2;
		add(lblBackup, gbc_lblBackup);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(30);
		
		JLabel lblPhrase = new JLabel("      RESTORE: ");
		lblPhrase.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPhrase = new GridBagConstraints();
		gbc_lblPhrase.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhrase.anchor = GridBagConstraints.EAST;
		gbc_lblPhrase.gridx = 0;
		gbc_lblPhrase.gridy = 5;
		add(lblPhrase, gbc_lblPhrase);
		
		textField2 = new JTextField();
		GridBagConstraints gbc_textField2 = new GridBagConstraints();
		gbc_textField2.insets = new Insets(0, 0, 5, 5);
		gbc_textField2.anchor = GridBagConstraints.WEST;
		gbc_textField2.gridx = 1;
		gbc_textField2.gridy = 5;
		add(textField2, gbc_textField2);
		textField2.setColumns(30);
		
	
		JButton btnBackup = new JButton("BACKUP");
		btnBackup.setFont(new Font("Calibri", Font.BOLD, 15));
		GridBagConstraints gbc_btnBackup = new GridBagConstraints();
		gbc_btnBackup.insets = new Insets(0, 0, 5, 0);
		gbc_btnBackup.gridx = 2;
		gbc_btnBackup.gridy = 2;
		add(btnBackup, gbc_btnBackup); 
		
		JButton btnRestore = new JButton("RESTORE");
		btnRestore.setFont(new Font("Calibri", Font.BOLD, 15));
		GridBagConstraints gbc_btnRestore = new GridBagConstraints();
		gbc_btnRestore.insets = new Insets(0, 0, 5, 0);
		gbc_btnRestore.gridx = 3;
		gbc_btnRestore.gridy = 5;
		add(btnRestore, gbc_btnRestore); 
	}

}
