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
public class SudokuValidator {
	static Logger logger = Logger.getLogger(SudokuValidator.class.getName());
	
	enum SudokuResult {
		SUCCESS, FAILURE
	}
	
	public static void main(String[] args) {
		
		logger.info("hello world!");
	}

	public SudokuResult validateSudokuFile(String absolutePath) throws IOException {

		System.out.println(absolutePath);
		SudokuBean sudokuBean = new SudokuBean();
		sudokuBean.loadFromFile(absolutePath);
		logger.info(sudokuBean.toString());

		return SudokuResult.SUCCESS;

	}
}
