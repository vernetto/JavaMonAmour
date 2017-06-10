package com.pierre.sudoku;
/**
 * Holds validation resut and - in case of failure - the reason for failure
 * @author Pierre-Luigi
 *
 */

public class SudokuValidationResult {
	enum ResultCode {
		SUCCESS, FAILURE;
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
		return new SudokuValidationResult(ResultCode.SUCCESS, "");
	}

	// factory method
	public static SudokuValidationResult createFailure(String error) {
		return new SudokuValidationResult(ResultCode.FAILURE, error);
	}

}
