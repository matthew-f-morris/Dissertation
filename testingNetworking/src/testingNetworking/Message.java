package testingNetworking;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private double number;
	
	public Message(int set) {
		number = (double) set;
	}
	
	public Message() {
		number = Math.ceil(Math.random() * 10000 + 1);
	}
	
	public double getNumber() {		
		return number;
	}
}
