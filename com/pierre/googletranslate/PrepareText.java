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
		String inputFileName = "D:\\pierre\\calibre\\Victor Klemperer\\LTI (93)\\LTI - Victor Klemperer.txt";
		Path outputFilePath = Paths.get(inputFileName + ".out");
		BufferedWriter writer = Files.newBufferedWriter(outputFilePath);

		Path fileName = Paths.get(inputFileName);
		Charset defaultCharset = StandardCharsets.UTF_8;
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
			result = s.replace(".", ".\n").replace("?", "?\n").replace("!", "!\n").replace(":", ":\n")
					.replace(";", ";\n").replace("\"", " ");

		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;

	}

	public static String transformLatinToUTF8(String s) {
		String returnValue = "";
		try {
			returnValue = s.replace("Ü", "�").replace("ü", "�").replace("ä", "�").replace("ö", "�")
					.replace("ß", "ss").replace("é", "�").replace("ý", "�").replace("Ä", "�").replace("á", "�")
					.replace("ó", "�").replace("ò", "�").replace("ö", "�").replace("ó", "�").replace("Ö", "�")
					.replace("è", "�").replace("í", "�").replace("à", "�").replace("ê", "�").replace("â", "�");
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return returnValue;

	}
}
