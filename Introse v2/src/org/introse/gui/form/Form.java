package org.introse.gui.form;


public interface Form 
{
	public abstract boolean areFieldsValid();
	public abstract void setFields(Object object);
	public abstract Object getObject();
	public abstract void setEditable(boolean isEditable);
	
}