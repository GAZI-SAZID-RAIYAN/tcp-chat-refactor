package chat.controller;

import chat.model.ChatRoom;
import chat.model.Message;
import chat.network.Receiver;
import chat.network.Sender;

/**
 * CONTROLLER - Handles one client on server side
 * MVC Role: C (Controller)
 * SOLID: SRP - Only handles one client's communication
 */
public class ClientHandlerController implements Runnable {

    private final Sender     sender;
    private final Receiver   receiver;
    private final ServerController serverController;
    private final ChatRoom   model;
    private final String     username;

    public ClientHandlerController(Sender sender, Receiver receiver,
                                   ServerController serverController,
                                   ChatRoom model, String username) {
        this.sender           = sender;
        this.receiver         = receiver;
        this.serverController = serverController;
        this.model            = model;
        this.username         = username;
    }

    @Override
    public void run() {
        try {
            // Update model
            model.addUser(username);
            // Notify all via server controller
            serverController.broadcast(username + " joined the chat.");

            String input;
            while ((input = receiver.receive()) != null) {
                Message msg = new Message(username, input);
                // Save to model
                model.addMessage(msg);
                // Broadcast via controller
                serverController.broadcast(msg.format());
            }
        } catch (Exception e) {
            System.out.println(username + " disconnected.");
        } finally {
            model.removeUser(username);
            serverController.removeClient(this);
            serverController.broadcast(username + " left the chat.");
        }
    }

    public void send(String msg) {
        try {
            sender.send(msg);
        } catch (Exception ignored) {}
    }

    public String getUsername() { return username; }
}
