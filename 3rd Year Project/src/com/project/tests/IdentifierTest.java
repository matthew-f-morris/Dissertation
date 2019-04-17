package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.datatypes.Identifier;

class IdentifierTest {
	
	private static Identifier id = new Identifier(24321, 19L);
	
	@Test
	@DisplayName("Test Identifier Gen")
	void testIdentifierGen() {
		
		assertNotNull(id);
	}
	
	@Test
	@DisplayName("Test Properties")
	void testProperties() {
		
		assertAll("Properties",
			() -> assertEquals(24321, id.position),
			() -> assertEquals(19L, id.siteId)
		);
	}
	
	@Test
	@DisplayName("Test Clone")
	void testClone() {
		
		Identifier clone = id.clone();
		
		assertAll("Clone",
			() -> assertTrue(id.position == clone.position, "Clone failed"),
			() -> assertTrue(id.siteId == clone.siteId, "Clone failed"),
			() -> assertTrue(!id.equals(clone))
		);
	}
	
	@Test
	@DisplayName("Test To String")
	void testToString() {		
		assertEquals("[24321, 19]", id.toString());
	}
}
