package chat.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * MODEL - Stores message data only
 * MVC Role: M (Model)
 * SOLID: SRP - Only responsible for message data
 */
public class Message {
    private final String sender;
    private final String content;
    private final String timestamp;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getSender()   { return sender; }
    public String getContent()  { return content; }
    public String getTimestamp(){ return timestamp; }

    public String format() {
        return "[" + timestamp + "] " + sender + ": " + content;
    }
}
