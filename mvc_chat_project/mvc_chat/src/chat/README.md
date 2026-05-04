# 💬 MVC Chat Application
👤 Gazi Sazid Raiyan | 🆔 240211

👤 Md Hasan | 🆔 240227


A real-time TCP-based chat application built in **Java**, strictly following the **MVC (Model-View-Controller)** architectural pattern and **SOLID** design principles.

---

## 📁 Project Structure

```
mvc_chat/
└── src/
    └── chat/
        ├── ChatApplication.java              # Entry point — wires MVC together
        ├── model/
        │   ├── Message.java                  # Stores message data (sender, content, timestamp)
        │   └── ChatRoom.java                 # Stores chat room state (users, message history)
        ├── view/
        │   ├── ChatView.java                 # View interface (any UI must implement this)
        │   └── ChatWindow.java               # Swing GUI implementation
        ├── controller/
        │   ├── ClientController.java         # Handles client-side connection & communication
        │   ├── ServerController.java         # Manages server, accepts clients, broadcasts
        │   └── ClientHandlerController.java  # Handles one connected client on the server side
        └── network/
            ├── Sender.java                   # Interface for sending messages
            ├── Receiver.java                 # Interface for receiving messages
            └── TcpNetworkAdapter.java        # TCP implementation of Sender + Receiver
```

---

## 🏛️ Architecture — MVC Pattern

| Layer | Class(es) | Responsibility |
|---|---|---|
| **Model** | `Message`, `ChatRoom` | Stores and manages all data — no UI, no network logic |
| **View** | `ChatView` (interface), `ChatWindow` | Displays messages, captures user input via Swing GUI |
| **Controller** | `ClientController`, `ServerController`, `ClientHandlerController` | Bridges View ↔ Model ↔ Network |
| **Network** | `TcpNetworkAdapter`, `Sender`, `Receiver` | Handles raw TCP socket I/O |

### Data Flow

```
User types message
      │
      ▼
 ChatWindow (View)
      │  setOnSendListener callback
      ▼
 ClientController (Controller)
      │  sender.send(text)
      ▼
 TcpNetworkAdapter (Network) ──► TCP Socket ──► Server
                                                  │
                                         ClientHandlerController
                                                  │  model.addMessage()
                                                  ▼
                                             ChatRoom (Model)
                                                  │  serverController.broadcast()
                                                  ▼
                                         All Connected Clients
                                                  │
                                             ChatWindow (View)
                                          displayMessage(line)
```

---

## 🔷 SOLID Principles Applied

| Principle | Where Applied |
|---|---|
| **SRP** — Single Responsibility | Each class has one job: `Message` only holds data, `ChatWindow` only handles UI, `ClientController` only handles client logic |
| **OCP** — Open/Closed | New UI can implement `ChatView` without changing any controller |
| **LSP** — Liskov Substitution | `TcpNetworkAdapter` can substitute wherever `Sender` or `Receiver` is expected |
| **ISP** — Interface Segregation | `Sender` and `Receiver` are separate interfaces — clients only depend on what they need |
| **DIP** — Dependency Inversion | `ClientController` depends on `ChatView` interface, not `ChatWindow` directly; network layer depends on `Sender`/`Receiver` abstractions |

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher
- No external libraries needed

### Step 1 — Compile

```bash
cd mvc_chat/src
javac chat/*.java chat/model/*.java chat/view/*.java chat/controller/*.java chat/network/*.java
```

### Step 2 — Start the Server

```bash
java chat.ChatApplication server
```

Output:
```
=== MVC Chat Server Started on port 27015 ===
Waiting for clients...
```

### Step 3 — Start a Client (same machine)

```bash
java chat.ChatApplication
```

### Step 4 — Start a Client (different machine on same network)

```bash
java chat.ChatApplication client 192.168.1.5
```

> Replace `192.168.1.5` with the server's actual IP address.

You can open **multiple client windows** — all clients will see each other's messages in real time.

---

## ⚙️ Configuration

| Setting | Default | How to Change |
|---|---|---|
| Port | `27015` | Change the port number in `ChatApplication.java` |
| Default host | `localhost` | Pass host as second argument: `java chat.ChatApplication client <host>` |
| Username | Auto-assigned (`Client1`, `Client2`, ...) | Modify `ServerController.java` counter logic |

---

## ✨ Features

- **Real-time messaging** — messages broadcast to all connected clients instantly
- **Timestamped messages** — each message shows `[HH:mm:ss] Username: message`
- **Join/leave notifications** — server announces when a client joins or leaves
- **Multi-client support** — server handles multiple clients concurrently using threads
- **Swing GUI** — clean chat window with scrollable message area and send button
- **Enter key support** — press Enter or click Send to send a message
- **Status bar** — shows connection status at the top of the chat window

---

## 🧩 Key Classes Explained

### `ChatApplication.java`
Entry point. Checks command-line arguments to decide whether to start in **server mode** or **client mode**, then wires together the Model, View, and Controller.

### `Message.java`
Immutable data class. Stores sender name, message content, and auto-generated timestamp. Has a `format()` method that produces `[HH:mm:ss] Username: content`.

### `ChatRoom.java`
Server-side model. Maintains the list of connected users and the full message history during the session.

### `ChatView.java` (interface)
Defines the contract for any UI: `displayMessage()`, `displaySystemMessage()`, and `setOnSendListener()`. Decouples the controller from any specific UI framework.

### `ChatWindow.java`
Swing-based implementation of `ChatView`. Renders the chat area, input field, send button, and status label. All UI updates are dispatched on the Event Dispatch Thread via `SwingUtilities.invokeLater()`.

### `ClientController.java`
Handles the client side. Connects to the server via TCP, registers a send listener on the View, and starts a background daemon thread to receive incoming messages.

### `ServerController.java`
Listens on port 27015, accepts incoming client connections, creates a `ClientHandlerController` thread for each, and broadcasts messages to all connected clients.

### `ClientHandlerController.java`
Runs on a dedicated thread per client on the server side. Receives messages from one client, saves them to the `ChatRoom` model, and triggers a broadcast via `ServerController`.

### `TcpNetworkAdapter.java`
Implements both `Sender` and `Receiver` interfaces using `BufferedReader`/`BufferedWriter` over a TCP socket. This is the only class that touches raw socket I/O.

---

## 🛠️ Technologies Used

- **Java** (Core, Networking, Swing)
- **TCP Sockets** (`java.net.Socket`, `java.net.ServerSocket`)
- **Java Swing** — GUI framework
- **Java Threads** — for concurrent client handling and background receiving
- **`java.time.LocalTime`** — for message timestamps

---

## 📌 Notes

- The server runs in a headless console mode — no GUI on the server side.
- Each client is auto-assigned a username (`Client1`, `Client2`, etc.) by the server.
- The server must be started **before** any client attempts to connect.
- Messages are line-delimited over TCP (`BufferedReader.readLine()`).
