package com.pierre.googletranslate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LatinToUTF8 {
	public static void main(String[] args) throws Throwable {
		String inputFileName = "D:\\pierre\\deutsch\\odisseaita.txt.out.out";
		Path outputFilePath = Paths.get(inputFileName + ".out");
		BufferedWriter writer = Files.newBufferedWriter(outputFilePath);

		Path fileName = Paths.get(inputFileName);
		Charset defaultCharset = StandardCharsets.UTF_8;
		try (Stream<String> lines = Files.lines(fileName, defaultCharset)) {
			lines.forEachOrdered(s -> filter(s, writer));
		}
		writer.close();
	}

	private static void filter(String s, BufferedWriter writer) {
		try {
			s = s.replace("Ãœ", "Ü").replace("Ã¼", "ü").replace("Ã¤",
					"ä".replace("Ã¶", "ö").replace("ÃŸ", "ss").replace("Ã©", "é").replace("Ã½", "ý").replace("Ã„", "Ë")
							.replace("Ã¡", "á").replace("Ã³", "ó").replace("Ã²", "ò").replace("Ã¶", "ö")
							.replace("Ã³", "ó").replace("Ã–", "Ö").replace("Ã¨", "è").replace("Ã­", "í")
							.replace("Ã ", "à").replace("Ãª", "ê").replace("Ã¢", "â"));
			writer.write(s);
			writer.write('\n');
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
}
