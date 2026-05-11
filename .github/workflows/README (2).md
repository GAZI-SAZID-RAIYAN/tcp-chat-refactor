# 💬 MVCChat — TCP Chat Application

A real-time TCP-based chat application built in **Java**, following the **MVC (Model-View-Controller)** architectural pattern and **SOLID** design principles.

---

## 🚀 How to Run (Windows)

### Step 1 — Start the Server

Open **Command Prompt** (CMD) and run:

```cmd
cd "C:\Program Files\MVCChat"
MVCChat.exe server
```

You should see:
```
=== MVC Chat Server Started on port 27015 ===
Waiting for clients...
```

> ⚠️ **The server must be running before any client can connect.**

---

### Step 2 — Start a Client

Press the **Windows key** on your keyboard, type **MVCChat**, and press Enter.

The chat window will open and connect to the server automatically.

> 💡 You can open **multiple client windows** — all clients will see each other's messages in real time!

---

### Step 3 — Connect from Another Computer (Same Network)

On the other computer, open CMD and run:

```cmd
cd "C:\Program Files\MVCChat"
MVCChat.exe client 192.168.1.5
```

> Replace `192.168.1.5` with the **IP address of the computer running the server**.

---

## ✨ Features

- 🔴 Real-time messaging — broadcast to all connected clients instantly
- 🕐 Timestamped messages — each message shows `[HH:mm:ss] Username: message`
- 👋 Join/leave notifications — server announces when a client connects or disconnects
- 👥 Multi-client support — multiple users can chat simultaneously
- 🖥️ Swing GUI — clean chat window with scrollable message area
- ⌨️ Press **Enter** or click **Send** to send a message

---

## ⚙️ Configuration

| Setting | Default | How to Change |
|---|---|---|
| Port | `27015` | Change in source code (`ChatApplication.java`) |
| Default host | `localhost` | Pass host as argument: `MVCChat.exe client <ip>` |
| Username | Auto-assigned (`Client1`, `Client2`, ...) | Assigned by server automatically |

---

## 🏛️ Architecture

Built with the **MVC pattern**:

| Layer | Responsibility |
|---|---|
| **Model** | Stores and manages chat data (`Message`, `ChatRoom`) |
| **View** | Displays messages and captures input (`ChatWindow`) |
| **Controller** | Bridges View ↔ Model ↔ Network (`ClientController`, `ServerController`) |
| **Network** | Handles raw TCP socket I/O (`TcpNetworkAdapter`) |

---

## 🛠️ Technologies

- **Java** (Core, Networking, Swing)
- **TCP Sockets** — `java.net.Socket` / `java.net.ServerSocket`
- **Java Swing** — GUI framework
- **Java Threads** — concurrent client handling
- **jpackage** — native Windows installer bundled with JRE

---

## 📌 Notes

- The server runs in **console mode** — no GUI on the server side.
- The client must be installed via the `.exe` installer before running.
- Java does **not** need to be separately installed — the JRE is bundled inside the installer.
