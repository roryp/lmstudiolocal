@echo off
rem This batch file runs the LM Studio Chat application with proper JavaFX module path

rem Check if JAVA_HOME is set
if "%JAVA_HOME%"=="" (
    echo Error: JAVA_HOME environment variable is not set.
    echo Please set JAVA_HOME to your Java installation directory.
    exit /b 1
)

rem Check if Maven is available
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Maven not found in PATH. Using direct Java execution...
    
    rem Set path to JavaFX modules - this path may need to be adjusted
    set PATH_TO_FX=%~dp0target\javafx-modules
    
    if not exist "%PATH_TO_FX%" (
        echo Creating JavaFX modules directory...
        mkdir "%PATH_TO_FX%"
        
        rem Extract JavaFX modules from the JAR if they exist (they should be included via Maven shade plugin)
        echo JavaFX modules will be extracted from the JAR if available.
    )
    
    echo Starting application with JavaFX modules...
    "%JAVA_HOME%\bin\java" --module-path "%PATH_TO_FX%" --add-modules=javafx.controls,javafx.fxml -jar target\phi3chat-1.0-SNAPSHOT.jar
) else (
    echo Maven found. Running with JavaFX Maven plugin...
    mvn javafx:run
)

echo.
pause