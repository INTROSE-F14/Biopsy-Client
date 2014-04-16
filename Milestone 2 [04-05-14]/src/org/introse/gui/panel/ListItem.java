package org.introse.gui.panel;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.introse.Constants.ActionConstants;

public abstract class ListItem extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<String> labels;
	protected ActionListener buttonListener;
	protected JButton deleteButton;
	
	public ListItem()
	{
		super(new GridBagLayout());
		labels = new Vector<String>();
		deleteButton = new JButton();
		deleteButton.setContentAreaFilled(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setFocusable(false);
		deleteButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_place_holder.png")));
	}
	
	public abstract void initializePanel();
	
	public int getLabelCount()
	{
		return labels.size();
	}
	
	public String getLabel(int index)
	{
		return labels.get(index);
	}
	
	public void addListener(MouseListener listener)
	{
		addMouseListener(listener);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				deleteButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_place_holder.png")));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_delete.png")));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
		deleteButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				deleteButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_place_holder.png")));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				deleteButton.setIcon(new ImageIcon(getClass().getResource("/res/icons/ic_action_delete_hover.png")));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}
	
	public void addButtonListener(ActionListener buttonListener)
	{
		this.buttonListener = buttonListener;
		deleteButton.addActionListener(buttonListener);
		deleteButton.setActionCommand(ActionConstants.DELETE);
	}
	
	public void setDeleteEnabled(boolean isEnabled)
	{
		deleteButton.setVisible(isEnabled);
		deleteButton.setEnabled(isEnabled);
	}
}
