package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.datatypes.Identifier;
import com.project.datatypes.Position;

class PositionTest {
	
	private static List<Identifier> list = new ArrayList<Identifier>();
	private static Position pos = new Position(new Identifier(44, 1L));	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		list.add(new Identifier(44, 1L));
		list.add(new Identifier(55, 2L));
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
	@DisplayName("Test Position Gen")
	void testGen() {
		assertNotNull(pos);
	}
	
	@Test
	@DisplayName("Test Positon Copy")
	void testPositionCopy() {
		
		Position pos = new Position(list);
		List<Identifier> copy = pos.copy();
		
		Boolean check = true;
		
		for(int i = 0; i < pos.ids.size(); i++) {
			
			Identifier posId = pos.ids.get(i);
			Identifier copyId = copy.get(i);
			
			if(!(posId.position == copyId.position && posId.siteId == copyId.siteId)){
				check = false; 
				break;
			}
		}
		
		assertTrue(check, "Position Copy Failed");
	}
	
	@Test
	@DisplayName("Test Splice Copy")
	void testSpliceCopy() {
		
		Position pos = new Position(list);
		int before = pos.ids.size();
		
		Position posNew = new Position(pos.copy(1));
		int after = posNew.ids.size();
		
		Identifier id = posNew.ids.get(0);
		
		assertAll("Splice Copy",
			() -> assertTrue(before != after),
			() -> assertEquals(true, before == 2),
			() -> assertEquals(true, after == 1),
			() -> assertEquals(true, id.position == 55 && id.siteId == 2L)
		);
	}
	
	@Test
	@DisplayName("Test Splice Copy 2")
	void testSpliceCopy2() {
		
		Position pos = new Position(list);
		int before = pos.ids.size();
		
		Position posNew = new Position(pos.copy(0));
		int after = posNew.ids.size();
		
		Identifier id = posNew.ids.get(0);
		
		assertAll("Splice Copy",
			() -> assertEquals(true, before == 2),
			() -> assertEquals(true, after == 2),
			() -> assertEquals(true, id.position == 44 && id.siteId == 1L)
		);
	}
}
