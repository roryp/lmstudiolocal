package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;

public class ChatService {

    private final ObjectMapper objectMapper;

    public ChatService() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends a chat message to the LM Studio server and returns the response.
     *
     * @param serverUrl The URL of the LM Studio server
     * @param message   The user's message
     * @return The assistant's response
     * @throws IOException If there is an error communicating with the server
     * @throws ParseException If there is an error parsing the response
     */
    public String sendChatMessage(String serverUrl, String message) throws IOException, ParseException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(serverUrl);
            
            // Create request payload
            String jsonInput = createRequestPayload(message);
            StringEntity entity = new StringEntity(jsonInput);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                
                // Parse the response
                return parseResponse(responseBody);
            }
        }
    }

    /**
     * Creates the JSON request payload for the LM Studio API.
     *
     * @param message The user's message
     * @return JSON string for the API request
     */
    private String createRequestPayload(String message) {
        try {
            // Simple JSON structure for LM Studio API
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"messages\": [");
            json.append("{\"role\": \"user\", \"content\": \"").append(escapeJsonString(message)).append("\"}");
            json.append("],");
            json.append("\"temperature\": 0.7,");
            json.append("\"max_tokens\": 1000");
            json.append("}");
            return json.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create request payload", e);
        }
    }

    /**
     * Parses the JSON response from the LM Studio server.
     *
     * @param responseBody The JSON response from the server
     * @return The assistant's message
     * @throws IOException If there is an error parsing the response
     */
    private String parseResponse(String responseBody) throws IOException {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        
        // Handle different response formats based on actual API response
        if (rootNode.has("choices") && rootNode.get("choices").isArray() && rootNode.get("choices").size() > 0) {
            JsonNode firstChoice = rootNode.get("choices").get(0);
            
            // Check if the response follows OpenAI format
            if (firstChoice.has("message") && firstChoice.get("message").has("content")) {
                return firstChoice.get("message").get("content").asText();
            } else if (firstChoice.has("text")) {
                // Simple text completion format
                return firstChoice.get("text").asText();
            } else if (firstChoice.has("content")) {
                // Direct content format
                return firstChoice.get("content").asText();
            }
        }
        
        // Fallback for unparseable responses - return raw JSON
        return "Failed to parse response: " + responseBody;
    }

    /**
     * Escapes special characters in a string for use in JSON.
     *
     * @param input The string to escape
     * @return The escaped string
     */
    private String escapeJsonString(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}