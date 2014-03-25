package org.introse.gui.form;

import org.introse.gui.event.CustomListener;


public interface Form 
{
	public void setFields(Object object);
	public boolean areFieldsValid();
	public Object getObject();
	public void setEditable(boolean isEditable);
	public void addListener(CustomListener listener);
	
}