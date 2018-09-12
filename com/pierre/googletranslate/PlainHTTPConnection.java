package com.pierre.googletranslate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFrame;
import java.util.concurrent.ThreadLocalRandom;

public class PlainHTTPConnection {

	enum FROMTOLANGUAGE {
		ENTODE, DETOEN, DETOEN_DE1, ENTODE_DE1, ITTODE, ITTODE_DE1, ESTODE, ESTODE_DE1, FRTODE, FRTODE_DE1
	}

	
	public static final int WAIT_TIME = 300;
	public static final int SECOND = 1000; 
	public static final int MINUTE = 60 * SECOND;
	public static final int BIG_WAIT_TIME = MINUTE;
	public static final int HUGE_WAIT_TIME = 10 * MINUTE;
	static final ConversionType conversionType = ConversionType.IT_DE;
	static final int repeatCount = 1;
	static final String VOICE_EN = "<voice required=\"name = Microsoft Zira Desktop\">";
	static final String VOICE_DE = "<voice required=\"name = Scansoft Steffi_Full_22kHz\">";
	static final String VOICE_IT = "<voice required=\"name = Scansoft Silvia_Dri20_22kHz\">";
	static final String VOICE_SP = "<voice required=\"name = Scansoft Isabel_Full_22kHz\">";
	static final String VOICE_FR = "<voice required=\"name = Scansoft Virginie_Dri20_22kHz\">";
	static final String VOICE_FR_2 = "<voice required=\"name = Microsoft Hortense Desktop\">";

	static final String VOICE_EN_END = "{{Bookmark=VOICE_EN}}";
	static final String VOICE_DE_END = "{{Bookmark=VOICE_DE}}";
	static final String VOICE_IT_END = "{{Bookmark=VOICE_IT}}";
	static final String VOICE_SP_END = "{{Bookmark=VOICE_SP}}";
	static final String VOICE_FR_END = "{{Bookmark=VOICE_FR}}";

	static final String SPEED_SLOW = "<rate absspeed=\"-2\">";
	static final String SPEED_NORMAL = "<rate absspeed=\"0\">";
	static final String SPEED_FAST = "<rate absspeed=\"2\">";
	static final String PAUSE = "{{Pause=0}}";

	static final FROMTOLANGUAGE language = FROMTOLANGUAGE.ITTODE_DE1;

	private static String inputFileNameOriginal = "D:\\pierre\\calibre\\Alberto Bagnai\\Il Tramonto Dell'Euro. Come E Perch (250)\\Il Tramonto Dell'Euro. Come E P - Alberto Bagnai.txt";
	public static String outputFileName = inputFileNameOriginal + ".transout";
	public static String outputFileNameTransoutdestination = inputFileNameOriginal + ".transoutdestination";
	public static String inputFileNamePrepared = inputFileNameOriginal + ".prepared";

	// public static TranslationListener listener = null;

	public static void main(String[] args) throws Throwable {
		PrepareText.prepare();
		PlainHTTPConnection.translate();
	}

	public static void setInputFile(String filename) {
		inputFileNameOriginal = filename;
		outputFileName = inputFileNameOriginal + ".transout";
		outputFileNameTransoutdestination = inputFileNameOriginal + ".transoutdestination";
		inputFileNamePrepared = inputFileNameOriginal + ".prepared";
	}

	public static String getInputFile() {
		return inputFileNameOriginal;
	}

