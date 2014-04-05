package org.introse.core;


import java.awt.Graphics;
import java.awt.print.PageFormat;

public class HistopathologyRecord extends Record
{

	public HistopathologyRecord()
	{
		super();
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int page)
	{
		return 0;
	}
}