package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.controller.MessageController;
import com.project.network.Node;
import com.project.utils.Message;

class MessageControllerTest {

	private Node node = new Node();
	private MessageController ctrl = node.getMessageController();
	
	@Test
	@DisplayName("Test Message Controller Initialize")
	void testInit() {		
		assertNotNull(ctrl);
	}
	
	@Test
	@DisplayName("Test Queue To Send")
	void testQueueToSend() {
		
		ctrl.queueToSend("Test Message");		
		LinkedList<Message> msgs = ctrl.messagesToSend;
		
		assertAll("Queue",
			() -> assertTrue(msgs.size() == 1),
			() -> assertTrue(msgs.getFirst().getText().equals("Test Message"))
		);
	}
	
	@Test
	@DisplayName("Test Add To Recieved")
	void testAddToRecieved() {
		
		Message msg = new Message(true);
		ctrl.addToRecieved(msg);
		LinkedList<Message> msgs = ctrl.messagesRecieved;		
		
		assertAll("Recieved Properties",
			() -> assertTrue(msgs.size() == 1),
			() -> assertTrue(msgs.getFirst().leaveNetwork() == true)
		);
	}
}
