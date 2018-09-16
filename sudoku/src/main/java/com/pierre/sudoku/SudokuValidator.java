package com.pierre.sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Simple wrapper to invoke validation on a SudokuBean and print the result
 * @author Pierluigi
 *
 */
public class SudokuValidator {
	static Logger logger = Logger.getLogger(SudokuValidator.class.getName());
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) throw new IllegalArgumentException("missing parameter \"sudoku file absolute path\"");
		SudokuValidator sudokuValidator = new SudokuValidator();
		String absolutePath = args[0];
		SudokuValidationResult result = sudokuValidator.validateSudokuFile(absolutePath );
	}

	public SudokuValidationResult validateSudokuFile(String absolutePath) throws IOException {

		logger.info(String.format("validating file %1$s" , absolutePath));
		SudokuBean sudokuBean = new SudokuBean();
		sudokuBean.loadFromFile(absolutePath);
		logger.info(sudokuBean.toString());
		SudokuValidationResult result = sudokuBean.validate();
		logger.info(String.format("%1$s %2$s", result.result, result.validationError));
		return result;

	}
}
