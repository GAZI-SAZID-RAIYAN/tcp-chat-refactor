// server.c
#define _WIN32_WINNT 0x0601

#include <winsock2.h>
#include <ws2tcpip.h>
#include <process.h>
#include <stdio.h>
#include <stdlib.h>

#pragma comment(lib, "Ws2_32.lib")

#define DEFAULT_PORT "27015"
#define BUFFER_SIZE 512
#define MAX_CLIENTS 10

typedef struct {
    SOCKET socket;
    int id;
} Client;

Client* clientList[MAX_CLIENTS];
int clientCount = 0;

/* Broadcast message to all connected clients */
void broadcastMessage(const char* message) {
    for (int i = 0; i < clientCount; ++i) {
        send(clientList[i]->socket, message, (int)strlen(message), 0);
    }
}

/* Thread function for handling each client */
unsigned int __stdcall clientHandler(void* arg) {
    Client* client = (Client*)arg;
    char recvBuffer[BUFFER_SIZE];
    int bytesReceived;

    printf("Client %d connected.\n", client->id);

    while (1) {
        ZeroMemory(recvBuffer, BUFFER_SIZE);

        bytesReceived = recv(client->socket, recvBuffer, BUFFER_SIZE, 0);

        if (bytesReceived > 0) {
            printf("Client %d: %s\n", client->id, recvBuffer);

            char formattedMsg[BUFFER_SIZE + 50];
            sprintf(formattedMsg, "Client %d: %s", client->id, recvBuffer);

            broadcastMessage(formattedMsg);
        } else {
            printf("Client %d disconnected.\n", client->id);
            break;
        }
    }

    closesocket(client->socket);

    /* Remove client from list */
    for (int i = 0; i < clientCount; i++) {
        if (clientList[i]->id == client->id) {
            for (int j = i; j < clientCount - 1; j++)
                clientList[j] = clientList[j + 1];

            clientCount--;
            break;
        }
    }

    free(client);
    return 0;
}

int main(void) {
    WSADATA wsaData;
    SOCKET listenSocket = INVALID_SOCKET;
    struct addrinfo* result = NULL;
    struct addrinfo hints;

    printf("Starting TCP Chat Server...\n");

    /* Initialize WinSock */
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        printf("WSAStartup failed\n");
        return 1;
    }

    ZeroMemory(&hints, sizeof(hints));
    hints.ai_family = AF_INET;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_protocol = IPPROTO_TCP;
    hints.ai_flags = AI_PASSIVE;

    /* Resolve server address and port */
    if (getaddrinfo(NULL, DEFAULT_PORT, &hints, &result) != 0) {
        printf("getaddrinfo failed\n");
        WSACleanup();
        return 1;
    }

    /* Create listening socket */
    listenSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
    if (listenSocket == INVALID_SOCKET) {
        printf("Socket creation failed\n");
        WSACleanup();
        return 1;
    }

    /* Bind socket */
    if (bind(listenSocket, result->ai_addr, (int)result->ai_addrlen) == SOCKET_ERROR) {
        printf("Bind failed\n");
        closesocket(listenSocket);
        WSACleanup();
        return 1;
    }

    freeaddrinfo(result);

    /* Start listening */
    if (listen(listenSocket, SOMAXCONN) == SOCKET_ERROR) {
        printf("Listen failed\n");
        closesocket(listenSocket);
        WSACleanup();
        return 1;
    }

    printf("Server is waiting for clients on port %s...\n", DEFAULT_PORT);

    /* Accept clients */
    while (1) {
        SOCKET clientSocket = accept(listenSocket, NULL, NULL);

        if (clientSocket != INVALID_SOCKET && clientCount < MAX_CLIENTS) {
            Client* client = (Client*)malloc(sizeof(Client));
            client->socket = clientSocket;
            client->id = clientCount + 1;

            clientList[clientCount++] = client;

            _beginthreadex(NULL, 0, clientHandler, client, 0, NULL);
        }
    }

    closesocket(listenSocket);
    WSACleanup();
    return 0;
}