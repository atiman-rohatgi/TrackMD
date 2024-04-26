package Services; // Adjust the package name as necessary

import Models.Message;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class MessagingService {
    private List<Message> messages = new ArrayList<>();
    private Map<String, List<String>> conversations = new HashMap<>(); // Maps conversation ID to participant IDs

    private static MessagingService instance = null;

    private MessagingService() {}

    public static synchronized MessagingService getInstance() {
        if (instance == null) {
            instance = new MessagingService();
        }
        return instance;
    }

    // Initialize a conversation
    public String initConversation(List<String> participantIds) {
        String conversationId = UUID.randomUUID().toString(); // Generate a unique ID for the conversation
        conversations.put(conversationId, participantIds);
        return conversationId;
    }

    public void sendMessageToConversation(String senderId, String conversationId, String content) {
        // Ensure conversation exists before sending the message
        ensureConversationExists(conversationId); // Implement this method if it doesn't exist
        
        // Now, send the message as usual
        Message newMessage = new Message(senderId, conversationId, content, LocalDateTime.now());
        messages.add(newMessage);
    }

    // Method to retrieve messages from a conversation
    public List<Message> getMessagesFromConversation(String conversationId) {
        if (!conversations.containsKey(conversationId)) {
            throw new IllegalArgumentException("Conversation does not exist");
        }
        return messages.stream()
            .filter(m -> m.getReceiver().equals(conversationId))
            .collect(Collectors.toList());
    }
    
    public boolean conversationExists(String conversationId) {
        return conversations.containsKey(conversationId);
    }

    public void ensureConversation(String conversationId, List<String> participantIds) {
        if (!conversationExists(conversationId)) {
            conversations.put(conversationId, participantIds);
        }
    }
    
    // Ensure this method exists or create it
    public void ensureConversationExists(String conversationId) {
        if (!conversations.containsKey(conversationId)) {
            conversations.put(conversationId, new ArrayList<>()); // Or initialize it as necessary
        }
    }

}