	public static void translate() throws IOException, FileNotFoundException, Throwable, InterruptedException {
		Charset defaultCharset = StandardCharsets.UTF_8;
		long numOfLines;

		try (Stream<String> lines = Files.lines(Paths.get(inputFileNamePrepared), defaultCharset)) {
			numOfLines = lines.count();
		}
		Path outputFilePath = Paths.get(outputFileName);
		BufferedWriter writer = Files.newBufferedWriter(outputFilePath, defaultCharset);
		Path outputFilePathTransoutdestination = Paths.get(outputFileNameTransoutdestination);
		BufferedWriter writerTransoutdestination = Files.newBufferedWriter(outputFilePathTransoutdestination,
				defaultCharset);

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(inputFileNamePrepared), defaultCharset));
		String line;
		int count = 0;

		while ((line = br.readLine()) != null) {
			count++;
			if (count % 10 == 0) {
				System.out.println("sleeping for " + BIG_WAIT_TIME + " ms");
				Thread.sleep(BIG_WAIT_TIME);
			}
			System.out.println(line);
			String[] splitLines = line.split(Pattern.quote("!?."));
			for (String originalLine : splitLines) {
				if (originalLine.trim().length() > 0) {
					switch (language) {
					case ENTODE:
						enToDe(writer, writerTransoutdestination, originalLine);
						break;
					case ENTODE_DE1:
						enToDeDE1(writer, writerTransoutdestination, originalLine);
						break;
					case DETOEN:
						deToEn(writer, writerTransoutdestination, originalLine);
						break;
					case DETOEN_DE1:
						deToEnDE1(writer, writerTransoutdestination, originalLine);
						break;
					case ITTODE:
						itToDe(writer, writerTransoutdestination, originalLine);
						break;
					case ITTODE_DE1:
						itToDeDE1(writer, writerTransoutdestination, originalLine);
						break;
					case ESTODE:
						esToDe(writer, writerTransoutdestination, originalLine);
						break;
					case ESTODE_DE1:
						esToDeDE1(writer, writerTransoutdestination, originalLine);
						break;
					case FRTODE:
						frToDe(writer, writerTransoutdestination, originalLine);
						break;
					case FRTODE_DE1:
						frToDeDE1(writer, writerTransoutdestination, originalLine);
						break;

					default:
						break;
					}

					String message = String.format("%1$d lines out of %2$d, missing time %3$d s", count, numOfLines,
							(numOfLines - count) * WAIT_TIME / 1000);
					// if (listener != null) listener.handleEvent(message, (int) ((100 * count) /
					// numOfLines) );
					System.out.println(message);

					Thread.sleep(WAIT_TIME);
				}
			}
		}
		System.out.println("count=" + count);

		br.close();
		writer.close();
	}

	private static void enToDe(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateEnDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_FAST, writer);
			out(VOICE_EN, writer);
			out(originalLine, writer);
			out(VOICE_EN_END, writer);
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void enToDeDE1(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateEnDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(SPEED_FAST, writer);
			out(VOICE_EN, writer);
			out(originalLine, writer);
			out(VOICE_EN_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void deToEn(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateDeEn(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_FAST, writer);
			out(VOICE_EN, writer);
			out(translatedLine, writer);
			out(VOICE_EN_END, writer);
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(originalLine, writer);
			out(VOICE_DE_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void deToEnDE1(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateDeEn(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(originalLine, writer);
			out(VOICE_DE_END, writer);
			out(PAUSE, writer);
			out(SPEED_FAST, writer);
			out(VOICE_EN, writer);
			out(translatedLine, writer);
			out(VOICE_EN_END, writer);
		}
		out(translatedLine, writer2);
	}

	private static void itToDe(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateItDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_FAST, writer);
			out(VOICE_IT, writer);
			out(originalLine, writer);
			out(VOICE_IT_END, writer);
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void itToDeDE1(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateItDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(SPEED_FAST, writer);
			out(VOICE_IT, writer);
			out(originalLine, writer);
			out(VOICE_IT_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void esToDe(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateEsDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_FAST, writer);
			out(VOICE_SP, writer);
			out(originalLine, writer);
			out(VOICE_SP_END, writer);
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void esToDeDE1(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateEsDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(SPEED_FAST, writer);
			out(VOICE_SP, writer);
			out(originalLine, writer);
			out(VOICE_SP_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void frToDe(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateFrDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_FAST, writer);
			out(VOICE_FR, writer);
			out(originalLine, writer);
			out(VOICE_FR_END, writer);
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static void frToDeDE1(BufferedWriter writer, BufferedWriter writer2, String originalLine) throws Throwable {
		String translatedLine = translateFrDe(originalLine);
		translatedLine = PrepareText.transformLatinToUTF8(translatedLine);
		for (int repeat = 0; repeat < repeatCount; repeat++) {
			out(SPEED_NORMAL, writer);
			out(VOICE_DE, writer);
			out(translatedLine, writer);
			out(VOICE_DE_END, writer);
			out(SPEED_FAST, writer);
			out(VOICE_FR, writer);
			out(originalLine, writer);
			out(VOICE_FR_END, writer);
			out(PAUSE, writer);
		}
		out(translatedLine, writer2);
	}

	private static String translateItDe(String sourceText)  throws Throwable {
		String sourceLang = "it";
		String targetLang = "de";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	private static String translateEsDe(String sourceText)  throws Throwable {
		String sourceLang = "es";
		String targetLang = "de";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	private static String translateFrDe(String sourceText)  throws Throwable {
		String sourceLang = "fr";
		String targetLang = "de";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	private static String translateEnRu(String sourceText)  throws Throwable {
		String sourceLang = "en";
		String targetLang = "ru";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	private static String translate(String sourceText)  throws Throwable {
		if (conversionType.equals(ConversionType.DE_EN))
			return translateDeEn(sourceText);
		if (conversionType.equals(ConversionType.EN_DE))
			return translateEnDe(sourceText);
		if (conversionType.equals(ConversionType.IT_DE))
			return translateItDe(sourceText);
		throw new IllegalArgumentException("unsupported conversion type " + conversionType);
	}

	private static String translateDeEn(String sourceText)  throws Throwable {
		String sourceLang = "de";
		String targetLang = "en";
		String matchedString = translate(sourceText, sourceLang, targetLang);
		return matchedString;
	}

	private static String translateEnDe(String sourceText)  throws Throwable {
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

	private static String translate(String sourceText, String sourceLang, String targetLang) throws Throwable {
		String matchedString = "";
		String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + sourceLang + "&tl="
				+ targetLang + "&dt=t&q=" + URLEncoder.encode(sourceText, "UTF-8") + "&ie=UTF-8&oe=UTF-8";

		// https://translate.google.com/translate_a/single?client=t&sl=it&tl=de&hl=en&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=0&tk=129376.499397&q=buongiorno
		// String url = "https://translate.google.com/translate_a/single?client=t&sl=" +
		// sourceLang + "&tl=" + targetLang +
		// "&hl=en&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&ssel=0&tsel=0&kc=0&tk=129376.499397&q="
		// + URLEncoder.encode(sourceText, "UTF-8");
		int randomNum = ThreadLocalRandom.current().nextInt(50, 60 + 1);
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:" + randomNum + ".0) Gecko/20100101 Firefox/"
				+ randomNum + ".0";
		boolean connected = false;
		int errorcount = 0;
		String result = "";
		while (connected != true) {
			try {
				URL theURL = new URL(url);
				URLConnection connection = theURL.openConnection();
				connection.setRequestProperty("User-Agent", userAgent);
				InputStream inputStream = connection.getInputStream();
				connected =true;
				result = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel()
						.collect(Collectors.joining("\n"));
				inputStream.close();
			}
			catch (IOException e) {
				errorcount++;
				System.out.println("ERROR at " + new Date() + " " + e.getMessage() + " " + " waiting for " + errorcount * HUGE_WAIT_TIME / MINUTE + " minutes");
				Thread.sleep(errorcount * HUGE_WAIT_TIME);
			}
		}
		

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
		return matchedString;
	}

}
