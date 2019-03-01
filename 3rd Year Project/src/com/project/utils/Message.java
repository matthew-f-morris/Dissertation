package com.project.utils;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private String text = "TCP: Hello from ";
	
	public Message(String msg) {
		text += msg;
	}
	
	public String getText() {		
		return text;
	}
}
