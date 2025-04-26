# Local LLM Chat Java Project
![Hal 9000 from 2001: A Space Odyssey](hal9000.webp)
The Local LLM Chat Java Project is designed to interact with a locally hosted language model server. The project consists of a Java application that sends chat messages to the server and receives responses.

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- LM Studio running in server mode (see setup below)

## LM Studio Setup

1. Download a model from Hugging Face.
2. Run the `llmstudio` in server mode.
    - Head to the Local Server tab ( <-> on the left)
    - Load the LLM you downloaded by choosing it from the dropdown.
    - Change the port to 8000.
    - Start the server by clicking on the green Start Server button.
    - You can verify the server is running by checking the logs or visiting http://localhost:8000 in your browser.

## Building and Running the JavaFX Client

### Option 1: Using the Provided Batch Files (Windows)

The simplest way to build and run the application is by using the provided batch files:

1. To build and run in one step, simply execute:
   ```
   run.bat
   ```

2. For more detailed control, you can use:
   ```
   run_app.bat
   ```

### Option 2: Using Maven Commands

1. Build the project:
   ```
   mvn clean package
   ```

2. Run the JavaFX application:
   ```
   mvn javafx:run
   ```

## Using the Chat Application

1. When the application starts, it will display a modern UI with a sidebar containing example prompts.
2. The default server URL is pre-configured to `http://localhost:8000/v1/chat/completions`.
3. You can:
   - Select example prompts from the sidebar
   - Type your own messages in the input area at the bottom
   - Click the "Send" button to submit your query to the local LM Studio server
   - View the chat history in the main panel

## Project Structure

- `src/main/java/com/example/` - Contains the Java source files
  - `LmStudioChatApp.java` - Main JavaFX application class
  - `ChatService.java` - Handles API communication with LM Studio
  - `ChatMessage.java` - Model class for chat messages
  - `ChatMessageCell.java` - Custom cell renderer for chat messages
- `src/main/resources/` - Contains resources for the application
  - `styles/styles.css` - CSS styles for the UI

## Troubleshooting

- If you encounter a "JavaFX runtime components are missing" error, ensure you're using one of the provided run methods (batch files or Maven commands).
- Make sure LM Studio is running on the expected port before starting the chat application.
- Check that your Java version is 17 or later with `java -version`.