package com.pierre.ssltests.clientserver;

import java.io.*;
import java.net.*;

public class PlainServer {
	public static void main(String[] args) throws Throwable {
		int port = 32000;
	
		ServerSocket s = new ServerSocket(port);
	    Socket c = s.accept();
	    OutputStream out = c.getOutputStream();
	    InputStream in = c.getInputStream();

	}

}
