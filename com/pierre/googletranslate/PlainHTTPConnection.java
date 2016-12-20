package com.pierre.googletranslate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// REQUEST:
// (Request-Line)	GET /translate_a/single?client=gtx&sl=en&tl=de&dt=t&q=how+are+you HTTP/1.1
// Host	translate.googleapis.com
// User-Agent	Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0
// Accept	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
// Accept-Language	en-US,en;q=0.5
// Accept-Encoding	gzip, deflate, br
// Connection	keep-alive
// Upgrade-Insecure-Requests	1

// RESPONSE
// (Status-Line)	HTTP/2.0 200 OK
// Access-Control-Allow-Origin	*
// X-Frame-Options	SAMEORIGIN
// Date	Sun, 11 Dec 2016 14:26:22 GMT
// Pragma	no-cache
// Expires	Fri, 01 Jan 1990 00:00:00 GMT
// Cache-Control	no-cache, no-store, must-revalidate
// Content-Type	application/json; charset=UTF-8
// X-Content-Type-Options	nosniff
// Content-Disposition	attachment; filename="f.txt"
// Content-Encoding	gzip
// Server	HTTP server (unknown)
// Content-Length	67
// X-XSS-Protection	1; mode=block
// Alt-Svc	quic=":443"; ma=2592000; v="35,34"
// X-Firefox-Spdy	h2

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PlainHTTPConnection {
	static final String VOICE_EN = "<voice required=\"name = Microsoft Zira Desktop\">";
	static final String VOICE_EN_END = "{{Bookmark=VOICE_EN}}";
	static final String VOICE_DE_END = "{{Bookmark=VOICE_DE}}";
	static final String VOICE_DE = "<voice required=\"name = Scansoft Steffi_Full_22kHz\">";

	public static void main(String[] args) throws Throwable {
		String fileName = "D:\\pierre\\pictures\\libroindianer\\indianerall.txt";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "Cp1252"));
		String line;
		int count = 0;
		
		while ((line = br.readLine()) != null) {
			count++;
			// System.out.println(line);
			String[] splitLines = line.split(Pattern.quote("!?."));
			for (String splitLine : splitLines) {
				if (splitLine.trim().length() > 0) {
					String translation = translateDeEn(splitLine);
					System.out.println(VOICE_DE);
					System.out.println(splitLine);
					System.out.println(VOICE_DE_END);
					System.out.println(VOICE_EN);
					System.out.println(translation);
					System.out.println(VOICE_EN_END);
					System.out.println(VOICE_DE);
					System.out.println(splitLine);
					System.out.println(VOICE_DE_END);
					Thread.sleep(500);
				}
			}
		}
		System.out.println("count=" + count);
		;
		br.close();

	}

	private static String translateDeEn(String sourceText) {
		String matchedString = "";
		try {
		String https_url = "https://www.google.com/";
		String sourceLang = "de";
		String targetLang = "en";
		String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + sourceLang + "&tl="
				+ targetLang + "&dt=t&q=" + URLEncoder.encode(sourceText, "UTF-8");
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0";
		URL theURL = new URL(url);
		URLConnection connection = theURL.openConnection();
		connection.setRequestProperty("User-Agent", userAgent);
		// System.out.println(connection);
		InputStream inputStream = connection.getInputStream();

		String result = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel()
				.collect(Collectors.joining("\n"));
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(result);
		
		if (m.find()) {
			matchedString = m.group(1);
		}
		}
		catch (Throwable t) {
			t.printStackTrace();
			System.out.println("ERROR!!! unable to translate " + sourceText);
		}
		return (matchedString);
	}

}
