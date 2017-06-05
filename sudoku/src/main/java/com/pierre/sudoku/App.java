package com.pierre.sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(App.class.getName());
		logger.info("hello world!");
	}

	public void readFile(String absolutePath) {

		System.out.println(absolutePath);

		// read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(absolutePath))) {

			stream.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
