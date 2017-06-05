package com.pierre.sudoku;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.pierre.sudoku.SudokuValidator.SudokuResult;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SudokuValidatorTest 
    extends TestCase
{
	Logger logger = Logger.getLogger(SudokuValidatorTest.class.getName());
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SudokuValidatorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SudokuValidatorTest.class );
    }

    public void testFail()
    {
    	SudokuValidator app = new SudokuValidator();
		String fileName = "invalidsudoku.txt";
    	String absolutePath = getAbsolutePathForResource(fileName);
    	SudokuResult result = null;
    	try {
    		result = app.validateSudokuFile(absolutePath);
    	}
    	catch (Exception e) {
    		logger.severe(e.getMessage());
    	}
    	
        assertEquals( SudokuResult.FAILURE, result);
    }

    
    public void testSucceed()
    {
    	SudokuValidator app = new SudokuValidator();
		String fileName = "validsudoku.txt";
    	String absolutePath = getAbsolutePathForResource(fileName);
    	SudokuResult result = null;
    	try {
    		result = app.validateSudokuFile(absolutePath);
    	}
    	catch (Exception e) {
    		logger.severe(e.getMessage());
    	}
    	
        assertEquals( SudokuResult.SUCCESS, result);
    }
    
    /**
     * In unit-tests, we access to test files located in src\test\resources -> deployed in target\test-classes by using getResource() from the classpath
     * @see https://docs.oracle.com/javase/8/docs/api/java/lang/ClassLoader.html#getResource-java.lang.String-
     * @param fileName
     * @return absolute path of file
     */
	private String getAbsolutePathForResource(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(fileName).getFile());
    	String absolutePath = file.getAbsolutePath();
		return absolutePath;
	}
    


}
