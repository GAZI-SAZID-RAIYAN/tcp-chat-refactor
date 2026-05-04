package chat.controller;

import chat.model.Message;
import chat.network.Receiver;
import chat.network.Sender;
import chat.network.TcpNetworkAdapter;
import chat.view.ChatView;

import java.net.Socket;

/**
 * CONTROLLER - Client side logic
 * MVC Role: C (Controller)
 * SOLID: SRP - Only handles client-side communication logic
 * SOLID: DIP - depends on ChatView interface, not concrete class
 */
public class ClientController {

    private final ChatView view;
    private Sender   sender;
    private Receiver receiver;

    public ClientController(ChatView view) {
        this.view = view;
    }

    public void connect(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            TcpNetworkAdapter adapter = new TcpNetworkAdapter(socket);
            this.sender   = adapter;
            this.receiver = adapter;

            view.displaySystemMessage("Connected to server at " + host + ":" + port);

            // Register send listener — View → Controller
            view.setOnSendListener(this::sendMessage);

            // Start receiving in background thread
            startReceiving();

        } catch (Exception e) {
            view.displaySystemMessage("Connection failed: " + e.getMessage());
        }
    }

    /**
     * Called by View when user clicks Send
     * Controller → Network
     */
    private void sendMessage(String text) {
        try {
            sender.send(text);
        } catch (Exception e) {
            view.displaySystemMessage("Failed to send message.");
        }
    }

    /**
     * Background thread: Network → Controller → View
     */
    private void startReceiving() {
        Thread thread = new Thread(() -> {
            try {
                String line;
                while ((line = receiver.receive()) != null) {
                    // Controller updates View with received data
                    view.displayMessage(line);
                }
            } catch (Exception e) {
                view.displaySystemMessage("Disconnected from server.");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
