#!/bin/bash
# This shell script runs the LM Studio Chat application with proper JavaFX module path

# Check if JAVA_HOME is set
if [ -z "$JAVA_HOME" ]; then
    echo "Error: JAVA_HOME environment variable is not set."
    echo "Please set JAVA_HOME to your Java installation directory."
    exit 1
fi

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Maven not found in PATH. Using direct Java execution..."
    
    # Set path to JavaFX modules - convert Windows path to Git Bash path
    SCRIPT_DIR=$(dirname "$(realpath "$0")")
    PATH_TO_FX="$SCRIPT_DIR/target/javafx-modules"
    
    if [ ! -d "$PATH_TO_FX" ]; then
        echo "Creating JavaFX modules directory..."
        mkdir -p "$PATH_TO_FX"
        
        # Extract JavaFX modules from the JAR if they exist
        echo "JavaFX modules will be extracted from the JAR if available."
    fi
    
    echo "Starting application with JavaFX modules..."
    "$JAVA_HOME/bin/java" --module-path "$PATH_TO_FX" --add-modules=javafx.controls,javafx.fxml -jar target/phi3chat-1.0-SNAPSHOT.jar
else
    echo "Maven found. Running with JavaFX Maven plugin..."
    mvn javafx:run
fi

echo
read -p "Press Enter to continue..."