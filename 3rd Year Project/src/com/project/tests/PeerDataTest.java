package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.utils.PeerData;

public class PeerDataTest {
	
	static PeerData data;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		data = new PeerData(1L, "hostname", InetAddress.getLocalHost(), 1000);
	}
	
	@Test
	@DisplayName("Peer Data Initialization")
	void testInit() {
		assertNotNull(data);
	}

	@Test
	@DisplayName("Peer Data UUID Test")
	void testUuid() {
		assertEquals(1L, data.getUuid());
	}
	
	@Test
	@DisplayName("Peer Data Hostname Test")
	void testHostname() {
		assertEquals("hostname", data.getHostname());
	}
	
	@Test
	@DisplayName("Peer Data get Inet Address Data Test")
	void testInetAddress() {		
		System.out.println("Hostname: " + data.getAddress().getHostName());
		System.out.println("IP Address: " + data.getAddress().getHostAddress());
	}
	
	@Test
	@DisplayName("Peer Data Port Test")
	void testPort() {
		assertEquals(1000, data.getPort());
	}
}
