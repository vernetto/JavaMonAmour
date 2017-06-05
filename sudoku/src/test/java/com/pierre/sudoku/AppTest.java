package com.pierre.sudoku;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testFail()
    {
    	App app = new App();
		String fileName = "invalidsudoku.txt";
    	String absolutePath = getAbsolutePathForResource(fileName);
    	app.readFile(absolutePath);
        assertTrue( true );
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
    

    public void testSucceed()
    {
        assertTrue( true );
    }
}
