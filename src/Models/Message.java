package Models; // Make sure this matches your package name

import java.time.LocalDateTime;

public class Message {
    private final String sender;
    private final String receiver;
    private final String content;
    private final LocalDateTime timestamp;

    public Message(String sender, String receiver, String content, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", timestamp, sender, content);
    }
}
