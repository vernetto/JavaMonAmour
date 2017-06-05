package com.pierre.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

public class SudokuBean {
	public static final int SUDOKU_SIZE = 9;

	int[][] data = new int[SUDOKU_SIZE][SUDOKU_SIZE];

	public void loadFromFile(String fileAbsolutePath) throws IOException {

		// read file into stream, try-with-resources, one CSV line at a time
		
		try (Stream<String> stream = Files.lines(Paths.get(fileAbsolutePath))) {
			final AtomicInteger rowCount = new AtomicInteger(0);
			stream.forEach((line) -> {
				String[] items = line.split(",");
				for (int i = 0; i < items.length; i++) {
					data[rowCount.get()][i] = Integer.parseInt(items[i]);
				}
				rowCount.getAndIncrement();
			});
		}
		
		
	}
	
	private Function<String, int []> mapToRow = (line) -> {
		int[] result = new int[SUDOKU_SIZE];
		String[] items = line.split(",");
		for (int i = 0; i < items.length; i++) {
			result[i] = Integer.parseInt(items[i]);
		}
		return result;
	};
	
  public String toString() {
	  StringBuilder sb = new StringBuilder();
	  for (int i = 0; i < SUDOKU_SIZE; i++) {
		  sb.append(Arrays.toString(data[i])).append("\n");
	  }
	  return sb.toString();
  }

}
