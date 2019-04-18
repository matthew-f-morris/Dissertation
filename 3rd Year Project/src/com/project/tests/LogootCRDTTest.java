package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.crdt.CGen;
import com.project.crdt.LogootCRDT;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;

class LogootCRDTTest {

	private LogootCRDT crdt = new LogootCRDT(10);
	
	static void setUpBeforeClass() throws Exception {
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
	@DisplayName("Test Set Boundary")
	void testSetBoundary() {
		
		LogootCRDT.setBoundary(1);		
		assertTrue(LogootCRDT.getBoundary() == 1, "Set Boundary Failed");
	}
	
	@Test
	@DisplayName("Test Generate")
	void testGenerate1() throws Exception {
		
		SequenceAtom atom = LogootCRDT.generate("message", CRDTUtility.genStartAtom().atomId.position, CRDTUtility.genStopAtom().atomId.position, 1L, false, false, false, false);
		
		System.out.println("Test Atom: " + atom.toString());
		
		assertAll("Atom Properties",
			() -> assertNotNull(atom),
			() -> assertTrue(atom.atomId.position.ids.size() == 1, "Failed to gen atom correctly"),
			() -> assertTrue((0 < atom.atomId.position.ids.get(0).position) && (atom.atomId.position.ids.get(0).position < Short.MAX_VALUE), "Failed to gen correct atom"),
			() -> assertTrue(atom.atomId.position.ids.get(0).siteId == 1)
		);
	}
	
	@Test
	@DisplayName("Test Generate with Extra")
	void testGenerate2() throws Exception {
		
		SequenceAtom atom = LogootCRDT.generate("message", CRDTUtility.genAtom(Short.MAX_VALUE - 2, 3L).atomId.position, CRDTUtility.genStopAtom().atomId.position, 1L, false, false, false, false);
		
		System.out.println("Test Atom: " + atom.toString());
		
		assertAll("Atom Properties",
			() -> assertNotNull(atom),
			() -> assertTrue(atom.atomId.position.ids.size() == 2, "Failed to gen atom correctly"),
			() -> assertTrue((0 < atom.atomId.position.ids.get(1).position) && (atom.atomId.position.ids.get(1).position < Short.MAX_VALUE), "Failed to gen correct atom"),
			() -> assertTrue(atom.atomId.position.ids.get(1).siteId == 1),
			() -> assertTrue(atom.atomId.position.ids.get(0).position == 32765),
			() -> assertTrue(atom.atomId.position.ids.get(0).siteId == 3)
		);
	}
}
