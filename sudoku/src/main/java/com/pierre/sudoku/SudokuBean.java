package com.pierre.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Holder of data for a Sudoku game, with accessor methods 
 * providing additional logic to load from file and validate
 * 
 * @author Pierluigi
 *
 */
public class SudokuBean {
	public static final int SUDOKU_SIZE = 9;
	public static final int SUDOKU_SQUARES_PER_ROW_AND_COLUMN = 3; // number of
																	// squares
																	// vertically
																	// and
																	// horizontally
	public static final int SUDOKU_SQUARE_SIZE = 3; // number of rows and
													// columns making a square

	int[][] data = new int[SUDOKU_SIZE][SUDOKU_SIZE];

	public void loadFromFile(String fileAbsolutePath) throws IOException {

		// read file into stream, try-with-resources, one CSV line at a time

		try (Stream<String> stream = Files.lines(Paths.get(fileAbsolutePath))) {
			final AtomicInteger rowCount = new AtomicInteger(0);
			stream.forEach((line) -> {
				String[] items = line.split(",");
				for (int i = 0; i < items.length; i++) {
					int parseInt = Integer.parseInt(items[i]);

					data[rowCount.get()][i] = parseInt;
				}
				rowCount.getAndIncrement();
			});
		}

	}



	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			sb.append(Arrays.toString(data[i])).append("\n");
		}
		return sb.toString();
	}

/*
 * 
 */
	public SudokuValidationResult validate() {

		// check that all elements are in valid range
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			for (int j = 0; j < SUDOKU_SIZE; j++) {
				int element = getElementAt(i, j);
				if (element < 1 || element > SUDOKU_SIZE) {
					throw new IllegalArgumentException(String.format(
							"SUDOKU-0001: range should be [1..%1$d], invalid element %2$d at position %3$d, %4$d",
							SUDOKU_SIZE, element, i, j));
				}
			}
		}

		// check that all rows are made of different numbers

		for (int row = 0; row < SUDOKU_SIZE; row++) {
			int[] rowArray = getRow(row);
			if (!allDifferent(rowArray))
				return SudokuValidationResult
						.createFailure(String.format("SUDOKU-0003 row %1$d has repeated numbers  %2$s", row, Arrays.toString(rowArray)));
		}

		// check that all columns are made of different numbers

		for (int column = 0; column < SUDOKU_SIZE; column++) {
			int[] columnArray = getColumn(column);
			if (!allDifferent(columnArray))
				return SudokuValidationResult
						.createFailure(String.format("SUDOKU-0004 column %1$d has repeated numbers  %2$s", column, Arrays.toString(columnArray)));
		}

		// check that all squares are made of different numbers

		for (int square = 0; square < SUDOKU_SIZE; square++) {
			int[] squareArray = getSquare(square);
			if (!allDifferent(squareArray))
				return SudokuValidationResult
						.createFailure(String.format("SUDOKU-0005 square %1$d has repeated numbers %2$s", square, Arrays.toString(squareArray)));
		}

		return SudokuValidationResult.createSuccess();
	}
	
	/**
	 * Return true if all the integers in the array of size SUDOKU_SIZE are different - the integers should be in the range 1..SUDOKU_SIZE
	 * @param theArray
	 * @return
	 */

	private boolean allDifferent(int[] theArray) {
		if (theArray.length != SUDOKU_SIZE) {
			throw new IllegalArgumentException(
					String.format("SUDOKU-0002: invalid array size %1$d, expected %2$d", theArray.length, SUDOKU_SIZE));
		}
		boolean result = true;
		// initialize all count frequencies to 0
		int[] freq = new int[SUDOKU_SIZE];
		// count occurrences for each digit
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			freq[theArray[i] - 1]++;
		}
		// if any digit occurs for a number of times different from 1, failure
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			if (freq[i] != 1)
				result = false;
		}

		return result;

	}

	private int getElementAt(int i, int j) {
		if (i < 0 || i >= SUDOKU_SIZE || j < 0 || j >= SUDOKU_SIZE)
			throw new IllegalArgumentException(String.format(
					"SUDOKU-0007: invalid coorinates %1$d %2$d, valid range is [0..%3%d]", i, j, SUDOKU_SIZE - 1));
		return data[i][j];
	}

	private int[] getRow(int row) {
		if (row < 0 || row >= SUDOKU_SIZE)
			throw new IllegalArgumentException(String
					.format("SUDOKU-0006: invalid row value %1$d, valid range is [0..%2%d]", row, SUDOKU_SIZE - 1));
		return data[row];
	}

	private int[] getColumn(int column) {
		if (column < 0 || column >= SUDOKU_SIZE)
			throw new IllegalArgumentException(String.format(
					"SUDOKU-0008: invalid column value %1$d, valid range is [0..%2%d]", column, SUDOKU_SIZE - 1));
		int[] result = new int[SUDOKU_SIZE];
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			result[i] = getElementAt(i, column);
		}
		return result;
	}

	private int[] getSquare(int square) {
		if (square < 0 || square >= SUDOKU_SIZE)
			throw new IllegalArgumentException(String.format(
					"SUDOKU-0009: invalid square value %1$d, valid range is [0..%2%d]", square, SUDOKU_SIZE - 1));
		int[] result = new int[SUDOKU_SIZE];
		// 0 -> 0,0 ; 1 -> 0,3 ; 2 -> 0,6 ; 3 -> 3,0 ; 4 -> 3,3 ; 5 -> 3,6 ; 6
		// -> 6,0 ; 7 -> 6,3 ; 8 -> 6,6
		int baseRow = (square / SUDOKU_SQUARES_PER_ROW_AND_COLUMN) * SUDOKU_SQUARES_PER_ROW_AND_COLUMN;
		int baseColumn = (square % SUDOKU_SQUARES_PER_ROW_AND_COLUMN) * SUDOKU_SQUARE_SIZE;
		for (int i = 0; i < SUDOKU_SQUARE_SIZE; i++)
			for (int j = 0; j < SUDOKU_SQUARE_SIZE; j++)
				result[i + SUDOKU_SQUARE_SIZE * j] = getElementAt(baseRow + i, baseColumn + j);

		return result;
	}

}
