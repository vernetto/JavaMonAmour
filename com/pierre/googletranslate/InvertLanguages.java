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
	static final String FILENAME = "D:\\pierre\\pictures\\librolafaille\\librolafaille.txt.transout";
	static final String FIRST = "{{Bookmark=VOICE_FR}}";
	static final String SECOND = "{{Bookmark=VOICE_DE}}";

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
				if (line.contains(FIRST)) {
					inIT = false;
				}
			}
			else {
				out.add(line);
				if (line.contains(SECOND)) {
					inIT = true;
					out.addAll(buffer);
					buffer = new ArrayList<>();
				}
				
			}
		}
		
		
		Files.write(Paths.get(FILENAME + ".inv"), out);
	}
}
