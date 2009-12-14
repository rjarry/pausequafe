@echo off

rem ##############################################
rem Generate Ui_*.java classes from *.jui files  #
rem                                              #
rem The binary juic must be in your PATH or in   #
rem this directory                               #
rem                                              #
rem Don't forget to add the generated            #
rem directory to your source folders in Eclipse  #
rem ##############################################

echo clearing old generated files
cd /d %~dp0
del /F /S /Q generated
echo.
echo Checking for .jui files in .\src\main
call juic.exe -d "generated" -cp .\src\main
echo.
echo Checking for .jui files in .\src\test
call juic.exe -d "generated" -cp .\src\test
echo.

pause