package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Custom cell renderer for chat messages in the list view.
 */
public class ChatMessageCell extends ListCell<ChatMessage> {

    private final VBox container;
    private final Label nameLabel;
    private final TextFlow messageFlow;
    private final Text messageText;
    private final Label timestampLabel;
    private final FontIcon userIcon;
    private final FontIcon assistantIcon;
    private final HBox header;
    
    public ChatMessageCell() {
        container = new VBox(5);
        container.setPadding(new Insets(10));
        
        header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        
        // User icon
        userIcon = new FontIcon(FontAwesomeSolid.USER);
        userIcon.setIconSize(16);
        
        // Assistant icon
        assistantIcon = new FontIcon(FontAwesomeSolid.ROBOT);
        assistantIcon.setIconSize(16);
        
        // Name label
        nameLabel = new Label();
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        
        // Timestamp label
        timestampLabel = new Label();
        timestampLabel.setFont(Font.font("System", 11));
        timestampLabel.setStyle("-fx-text-fill: #777777;");
        
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        header.getChildren().addAll(userIcon, nameLabel, timestampLabel);
        
        // Message content
        messageText = new Text();
        messageText.setWrappingWidth(0); // Initialize with 0, will be updated in configureWrapping
        
        messageFlow = new TextFlow(messageText);
        // Ensure the text flow can grow within its container
        VBox.setVgrow(messageFlow, Priority.ALWAYS);
        
        container.getChildren().addAll(header, messageFlow);
        
        // Register for width changes to update wrapping
        widthProperty().addListener((obs, oldVal, newVal) -> {
            configureWrapping();
        });
    }
    
    @Override
    protected void updateItem(ChatMessage message, boolean empty) {
        super.updateItem(message, empty);
        
        if (empty || message == null) {
            setText(null);
            setGraphic(null);
            return;
        }
        
        // Configure the cell based on message role
        if ("user".equals(message.getRole())) {
            nameLabel.setText("You");
            container.setStyle("-fx-background-color: #e3f2fd; -fx-background-radius: 10;");
            assistantIcon.setVisible(false);
            userIcon.setVisible(true);
        } else {
            nameLabel.setText("Assistant");
            container.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 10;");
            userIcon.setVisible(false);
            assistantIcon.setVisible(true);
            header.getChildren().set(0, assistantIcon);
        }
        
        messageText.setText(message.getContent());
        
        // Set wrapping width based on current list view width
        configureWrapping();
        
        timestampLabel.setText(message.getFormattedTimestamp());
        
        setGraphic(container);
    }
    
    private void configureWrapping() {
        // Get the width of the ListView and account for padding and scrollbar
        double width = 600; // Default fallback width
        
        if (getListView() != null) {
            // Calculate available width by accounting for padding, cell insets, and scrollbar
            width = getListView().getWidth() - 40; // Adjust for padding and scrollbar
            
            // Don't allow tiny widths during initialization or when window is very small
            if (width < 200) {
                width = 600;
            } else if (width > 2000) {
                // Cap maximum width for better readability
                width = 2000;
            }
            
            // Account for container padding
            width -= 20; // Left and right container padding
        }
        
        // Set the wrapping width on the text
        messageText.setWrappingWidth(width);
        
        // Make sure the container respects the width constraints
        container.setMaxWidth(width + 20); // Add padding back for container
    }
}