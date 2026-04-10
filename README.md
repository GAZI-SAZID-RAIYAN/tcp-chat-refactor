**Assignment 2: Refactoring into Java using SOLID Principles**

In this assignment, the original C-based TCP chat application was refactored into Java by applying object-oriented programming concepts and adhering to SOLID design principles. The primary goal of this task was to transform a procedural, tightly coupled network program into a modular, extensible, and maintainable system using modern software engineering practices.

The original implementation in C followed a procedural approach where networking, message handling, and client management logic were closely intertwined. This made the system harder to scale, test, and modify. By refactoring the application into Java, the system was redesigned using classes, interfaces, and abstraction layers to improve code organization and flexibility.

The Java implementation is located in the following directory:

java-refactor/src/chat/

**Objective of the Given Prompt**

The prompt for this assignment required taking an existing procedural TCP chat application written in C and redesigning it in Java using object-oriented principles. Specifically, the task emphasized:

Applying SOLID principles in the design
Breaking down the system into smaller, responsibility-driven classes
Using interfaces to decouple high-level and low-level modules
Ensuring the system is easy to extend (e.g., supporting new protocols or features)
Maintaining the original functionality while improving structure and readability


**SOLID Principles Applied
SRP – Single Responsibility Principle**

Each class in the system is designed to have only one responsibility, ensuring better readability and easier maintenance. For example, the Message class is solely responsible for handling message data such as content, sender, and timestamp. It does not deal with networking or UI logic.

**OCP – Open/Closed Principle**

The system is open for extension but closed for modification. The server architecture allows new features or network implementations to be added without changing the existing core logic. For instance, new types of network adapters can be introduced without altering the server code.

**LSP – Liskov Substitution Principle**

Subclasses or implementations can replace their base types without affecting system correctness. For example, the TcpNetworkAdapter can be replaced with another implementation (such as a UDP adapter or a mock adapter for testing) without breaking the functionality of the application.

**ISP – Interface Segregation Principle**

Instead of using large, general-purpose interfaces, the system uses smaller, more specific ones. Networking responsibilities are split into interfaces such as Sender and Receiver, ensuring that classes only implement methods they actually need.

**DIP – Dependency Inversion Principle**

High-level modules such as ChatServer and ClientHandler depend on abstractions (interfaces) rather than concrete implementations. This reduces coupling and makes the system easier to test and extend. For example, the server interacts with network functionality through interfaces instead of directly using socket classes.

**System Design Overview**

The refactored system is composed of several key components:

ChatApplication: Entry point of the program that initializes either the server or client based on user input.
ChatServer: Manages incoming client connections and coordinates message distribution.
ClientHandler: Handles communication with a single client.
Message: Represents the structure of a chat message.
Network Interfaces (Sender, Receiver): Define communication behavior.
TcpNetworkAdapter: Concrete implementation of networking using TCP.

This modular design improves separation of concerns and makes the application easier to understand and extend.

**How to Compile and Run the Java Version**

To compile the Java source files, navigate to the project directory and run:

javac chat/*.java

To start the server:

java chat.ChatApplication server

To start a client:

java chat.ChatApplication

Multiple clients can be run simultaneously to test real-time communication with the server.

**Conclusion**

This refactoring task demonstrates how a procedural program can be transformed into a well-structured object-oriented system using SOLID principles. The resulting Java implementation is more scalable, maintainable, and flexible compared to the original C version. It also provides a strong foundation for future enhancements such as adding encryption, GUI support, or alternative communication protocols.
