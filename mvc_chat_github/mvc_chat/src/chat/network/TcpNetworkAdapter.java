package chat.network;

import java.io.*;
import java.net.Socket;

/**
 * NETWORK LAYER - TCP implementation
 * SOLID: DIP - implements abstractions (Sender, Receiver)
 * SOLID: LSP - can substitute wherever Sender/Receiver needed
 */
public class TcpNetworkAdapter implements Sender, Receiver {

    private final BufferedReader in;
    private final BufferedWriter out;

    public TcpNetworkAdapter(Socket socket) throws Exception {
        this.in  = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void send(String msg) throws Exception {
        out.write(msg);
        out.newLine();
        out.flush();
    }

    @Override
    public String receive() throws Exception {
        return in.readLine();
    }
}
