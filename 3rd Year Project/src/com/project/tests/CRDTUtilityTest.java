package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.project.clock.Clock;
import com.project.crdt.ComponentGenerator;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.SequenceAtom;
import com.project.utils.CRDTUtility;

class CRDTUtilityTest {

	private static Clock clock;
	
	@BeforeEach
	void beforeEach() {
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
	@DisplayName("Test Random Bool Gen")
	void testRandomBool() {
		assertEquals(true, CRDTUtility.randomBool() || CRDTUtility.randomBool());
	}
	
	@Test
	@DisplayName("Test Gen Start Atom")
	void testGenStartAtom() {
		SequenceAtom seq = CRDTUtility.genStartAtom();
		
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("Test Gen Stop Atom")
	void testGenStopAtom() {
		SequenceAtom seq1 = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMax()), Clock.counter));
		SequenceAtom seq = CRDTUtility.genStopAtom();
		
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(Short.MAX_VALUE - 1, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("Test Lseq Gen Start Atom")
	void testGenStartAtomLseq() {
		SequenceAtom seq1 = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMinLseq()), Clock.counter));
		SequenceAtom seq = CRDTUtility.genStartAtomLseq();
				
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("Test Lseq Gen Stop Atom")
	void testGenStopAtomLseq() {
		SequenceAtom seq = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMaxLseq()), Clock.counter));
		SequenceAtom seq1 = CRDTUtility.genStopAtomLseq();	
	
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(0, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(31, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0, seq.atomId.position.ids.get(0).siteId)
		);		
	}
	
	@Test
	@DisplayName("Test Gen Sequence Atom")
	void testGenSequenceAtom() throws Exception {		
		
		clock = new Clock(50);
		SequenceAtom seq = CRDTUtility.genSequenceAtom("message", ComponentGenerator.genPosition(ComponentGenerator.genIdentifier(10, 0L)));
				
		assertAll("properties",
			() -> assertNotNull(seq, "Sequence Atom was not generated"),
			() -> assertEquals(50, seq.atomId.clock, "Clock was incorrect on generation"),
			() -> assertEquals(10, seq.atomId.position.ids.get(0).position),
			() -> assertEquals(0L, seq.atomId.position.ids.get(0).siteId),
			() -> assertEquals("message", seq.message)
		);		
	}
	
	@Test
	@DisplayName("Test Insert Sequence Atom")
	void testInsertSequenceAtom() throws Exception {
		
		SequenceAtom start = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMin()), Clock.counter));
		SequenceAtom stop = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMax()), Clock.counter));
		SequenceAtom add = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifier(10, 0L)), Clock.counter), "message");
		
		ArrayList<SequenceAtom> seq = new ArrayList<SequenceAtom>();
		seq.add(start);
		seq.add(stop);
		
		CRDTUtility.insertSequenceAtom(seq, add);
		
		assertEquals(true, seq.get(1).equals(add), "Insertion Failed");
	}
	
	@Test
	@DisplayName("Test Insert Throws Exception")
	void testInsertThrowException() throws Exception {
		
		SequenceAtom start = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMin()), Clock.counter));
		SequenceAtom stop = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifierMax()), Clock.counter));
		SequenceAtom add = ComponentGenerator.genSequenceAtom(ComponentGenerator.genAtomIdentifier(ComponentGenerator.genPosition(ComponentGenerator.genIdentifier(10, 0L)), Clock.counter), "message");
		
		ArrayList<SequenceAtom> seq = new ArrayList<SequenceAtom>();
		seq.add(stop);
		seq.add(start);		
		
		assertThrows(Exception.class, () -> {
			CRDTUtility.insertSequenceAtom(seq, add);
		}, "Insertion Failed");
	}
	
	@Test
	@DisplayName("Test Compare Identifier")
	void testCompareidentifier() {
		
		Identifier id1 = new Identifier(1, 0L);
		Identifier id2 = new Identifier(2, 0L);
		
		Identifier id3 = new Identifier(1, 1L);
		Identifier id4 = new Identifier(2, 1L);		
		
		assertAll("Comparing Identifiers",
			() -> assertEquals(0, CRDTUtility.compareIdentifier(id1, id1), "Failed 1"),
			() -> assertEquals(1, CRDTUtility.compareIdentifier(id2, id1), "Failed 2"),
			() -> assertEquals(1, CRDTUtility.compareIdentifier(id3, id1), "Failed 3"),
			() -> assertEquals(-1, CRDTUtility.compareIdentifier(id3, id4), "Failed 4"),
			() -> assertEquals(-1, CRDTUtility.compareIdentifier(id2, id4), "Failed 5")
		);
	}
	
	@Test
	@DisplayName("Test Compare Position")
	void testComparePosition() {
		
		Identifier id1 = new Identifier(1, 0L);
		Identifier id2 = new Identifier(2, 5L);
		
		Identifier id3 = new Identifier(1, 1L);
		Identifier id4 = new Identifier(2, 6L);	
		
		Position pos1 = new Position(new ArrayList<Identifier>(Arrays.asList(id1, id2)));
		Position pos2 = new Position(new ArrayList<Identifier>(Arrays.asList(id3, id4)));
		Position pos3 = new Position();
		Position pos4 = new Position();
		
		assertAll("Comparing Position",
			() -> assertEquals(0, CRDTUtility.comparePosition(pos3, pos4), "Position Lengths were not the same"),
			() -> assertEquals(-1, CRDTUtility.comparePosition(pos3, pos2), "Position P should be length 0"),
			() -> assertEquals(1, CRDTUtility.comparePosition(pos1, pos4), "Position Q should be length 0")
		);		
	}
	
	@Test
	@DisplayName("Test Get Prefix")
	void testPrefix() {
		
		Identifier id1 = new Identifier(8394, 0L);
		Identifier id2 = new Identifier(12389, 5L);
		Position pos1 = new Position(new ArrayList<Identifier>(Arrays.asList(id1, id2)));
		
		assertEquals(8394, CRDTUtility.prefix(pos1), "Incorrect Prefix");
	}
	
	@Test
	@DisplayName("Test Base Calculation")
	void testBase() {
		
		assertAll("Base",
			() -> assertEquals(0, CRDTUtility.base(-1)),
			() -> assertEquals(32, CRDTUtility.base(0)),
			() -> assertEquals(64, CRDTUtility.base(1)),
			() -> assertEquals(128, CRDTUtility.base(2))
		);
	}
}
