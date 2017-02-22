package com.bzn.fundamental.utils;

import java.util.UUID;

/**
 * 
 * 生成8位随机ID
 * @author cc
 *
 * 2015年10月12日下午5:47:48
 *
 * 
 */
public class RandomIdUtil {
	
	private static RandomIdUtil u = null;
	
	
	private String version = null;
	
	
	
	
	
	
	private RandomIdUtil(){
		
	};
	
	
	public static synchronized RandomIdUtil getIns(){
		if(u == null){
			u = new RandomIdUtil();
		}
		return u;
	}
	
	
	
	private final static String[] CHARS = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" };  
	
	
	public final static String[] CHARTS = new String[] { "!", "#", "$", "%", "&", "'",
		"(", ")", "*", "+", ",", "-", ".", "/", "0", "1", "2", "3",
		"4", "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?",
		"@", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
		"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
		"X", "Y", "Z", "[", "]", "^", "_", "`"
	};
	
	
	
	/**
	 * 获取8位随机英语和数字
	 * @author cc
	 *
	 * 2015年10月16日下午6:30:30
	 *
	 * @return
	 */
	public String getRandomId() {  
	    StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    for (int i = 0; i < 8; i++) {  
	        String str = uuid.substring(i * 4, i * 4 + 4);  
	        int x = Integer.parseInt(str, 16);  
	        shortBuffer.append(CHARS[x % 0x3E]);  
	    }  
	    return shortBuffer.toString();  
	  
	}
	
	/**
	 * 获取16位随机数
	 * @author cc
	 *
	 * 2015年10月16日下午6:29:39
	 *
	 * @return
	 */
	public String getNumberId() {  
		StringBuffer shortBuffer = new StringBuffer();  
		String uuid = UUID.randomUUID().toString().replace("-", "");  
		for (int i = 0; i < 8; i++) {  
			String str = uuid.substring(i * 4, i * 4 + 4);  
			int x = Integer.parseInt(str, 16);  
			shortBuffer.append((int)CHARTS[x % 0x3E].toCharArray()[0]);  
		}  
		return shortBuffer.toString();  
		
	}

	
	
	public String getVersion(){
		if(version == null){
			version = getNumberId();
		}
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}
	
	
	
	
}
