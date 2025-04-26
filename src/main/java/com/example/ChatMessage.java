package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a chat message in the conversation.
 */
public class ChatMessage {
    private final String role;
    private final String content;
    private final LocalDateTime timestamp;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Creates a new chat message.
     *
     * @param role    The role of the message sender (user or assistant)
     * @param content The content of the message
     */
    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Get the role of the message sender.
     *
     * @return "user" or "assistant"
     */
    public String getRole() {
        return role;
    }

    /**
     * Get the content of the message.
     *
     * @return Message text
     */
    public String getContent() {
        return content;
    }

    /**
     * Get the formatted timestamp of when the message was created.
     *
     * @return Formatted timestamp
     */
    public String getFormattedTimestamp() {
        return timestamp.format(formatter);
    }
}