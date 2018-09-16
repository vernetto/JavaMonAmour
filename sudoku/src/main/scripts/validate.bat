@echo off
if [%1]==[] goto usage

SET SUDOKUFILE=%1

if "%SUDOKU_HOME%"=="" (goto :novarset)

if NOT EXIST %SUDOKU_HOME%/lib/sudoku.jar (goto :nojarfile)

if NOT EXIST %SUDOKUFILE%  (goto :nosudokufile)

java -cp %SUDOKU_HOME%/lib/sudoku.jar com.pierre.sudoku.SudokuValidator %SUDOKUFILE%

	

@echo Done.
goto :eof

:usage
@echo Missing parameter. Usage: %0 ^<SudokuFileAbsolutePath^>
exit /B 1

:novarset
echo SUDOKU_HOME is not defined. SUDOKU_HOME/lib/sudoku.jar should also exist. 
exit /B 1

:nojarfile
echo %SUDOKU_HOME%/lib/sudoku.jar could not be found.
exit /B 1

:nosudokufile
echo sudoku file "%SUDOKUFILE%" not found
exit /B 1

