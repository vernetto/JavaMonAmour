package com.pierre.sudoku;
/**
 * Holds sudoku validation result and - in case of failure - the reason for failure
 * @author Pierluigi
 *
 */

public class SudokuValidationResult {
	enum ResultCode {
		VALID, INVALID;
	}

	ResultCode result = null;
	String validationError = null;

	public SudokuValidationResult(ResultCode result, String validationError) {
		this.result = result;
		this.validationError = validationError;
	}

	@Override
	public String toString() {
		return "SudokuValidationResult [result=" + result + ", validationError=" + validationError + "]";
	}
	
	// factory method
	public static SudokuValidationResult createSuccess() {
		return new SudokuValidationResult(ResultCode.VALID, "");
	}

	// factory method
	public static SudokuValidationResult createFailure(String error) {
		return new SudokuValidationResult(ResultCode.INVALID, error);
	}

}
