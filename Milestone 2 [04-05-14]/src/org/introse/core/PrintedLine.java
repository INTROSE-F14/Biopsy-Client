package org.introse.core;

public class PrintedLine {
	
	private String string;
	private int align;
	
	public PrintedLine(String string, int align){
		this.string = string;
		this.align = align;
	}
	
	
	public String getString(){
		return this.string;
	}
	
	// 0 - left 1 - center 2 - right
	public int getAlign(){
		return this.align;
	}
	
}
