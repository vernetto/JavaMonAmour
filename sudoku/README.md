## Sudoku validation test

This test was prepared with Maven 3.5.0 and Java 8.

To install Maven, download it from https://maven.apache.org/download.cgi. 


To install Java 8, download it from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html 


Make sure you define the following environment variables (I am reporting my values under Windows 10): 

```
JAVA_HOME="D:\pierre\Java\jdk1.8.0_111"
Path=D:\pierre\Java\jdk1.8.0_111\bin;D:\apps\apache-maven-3.5.0\bin;
M2_HOME=D:\apps\apache-maven-3.5.0\
```


The test is provided with 2 files, one valid and one invalid, in src/test/resources, and 2 JUnit tests to test both scenarios

To execute the tests, you can either git clone this repository, or download the sudoku.zip file, unzip it and, from the location of pom.xml file, execute `mvn clean package`

In the `src\main\scripts` folder there is the validate.bat file. Before you run it, you should initialize (`set SUDOKU_HOME=absolute_path_to_folder`) the variable SUDOKU_HOME to a location such as %SUDOKU_HOME%\lib\sudoku.jar exists (e.g. the root location where you run the `git clone` command, or where you unzipped the sudoku.zip file)
 