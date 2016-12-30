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
		String inputFileName = "D:\\pierre\\deutsch\\odisseaita.txt";
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
			s = s.trim();
			if (s.matches("[0-9]+") || s.length() == 0)
				return;
			writer.write(s);
			writer.write('\n');
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
}
