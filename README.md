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
