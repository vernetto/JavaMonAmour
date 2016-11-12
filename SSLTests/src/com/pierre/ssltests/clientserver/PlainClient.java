package com.pierre.ssltests.clientserver;

import java.io.*;
import java.net.*;

public class PlainClient {
	public static void main(String[] args) throws Throwable {
		int port = 32000;
		String host = "localhost";
		Socket s = new Socket(host, port);

		OutputStream out = s.getOutputStream();
		InputStream in = s.getInputStream();

	}

}
