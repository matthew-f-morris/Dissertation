package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.network.MessageSender;
import com.project.network.Node;
import com.project.utils.Message;

class MessageSenderTest {

	private static Node node = new Node();
	private static MessageSender test = new MessageSender(node.getMessageController());

	@Test
	@DisplayName("Test initialize message sender")
	void testInitialize() {
		assertNotNull(test);
	}

	@Test
	@DisplayName("Test sendMessage")
	void testSendMessage() {
		try {
			assertTrue(test.sendMessage(InetAddress.getLocalHost(), new Message(false)));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Test Send Leave Network Message")
	void testSendLeaveMessage() {
		
		try {
			assertTrue(test.sendMessage(InetAddress.getLocalHost(), new Message(true)));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
