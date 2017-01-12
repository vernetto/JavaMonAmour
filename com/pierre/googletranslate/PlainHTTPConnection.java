package com.pierre.googletranslate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;

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
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PlainHTTPConnection {
	static final String VOICE_EN = "<voice required=\"name = Microsoft Zira Desktop\">";
	static final String VOICE_EN_END = "{{Bookmark=VOICE_EN}}";
	static final String VOICE_DE_END = "{{Bookmark=VOICE_DE}}";
	static final String VOICE_IT_END = "{{Bookmark=VOICE_IT}}";
	static final String VOICE_DE = "<voice required=\"name = Scansoft Steffi_Full_22kHz\">";
	static final String VOICE_IT = "<voice required=\"name = Scansoft Silvia_Dri20_22kHz\">";
	static final String SLOW = "<rate absspeed=\"-2\">";
	static final String NORMAL = "<rate absspeed=\"0\">";
	static final String PAUSE = "{{Pause=1}}";

	public static void main(String[] args) throws Throwable {
		// String fileName =
		// "D:\\pierre\\pictures\\libroindianer\\indianerall.txt";
		Charset defaultCharset = StandardCharsets.UTF_8;
		String inputFileName = "D:\\pierre\\calibre\\Fosco Maraini\\Ore giapponesi (30)\\Ore giapponesi - Fosco Maraini.txt";
		Path outputFilePath = Paths.get(inputFileName + ".out");
		BufferedWriter writer = Files.newBufferedWriter(outputFilePath, defaultCharset);

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(inputFileName), defaultCharset));
		String line;
		int count = 0;

		while ((line = br.readLine()) != null) {
			count++;
			// System.out.println(line);
			String[] splitLines = line.split(Pattern.quote("!?."));
			for (String splitLine : splitLines) {
				if (splitLine.trim().length() > 0) {
					String translation = translateItDe(splitLine);
					translation = PrepareText.transformLatinToUTF8(translation);
					for (int repeat = 0; repeat < 1; repeat++) {
						out(NORMAL, writer);
						out(VOICE_IT, writer);
						out(splitLine, writer);
						out(VOICE_IT_END, writer);
						out(SLOW, writer);
						out(VOICE_DE, writer);
						out(translation, writer);
						out(VOICE_DE_END, writer);
						out(PAUSE, writer);
					}

					Thread.sleep(500);
				}
			}
		}
		System.out.println("count=" + count);

		br.close();
		writer.close();

	}

	private static String translateItDe(String sourceText) {
		String sourceLang = "it";
		String targetLang = "de";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	private static String translateDeEn(String sourceText) {
		String sourceLang = "de";
		String targetLang = "en";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	private static String translateEnDe(String sourceText) {
		String sourceLang = "en";
		String targetLang = "de";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	public static void out(String s, BufferedWriter writer) throws Throwable {
		writer.write(s);
		writer.write('\n');
		System.out.println(s);
	}

	private static String translate(String sourceText, String sourceLang, String targetLang) {
		String matchedString = "";
		try {

			String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + sourceLang + "&tl="
					+ targetLang + "&dt=t&q=" + URLEncoder.encode(sourceText, "UTF-8") + "&ie=UTF-8&oe=UTF-8";
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
			List<String> answers = new ArrayList<String>();
			while (m.find()) {
				String item = m.group(1);
				answers.add(item);
			}
			String[] elements = new String[answers.size()];
			elements = (String[]) answers.toArray(elements);

			for (int i = 0; i < (elements.length - 1) / 2; i++) {
				matchedString += elements[i * 2] + " ";
			}

		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println("ERROR!!! unable to translate " + sourceText);
		}
		return matchedString;
	}

}
