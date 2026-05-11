package chat;

import chat.controller.ClientController;
import chat.controller.ServerController;
import chat.model.ChatRoom;
import chat.view.ChatView;
import chat.view.ChatWindow;

/**
 * ENTRY POINT
 * Wires together: Model + View + Controller
 *
 * Run Server:  java chat.ChatApplication server
 * Run Client:  java chat.ChatApplication
 * Run Client with host: java chat.ChatApplication client 192.168.1.5
 */
public class ChatApplication {

    public static void main(String[] args) throws Exception {

        if (args.length > 0 && args[0].equals("server")) {
            // ── SERVER MODE ──
            // Model
            ChatRoom model = new ChatRoom();
            // Controller (no View needed on server — console only)
            ServerController serverController = new ServerController(model);
            serverController.start(27015);

        } else {
            // ── CLIENT MODE ──
            String host = "localhost";
            if (args.length > 1) {
                host = args[1]; // custom host e.g. 192.168.1.5
            }

            // View
            ChatView view = new ChatWindow("TCP Chat - MVC Architecture");

            // Controller — wires View to network
            ClientController controller = new ClientController(view);
            controller.connect(host, 27015);
        }
    }
}
