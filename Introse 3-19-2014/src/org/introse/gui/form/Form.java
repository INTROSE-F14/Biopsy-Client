package org.introse.gui.form;

import org.introse.gui.event.CustomListener;


public interface Form 
{
	public boolean areFieldsValid();
	public void setFields(Object object);
	public Object getObject();
	public void setEditable(boolean isEditable);
	public void addListener(CustomListener listener);
	
}