TCP Chat Application in C
Software Engineering Assignment 1

Name: Gazi Sazid Raiyan
Student ID: 240211
Name: Md Hasan
Student ID: 240227

Project Description
-------------------
This project implements a TCP-based multi-client chat system using the C programming language and WinSock API.

The system consists of:
1. A server application that accepts multiple clients and broadcasts messages.
2. A graphical client application built using the Windows API.

Project Features
----------------
- Multi-client communication
- Message broadcasting
- GUI client with message input and output
- Threaded client handling on the server

File Structure
--------------
c-original/
    server.c
    client_gui.c

docs/
    STYLE_GUIDE.txt
    AI_PROMPTS.txt

Compilation
-----------
Server:
gcc server.c -o server.exe -lws2_32

Client:
gcc client_gui.c -o client_gui.exe -lws2_32 -mwindows

How to Run
----------
1. Start the server:
   server.exe

2. Run one or more clients:
   client_gui.exe

3. Type a message in the client and press Send.

Design Overview
---------------
The server uses threads to handle each connected client.
Messages received from one client are broadcast to all connected clients.

AI Assistance
-------------
AI tools were used to:
- Improve code formatting
- Generate a coding style guide
- Document the development process

 Documentation
-------------
All documentation files are located in the docs/ directory.
These documents apply to both the original C implementation and future refactored versions.



----------------------------------------
Assignment 2: Refactoring into Java using SOLID
----------------------------------------

In this assignment, the original C TCP chat application was refactored into Java
using object-oriented design and SOLID principles.

The purpose of this task was to transform a procedural network program into a
modular, extensible and maintainable object-oriented architecture.

The Java implementation is located in:
java-refactor/src/chat/

----------------------------------------
SOLID Principles Applied
----------------------------------------

SRP - Single Responsibility Principle
Each class has only one responsibility.
Example:
Message class only handles message data.

OCP - Open Closed Principle
The server is designed so that new network implementations can be added
without modifying existing server logic.

LSP - Liskov Substitution Principle
The TcpNetworkAdapter can be replaced by another network adapter without
breaking the system.

ISP - Interface Segregation Principle
Network operations were separated into small interfaces:
Sender and Receiver.

DIP - Dependency Inversion Principle
High-level modules like ChatServer and ClientHandler depend on
interfaces instead of concrete network classes.

----------------------------------------
How to Compile and Run the Java Version
----------------------------------------

Compile:
javac chat/*.java

Run server:
java chat.ChatApplication server

Run client:
java chat.ChatApplication
