package Controllers;

import java.util.List;
import java.util.stream.Collectors;

import Services.MessagingService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Models.Message;
import java.time.format.DateTimeFormatter;

public class MessagePopupController {

    @FXML
    private ListView<String> messagesListView;

    @FXML
    private TextArea messageTextArea;

    private String patientName; // This will be set by the doctor portal controller
    private String senderRole; // Add this field
    private MessagingService messagingService = MessagingService.getInstance();

    // Modify the initController method to accept senderRole
    public void initController(String patientName, String senderRole) {
        this.patientName = patientName;
        this.senderRole = senderRole; // Use this role as part of the message sending
        loadMessages();
    }
    
    // Then, adjust the sendMessage method to use senderRole as the sender
    @FXML
    void sendMessage() {
        String messageContent = messageTextArea.getText().trim();
        if (!messageContent.isEmpty()) {
            messagingService.sendMessageToConversation(senderRole, patientName, messageContent); // Use senderRole here
            messageTextArea.clear();
            loadMessages(); // Refresh message list
        }
    }

    private void loadMessages() {
        if (messagingService.conversationExists(patientName)) {
            // Fetch messages from the conversation
            List<Message> messages = messagingService.getMessagesFromConversation(patientName);

            // Use a stream to format messages and collect them into a List
            List<String> formattedMessages = messages.stream()
                .map(message -> String.format("%s [%s]: %s",
                        message.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        message.getSender(),
                        message.getContent()))
                .collect(Collectors.toList());

            // Convert the List to an ObservableList and set it on the ListView
            ObservableList<String> observableMessages = FXCollections.observableArrayList(formattedMessages);
            messagesListView.setItems(observableMessages);
        } else {
            messagesListView.setItems(FXCollections.observableArrayList("No messages found."));
        }
    }
}
