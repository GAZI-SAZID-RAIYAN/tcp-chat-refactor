// client_gui.c
#define _WIN32_WINNT 0x0601

#include <windows.h>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <process.h>
#include <stdio.h>

#pragma comment(lib, "Ws2_32.lib")

#define BUFFER_SIZE 512

SOCKET connectSocket = INVALID_SOCKET;
HWND hInputBox, hOutputBox;

/* Append text to output edit control */
void appendText(HWND hEdit, const char* text) {
    int length = GetWindowTextLength(hEdit);
    SendMessage(hEdit, EM_SETSEL, (WPARAM)length, (LPARAM)length);
    SendMessage(hEdit, EM_REPLACESEL, 0, (LPARAM)text);
}

/* Thread to receive messages from server */
unsigned __stdcall receiveThread(void* arg) {
    char recvBuffer[BUFFER_SIZE];
    int bytesReceived;

    while (1) {
        ZeroMemory(recvBuffer, BUFFER_SIZE);

        bytesReceived = recv(connectSocket, recvBuffer, BUFFER_SIZE, 0);

        if (bytesReceived > 0) {
            strcat(recvBuffer, "\r\n");
            appendText(hOutputBox, recvBuffer);
        } else {
            appendText(hOutputBox, "Disconnected from server.\r\n");
            break;
        }
    }

    return 0;
}

/* Window procedure */
LRESULT CALLBACK windowProcedure(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam) {
    char buffer[BUFFER_SIZE];

    switch (msg) {
    case WM_COMMAND:
        if (LOWORD(wParam) == 1) {  // Send button
            GetWindowTextA(hInputBox, buffer, BUFFER_SIZE);

            if (strlen(buffer) > 0) {
                send(connectSocket, buffer, (int)strlen(buffer), 0);

                char selfMessage[BUFFER_SIZE + 10];
                sprintf(selfMessage, "Me: %s\r\n", buffer);
                appendText(hOutputBox, selfMessage);

                SetWindowTextA(hInputBox, "");
            }
        }
        break;

    case WM_DESTROY:
        closesocket(connectSocket);
        WSACleanup();
        PostQuitMessage(0);
        return 0;
    }

    return DefWindowProc(hwnd, msg, wParam, lParam);
}

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
                   LPSTR lpCmdLine, int nCmdShow) {

    WSADATA wsaData;
    struct sockaddr_in serverAddress;

    /* Initialize WinSock */
    WSAStartup(MAKEWORD(2, 2), &wsaData);

    connectSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (connectSocket == INVALID_SOCKET) {
        MessageBox(NULL, "Socket creation failed", "Error", MB_OK);
        return 1;
    }

    serverAddress.sin_family = AF_INET;
    serverAddress.sin_port = htons(27015);
    serverAddress.sin_addr.s_addr = inet_addr("127.0.0.1");

    /* Connect to server */
    if (connect(connectSocket, (struct sockaddr*)&serverAddress, sizeof(serverAddress)) == SOCKET_ERROR) {
        MessageBox(NULL, "Could not connect to server", "Error", MB_OK);
        return 1;
    }

    _beginthreadex(NULL, 0, receiveThread, NULL, 0, NULL);

    /* Register window class */
    const char CLASS_NAME[] = "ChatClientWindow";
    WNDCLASS wc = { 0 };
    wc.lpfnWndProc = windowProcedure;
    wc.hInstance = hInstance;
    wc.lpszClassName = CLASS_NAME;

    RegisterClass(&wc);

    /* Create main window */
    HWND hwnd = CreateWindowEx(
        0,
        CLASS_NAME,
        "TCP Chat Client",
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT,
        400, 300,
        NULL, NULL, hInstance, NULL);

    /* Create UI controls */
    hInputBox = CreateWindow(
        "EDIT", "",
        WS_CHILD | WS_VISIBLE | WS_BORDER | ES_AUTOHSCROLL,
        20, 20, 340, 25,
        hwnd, NULL, hInstance, NULL);

    hOutputBox = CreateWindow(
        "EDIT", "",
        WS_CHILD | WS_VISIBLE | WS_BORDER |
        ES_MULTILINE | ES_AUTOVSCROLL | ES_READONLY,
        20, 60, 340, 150,
        hwnd, NULL, hInstance, NULL);

    CreateWindow(
        "BUTTON", "Send",
        WS_TABSTOP | WS_VISIBLE | WS_CHILD | BS_DEFPUSHBUTTON,
        150, 220, 80, 30,
        hwnd, (HMENU)1, hInstance, NULL);

    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);

    /* Message loop */
    MSG msg = { 0 };
    while (GetMessage(&msg, NULL, 0, 0)) {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }

    return 0;
}