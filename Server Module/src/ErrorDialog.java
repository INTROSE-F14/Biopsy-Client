import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class ErrorDialog extends JDialog implements ActionListener 
{
	public ErrorDialog(String errorTitle, String errorMessage)
	{
		super(null, errorTitle, ModalityType.APPLICATION_MODAL);
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel errorLabel = new JLabel(errorMessage);
		errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(errorLabel);
		JButton okButton = new JButton("OK");
		okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		okButton.addActionListener(this);
		panel.add(okButton);
		setContentPane(panel);
		pack();
	}
	
	public void showGui()
	{
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		dispose();
	}
}
