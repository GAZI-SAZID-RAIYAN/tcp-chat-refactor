package chat.view;

/**
 * VIEW Interface
 * MVC Role: V (View)
 * Any UI must implement this interface
 */
public interface ChatView {
    void displayMessage(String message);
    void displaySystemMessage(String message);
    void setOnSendListener(MessageListener listener);

    @FunctionalInterface
    interface MessageListener {
        void onSend(String message);
    }
}
