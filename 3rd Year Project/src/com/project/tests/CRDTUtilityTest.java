package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.project.clock.Clock;
import com.project.crdt.ComponentGenerator;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;

class CRDTUtilityTest {

	private static Clock clock;
	
	@BeforeAll
	static void beforeAll() {
		clock = new Clock();
	}
	
	@ParameterizedTest
	@DisplayName("CRDTUtility Test Random Int Generator (Exclusive)")
	@CsvSource({
		"1, 3",
		"1, 4",
		"100, 200",
		"1020, 89321"
	})
	void testRadomInt(int a, int b) {
		assertEquals(true, a < CRDTUtility.randomInt(a, b) && CRDTUtility.randomInt(a, b) < b);
	}
	
	@Test
	@DisplayName("CRDTUtility Test Random Bool Gen")
	void testRandomBool() {
		assertEquals(true, !CRDTUtility.randomBool() || CRDTUtility.randomBool());
	}
	
	@Test
	@DisplayName("CRDTUtility Test Gen Start Atom")
	void testGenStartAtom() {
		SequenceAtom seq = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMin()), Clock.counter));
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("CRDTUtility Test Gen Stop Atom")
	void testGenStopAtom() {
		SequenceAtom seq = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMax()), Clock.counter));
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(Short.MAX_VALUE - 1, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("CRDTUtility Test Lseq Gen Start Atom")
	void testGenStartAtomLseq() {
		SequenceAtom seq = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMinLseq()), Clock.counter));
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("CRDTUtility Test Lseq Gen Stop Atom")
	void testGenStopAtomLseq() {
		SequenceAtom seq = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMinLseq()), Clock.counter));
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(31, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("CRDTUtility Test Gen Sequence Atom")
	void testGenSequenceAtom() {
		SequenceAtom seq = null;
		
		try {
			seq = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifier(0, 0L)), Clock.counter));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(31, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
}
