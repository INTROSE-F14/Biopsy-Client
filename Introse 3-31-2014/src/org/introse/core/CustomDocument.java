package org.introse.core;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CustomDocument extends PlainDocument {
	
	private int limit;
	public CustomDocument(int limit)
	{
		this.limit = limit;
	}
	
	@Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if(getLength() + str.length() <= limit)
            super.insertString(offs, str, a);
    }

}
