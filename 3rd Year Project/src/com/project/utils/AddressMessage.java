package com.project.utils;

import java.net.InetAddress;

public class AddressMessage {

	private InetAddress address;
	private Message message;
	
	public AddressMessage(InetAddress address, Message message) {
		this.address = address;
		this.message = message;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public Message getMessage() {
		return message;
	}
}
