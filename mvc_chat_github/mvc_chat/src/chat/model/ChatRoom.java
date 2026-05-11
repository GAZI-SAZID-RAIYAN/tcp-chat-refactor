package chat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL - Stores chat room state
 * MVC Role: M (Model)
 * SOLID: SRP - Only manages chat room data
 */
public class ChatRoom {
    private final List<Message> messageHistory = new ArrayList<>();
    private final List<String>  connectedUsers  = new ArrayList<>();

    public void addMessage(Message msg) {
        messageHistory.add(msg);
    }

    public void addUser(String username) {
        connectedUsers.add(username);
    }

    public void removeUser(String username) {
        connectedUsers.remove(username);
    }

    public List<Message> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    public List<String> getConnectedUsers() {
        return new ArrayList<>(connectedUsers);
    }

    public int getUserCount() {
        return connectedUsers.size();
    }
}
