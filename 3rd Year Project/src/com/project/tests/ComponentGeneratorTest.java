package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.crdt.ComponentGenerator;
import com.project.datatypes.AtomIdentifier;
import com.project.datatypes.Identifier;
import com.project.datatypes.Position;
import com.project.datatypes.Sequence;
import com.project.datatypes.SequenceAtom;

class ComponentGeneratorTest {

	private static ArrayList<Identifier> list = new ArrayList<Identifier>();
	private Boolean check = false;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		list.add(new Identifier(44, 1L));
		list.add(new Identifier(55, 2L));
	}
	
	@Test
	@DisplayName("Test Gen Identifier")
	void testGenIdentifier() throws Exception {
		
		Identifier id = ComponentGenerator.genIdentifier(2, 2L);
		
		assertAll("Identifier Properties",
			() -> assertNotNull(id),
			() -> assertTrue(id.position == 2),
			() -> assertTrue(id.siteId == 2)
		);
	}
	
	@Test
	@DisplayName("Test Gen Identifier Max")
	void testGenIdentifierMax() throws Exception {
		
		Identifier id = ComponentGenerator.genIdentifierMax();
		
		assertAll("Identifier Properties",
			() -> assertNotNull(id),
			() -> assertTrue(id.position == Short.MAX_VALUE - 1),
			() -> assertTrue(id.siteId == 0)
		);
	}
	
	@Test
	@DisplayName("Test Gen Identifier Min")
	void testGenIdentifierMin() throws Exception {
		
		Identifier id = ComponentGenerator.genIdentifierMin();
		
		assertAll("Identifier Properties",
			() -> assertNotNull(id),
			() -> assertTrue(id.position == 0),
			() -> assertTrue(id.siteId == 0)
		);
	}
	
	@Test
	@DisplayName("Test Gen Identifier Min LSEQ")
	void testGenIdentifierMinLseq() throws Exception {
		
		Identifier id = ComponentGenerator.genIdentifierMinLseq();
		
		assertAll("LSEQ Id Properties",
			() -> assertNotNull(id),
			() -> assertTrue(id.position == 0, "Identifier LSEQ Gen gave incorrect position"),
			() -> assertTrue(id.siteId == 0, "Identifier LSEQ Gen gave incorrect position")
		);
	}
	
	@Test
	@DisplayName("Test Gen Identifier Max LSEQ")
	void testGenIdentifierMaxLseq() throws Exception {
		
		Identifier id = ComponentGenerator.genIdentifierMaxLseq();
		
		assertAll("LSEQ Id Properties",
			() -> assertNotNull(id),
			() -> assertTrue(id.position == 31, "Identifier LSEQ Gen gave incorrect position"),
			() -> assertTrue(id.siteId == 0, "Identifier LSEQ Gen gave incorrect position")
		);
	}
	
	@Test
	@DisplayName("Test Gen Identifiers Throw Error on Illegal Position Values")
	void testGenIdentifierThrows() {
		
		assertAll("Gen Identifier Throws Error",
				
			() -> assertThrows(Exception.class, () -> {
					ComponentGenerator.genIdentifier(0, 1L);
					}, "Component Gen Failed to Fail"),
			() -> assertThrows(Exception.class, () -> {
					ComponentGenerator.genIdentifier(Integer.MAX_VALUE, 1L);
					}, "Component Gen Failed to Fail"),
			() -> assertThrows(Exception.class, () -> {
					ComponentGenerator.genIdentifierLseq(0, 1L);
					}, "Component Gen Failed to Fail")
		);		
	}
	
	@Test
	@DisplayName("Test Gen Identifier LSEQ")
	void testGenIdentifierLseq() throws Exception {
		
		Identifier id = ComponentGenerator.genIdentifierLseq(3, 2L);
		
		assertAll("LSEQ Id Properties",
			() -> assertNotNull(id),
			() -> assertTrue(id.position == 3, "Identifier LSEQ Gen gave incorrect position"),
			() -> assertTrue(id.siteId == 2, "Identifier LSEQ Gen gave incorrect position")
		);
	}
	
	@Test
	@DisplayName("Test Gen Position Returns New Position")
	void testGenPosition() {
		
		Position pos = null;
		pos = ComponentGenerator.genPosition();
		
		assertNotNull(pos);
	}
	
	@Test
	@DisplayName("Test Gen Position with Identifier")
	void testGenPositionIdentifier() {
		
		Position pos = ComponentGenerator.genPosition(new Identifier(1, 1L));		
		assertAll("Position Properties",
			() -> assertNotNull(pos),
			() -> assertTrue(pos.ids.get(0).position == 1),
			() -> assertTrue(pos.ids.get(0).siteId == 1)
		);
	}
	
	@Test
	@DisplayName("Test Gen Position with List of Identifier")
	void testGenPositionIdentifierList() {
		
		Position pos = ComponentGenerator.genPosition(list);		
		check = true;
		
		for(int i = 0; i < pos.ids.size(); i++) {
			
			Identifier posId = pos.ids.get(i);
			Identifier copyId = list.get(i);
			
			if(!(posId.position == copyId.position && posId.siteId == copyId.siteId)){
				check = false; 
				break;
			}
		}
		
		assertAll("Position Properties",
			() -> assertNotNull(pos),
			() -> assertEquals(true, check, "Position Gen With list Failed")
		);
	}
	
	@Test
	@DisplayName("Test Atom Identifier Gen With Clock")
	void testGenAtomIdentifier() {
		
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(321);		
		assertTrue(atom.clock == 321, "Atom Identifier Clock Gen Failed");
	}
	
	@Test
	@DisplayName("Test Atom Identifier Gen with Position and Clock")
	void testGenAtomIdentifierPosClock() {
		
		AtomIdentifier atom = ComponentGenerator.genAtomIdentifier(new Position(new Identifier(1, 1L)), 321);		
		
		assertAll("Atom Properties",
			() -> assertNotNull(atom),
			() -> assertTrue(atom.position.ids.size() == 1, "Position Gen Failed"),
			() -> assertTrue(atom.position.ids.get(0).position == 1, "Failed"),
			() -> assertTrue(atom.position.ids.get(0).siteId == 1, "Failed"),
			() -> assertTrue(atom.clock == 321, "Failed")
		);
	}
	
	@Test
	@DisplayName("Test Sequence Atom Gen with Atom Id")
	void testGenSequenceWithAtom() {
		AtomIdentifier atom = new AtomIdentifier(new Position(new Identifier(1, 1L)), 321);
		SequenceAtom seqAt = ComponentGenerator.genSequenceAtom(atom);
		
		assertAll("Atom Properties",
			() -> assertNotNull(seqAt),
			() -> assertTrue(seqAt.atomId.position.ids.size() == 1, "Position Gen Failed"),
			() -> assertTrue(seqAt.atomId.position.ids.get(0).position == 1, "Failed"),
			() -> assertTrue(seqAt.atomId.position.ids.get(0).siteId == 1, "Failed"),
			() -> assertTrue(seqAt.atomId.clock == 321, "Failed")
		);
	}
	
	@Test
	@DisplayName("Test Sequence Atom Gen with Atom Id and Message")
	void testGenSequenceWithAtomMessage() {
		AtomIdentifier atom = new AtomIdentifier(new Position(new Identifier(1, 1L)), 321);
		SequenceAtom seqAt = ComponentGenerator.genSequenceAtom(atom, "message");
		
		assertAll("Atom Properties",
			() -> assertNotNull(seqAt),
			() -> assertTrue(seqAt.atomId.position.ids.size() == 1, "Position Gen Failed"),
			() -> assertTrue(seqAt.atomId.position.ids.get(0).position == 1, "Failed"),
			() -> assertTrue(seqAt.atomId.position.ids.get(0).siteId == 1, "Failed"),
			() -> assertTrue(seqAt.atomId.clock == 321, "Failed"),
			() -> assertTrue(seqAt.message.equals("message"))
		);
	}
	
	@Test
	@DisplayName("Test Sequence Gen")
	void testGenSequence() {
		
		Sequence seq = ComponentGenerator.genSequence();
		
		assertAll("Sequence Properties",
			() -> assertNotNull(seq),
			() -> assertTrue(seq.arr.size() == 0),
			() -> assertEquals("Empty Sequence", seq.toString(), "toString Failed")
		);
	}
	
	@Test
	@DisplayName("Test Gen Sequence With Sequence Atom")
	void testGenSequenceSeqAtom() {
		
		Sequence seq = ComponentGenerator.genSequence(new SequenceAtom(new AtomIdentifier(1), "message"));
		
		assertAll("Sequence Properties",
			() -> assertNotNull(seq),
			() -> assertTrue(seq.arr.get(0).message.equals("message"), "Sequence Gen With Message Failed"),
			() -> assertTrue(seq.arr.get(0).atomId.clock == 1, "Sequence Gen With Clock Failed")
		);
	}
}
