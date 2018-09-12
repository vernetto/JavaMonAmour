package com.pierre.googletranslate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PrepareText {
	public static void main(String[] args) throws Throwable {

		prepare();
	}

	public static void prepare() throws IOException {
		Charset defaultCharset = StandardCharsets.UTF_8;

		Path outputFilePath = Paths.get(PlainHTTPConnection.inputFileNamePrepared);
		BufferedWriter writer = Files.newBufferedWriter(outputFilePath, defaultCharset);
		

		Path fileName = Paths.get(PlainHTTPConnection.getInputFile());
		try (Stream<String> lines = Files.lines(fileName, defaultCharset)) {
			lines.forEachOrdered(s -> {
				try {
					writer.write(filter(s));
					writer.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		writer.close();
	}

	private static String filter(String s) {
		String result = "";
		try {
			s = s.trim();
			if (s.matches("[0-9]+") || s.length() == 0)
				return result;
			
			result = s;
			result = result.replace(",", "\n").replace("“", " ").replace("”", " ").replace(" Ph.D. ", " PhD ").replace(" B.A. ", " BA ").replace(" F. ", " F ").replace("»", " ").replace("«", " ");
			result = result.replace("\r", "\n").replace("\n\n", "\n");
			
			result = result.replace(" U.S. ", " USA ").replace("’", " ");
			result = result.replace(".", ".\n").replace("?", "?\n").replace("!", "!\n").replace(":", ":\n")
					.replace(";", ";\n").replace("\"", " ").replace('‘', ' ');
			result = result.replace("\n ", "\n").replace("«", " ").replace("»", " ").replace("“", " ").replace("”", " ").replace("—", " ");

		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;

	}

	public static String transformLatinToUTF8(String s) {
		String returnValue = "";
		try {
			returnValue = s.replace("Ãœ", "Ü").replace("Ã¼", "ü").replace("Ã¤", "ä").replace("Ã¶", "ö")
					.replace("ÃŸ", "ss").replace("Ã©", "é").replace("Ã½", "ý").replace("Ã„", "Ë").replace("Ã¡", "á")
					.replace("Ã³", "ó").replace("Ã²", "ò").replace("Ã¶", "ö").replace("Ã³", "ó").replace("Ã–", "Ö")
					.replace("Ã¨", "è").replace("Ã­", "í").replace("Ã ", "à").replace("Ãª", "ê").replace("Ã¢", "â");
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return returnValue;

	}
}
