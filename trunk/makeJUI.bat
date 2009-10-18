@echo off

rem ##############################################
rem Generate Ui_*.java classes from *.jui files  #
rem                                              #
rem The binary juic must be in your PATH or in   #
rem this directory                               #
rem                                              #
rem Don't forget to add the Generated_JUIC_files #
rem directory to your source folders in Eclipse  #
rem ##############################################

echo Checking for .jui files in .\src\main
call juic.exe -d "Generated JUIC files" -cp .\src\main

echo.
echo Checking for .jui files in .\src\test
call juic.exe -d "Generated JUIC files" -cp .\src\test
echo.

pause