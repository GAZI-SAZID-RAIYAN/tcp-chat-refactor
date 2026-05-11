package chat.controller;

import chat.model.ChatRoom;
import chat.network.TcpNetworkAdapter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLLER - Server side logic
 * MVC Role: C (Controller)
 * SOLID: SRP - Only manages server connections and broadcasting
 * SOLID: OCP - Network layer can change without changing this
 */
public class ServerController {

    private final List<ClientHandlerController> clients = new ArrayList<>();
    private final ChatRoom model; // Model
    private int counter = 1;

    public ServerController(ChatRoom model) {
        this.model = model;
    }

    public void start(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("=== MVC Chat Server Started on port " + port + " ===");
        System.out.println("Waiting for clients...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected: " + socket.getInetAddress());

            TcpNetworkAdapter adapter = new TcpNetworkAdapter(socket);
            String username = "Client" + counter++;

            ClientHandlerController handler = new ClientHandlerController(
                    adapter, adapter, this, model, username
            );

            clients.add(handler);
            new Thread(handler).start();
        }
    }

    /**
     * Broadcast message to all connected clients
     * Controller → All Views (via network)
     */
    public synchronized void broadcast(String msg) {
        System.out.println("[BROADCAST] " + msg);
        for (ClientHandlerController client : clients) {
            client.send(msg);
        }
    }

    public synchronized void removeClient(ClientHandlerController c) {
        clients.remove(c);
        System.out.println(c.getUsername() + " removed. Active clients: " + clients.size());
    }

    public ChatRoom getModel() { return model; }
}
