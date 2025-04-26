@echo off
rem This batch file runs the LM Studio Chat application using the JavaFX Maven plugin

echo Starting LM Studio Chat Client...
echo.
echo This will connect to your locally running LM Studio server (make sure it's running!)
echo.

mvn clean javafx:run

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo =======================================
    echo ERROR: Failed to run the application.
    echo =======================================
    echo.
    echo Possible solutions:
    echo 1. Make sure you have Maven installed and in your PATH
    echo 2. Make sure you have Java 17 or newer installed
    echo 3. Make sure your JAVA_HOME environment variable is set correctly
    echo.
    pause
)