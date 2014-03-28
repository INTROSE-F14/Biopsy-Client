package org.introse.gui.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BackupRestore extends JPanel 
{
	private JTextField textField;
	private JTable table;
	public BackupRestore() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPhrase = new JLabel("      PHRASE: ");
		lblPhrase.setFont(new Font("Calibri", Font.BOLD, 14));
		GridBagConstraints gbc_lblPhrase = new GridBagConstraints();
		gbc_lblPhrase.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhrase.anchor = GridBagConstraints.EAST;
		gbc_lblPhrase.gridx = 0;
		gbc_lblPhrase.gridy = 2;
		add(lblPhrase, gbc_lblPhrase);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(30);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Calibri", Font.BOLD, 15));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 2;
		add(btnAdd, gbc_btnAdd);
		
		table = new JTable();
		table.setBackground(Color.decode("#ECF0F1"));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 3;
		add(table, gbc_table);
	}

}
