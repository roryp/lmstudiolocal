package com.example;

/**
 * Launcher class that properly initializes JavaFX and launches the main application.
 * This class helps to work around the JavaFX module system requirements by using
 * a standard Java main method instead of extending Application directly.
 */
public class LmStudioChatAppLauncher {
    
    /**
     * Main entry point that delegates to the JavaFX application.
     * Using this launcher helps avoid JavaFX module path issues.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("javafx.verbose", "true");
        
        try {
            // Launch the actual JavaFX application
            LmStudioChatApp.main(args);
        } catch (Exception e) {
            System.err.println("Failed to start JavaFX application.");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nJavaFX modules may not be on the module path.");
            System.err.println("Please use one of the provided run scripts or add the following options:");
            System.err.println("--module-path <path-to-fx-mods> --add-modules=javafx.controls,javafx.fxml");
            e.printStackTrace();
            
            // Exit with error code
            System.exit(1);
        }
    }
}