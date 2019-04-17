package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.project.utils.CRDTFileGen;

class CRDTFileGenTest {

	@BeforeAll
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
	@DisplayName("Test File Generation")
	void testFileGen() throws FileNotFoundException {
		
		ArrayList<String> strs = new ArrayList<String>();
		strs.add("Test Line 1");
		strs.add("Test Line 2");
		strs.add("Test Line 3");
		
		File tempFile = new File("output-TEST_FILE.txt");		
		CRDTFileGen.start(strs, "TEST_FILE");
		
		boolean exists = tempFile.exists();
		
		assertAll("File Testing",
			() -> assertEquals(true, exists),
			() -> assertEquals(true, checkFile(tempFile, strs))
		);	
	}
	
	Boolean checkFile(File file, ArrayList<String> strs) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(file);  
		
		int counter = 0;
		while (scanner.hasNextLine()) {
		
			String line = scanner.nextLine();

			if(!line.equals(strs.get(counter))) {
				scanner.close();
				return false;
			}
			
			counter++;
		}
		
		scanner.close();
		return true;
	}
}
