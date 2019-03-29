package com.project.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class MsgGen {
	
	private static long leftLimit = 1L;
    private static long rightLimit = 10L;
    private static Random random = new Random();
    
    private static List<String> lines;
	
    static {	
    	
    	try {
			lines = Files.readAllLines(Paths.get("C:\\Users\\matth\\Dissertation\\CRDT\\src\\com\\project\\util\\msgs.txt"), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public static long getSite() {		
	    return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	}
	
	public static String getMsg() {
		return lines.get(random.nextInt(lines.size()));
	}	
}
