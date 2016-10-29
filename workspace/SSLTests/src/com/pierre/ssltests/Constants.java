package com.pierre.ssltests;

import java.io.File;

public class Constants {
	public static final String JKSKEYSTORE = "D:\\pierre\\github\\JavaMonAmour\\workspace\\SSLTests\\resources\\clientkeystore.jks";
	public static final String JKSKEYSTOREPASSWORD = "blabla"; 
	public static final String JKSCERTACLIAS = "client";
	
	public static final String JKSCACERTSFILE = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar);
	public static final String JKSCACERTSFILEPASSWORD = "changeit";
}
