TCP Chat Application in C

**Assignment 1**

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

