**💬 TCP Chat Application in C**

**📌 Assignment 1**

👤 Name: Gazi Sazid Raiyan
🆔 Student ID: 240211

👤 Name: Md Hasan
🆔 Student ID: 240227

**📖 Project Description**

This project demonstrates the implementation of a TCP-based multi-client chat system using the C programming language and the WinSock API.

The system enables real-time communication between multiple users connected to a central server. It showcases fundamental networking concepts such as socket programming, concurrency, and message broadcasting.

The project is divided into two main components:

🖥️ Server Application – Handles multiple client connections and message distribution
💻 Graphical Client Application – Provides a user-friendly interface for sending and receiving messages


**✨ Key Features**

🔗 Multi-client Communication – Supports multiple users simultaneously
📡 Message Broadcasting – Messages from one client are sent to all connected clients
🧵 Threaded Server Design – Each client is handled in a separate thread for efficiency
🪟 Windows GUI Client – Built using the Windows API for an interactive experience
⚡ Real-time Messaging – Instant communication over TCP protocol


**🗂️ Project Structure**

c-original/

│── server.c                   

│── client_gui.c               

docs/

│── STYLE_GUIDE.txt             

│── AI_PROMPTS.txt            


**⚙️ Compilation Instructions**

Make sure you have GCC installed with WinSock support.

🔧 Compile Server
gcc server.c -o server.exe -lws2_32
🔧 Compile Client
gcc client_gui.c -o client_gui.exe -lws2_32 -mwindows


**▶️ How to Run**


Start the server:

server.exe

Launch one or more clients:

client_gui.exe
Type a message in the client interface and click Send.
Messages will be broadcast to all connected clients in real time.


**🧠 Design Overview**

The system follows a client-server architecture:

The server listens for incoming client connections.
Each client is managed using a separate thread, allowing concurrent communication.
When a client sends a message, the server broadcasts it to all connected clients.
The GUI client handles user input and displays incoming messages dynamically.

**🤖 AI Assistance**

AI tools were used during the development and documentation of this project. Below are some of the key prompts used:

✨ "Improve the formatting and readability of my C code for a TCP chat server."
🧾 "Generate a clean and consistent coding style guide for a C socket programming project."
📖 "Write documentation explaining a multi-client TCP chat application in C using WinSock."
🛠️ "Help fix issues in a threaded server handling multiple clients."
🎨 "Make my GitHub README more professional and well-structured."

****📚 Documentation**
**
All supporting documentation is available in the docs/ directory.
These resources apply to both the current implementation and any future improvements or refactoring.

**🚀 Future Improvements**

🔒 Add user authentication
🌐 Support cross-platform clients (Linux/macOS)
📁 Enable file sharing between clients
🎨 Improve GUI design and usability
