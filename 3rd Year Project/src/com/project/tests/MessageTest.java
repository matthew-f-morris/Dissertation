package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.clock.Clock;
import com.project.crdt.ComponentGenerator;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;
import com.project.utils.Message;
import com.project.utils.PeerData;

class MessageTest {

	public static Clock clock;
	public static SequenceAtom seq;
	public static PeerData data;
	public static Message msg;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		clock = new Clock(50);
		data = new PeerData(1L, "hostname", InetAddress.getLocalHost(), 1000);
		seq = CRDTUtility.genSequenceAtom("message", ComponentGenerator.genPosition(ComponentGenerator.genIdentifier(10, 0L)));
		msg = new Message(data, seq);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test Message Gen")
	void testMessageGen() {
		assertEquals(false, msg.leaveNetwork());
	}
	
	@Test
	@DisplayName("Test Message Properties")
	void testMessageProperties() {
		
		assertAll("Properties",
			() -> assertEquals(false, msg.leaveNetwork()),
			() -> assertEquals("message", msg.getAtom().message),
			() -> assertEquals(1000, msg.getPeerData().getPort()),
			() -> assertEquals(1L, msg.getPeerData().getUuid()),
			() -> assertEquals(50, msg.getAtom().atomId.clock)
		);
	}
}
