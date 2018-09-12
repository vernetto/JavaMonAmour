package com.pierre.googletranslate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvertLanguages {
	static final String FILENAME = "D:\\pierre\\calibre\\Pier Paolo Pasolini\\Descrizioni di descrizioni (218)\\Descrizioni di descrizioni - Pier Paolo Pasolini.txt.transout";
	static final String IT = "{{Bookmark=VOICE_IT}}";
	static final String DE = "{{Bookmark=VOICE_DE}}";

	public static void main(String[] args) throws IOException {
		List<String> list = new ArrayList<>();
		List<String> out = new ArrayList<>();
		BufferedReader br = Files.newBufferedReader(Paths.get(FILENAME));
		list = br.lines().collect(Collectors.toList());
		
		List<String> buffer = new ArrayList<>();
		boolean inIT = true;
		for (int i = 0 ; i < list.size(); i++) {
			String line = list.get(i);
			if (inIT) {
				buffer.add(line);
				if (line.contains(IT)) {
					inIT = false;
				}
			}
			else {
				out.add(line);
				if (line.contains(DE)) {
					inIT = true;
					out.addAll(buffer);
					buffer = new ArrayList<>();
				}
				
			}
		}
		
		
		Files.write(Paths.get(FILENAME + ".inv"), out);
	}
}
