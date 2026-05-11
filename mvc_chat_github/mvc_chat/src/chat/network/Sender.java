package chat.network;

/**
 * SOLID: ISP - Interface Segregation
 * DIP - Depend on abstraction not concrete class
 */
public interface Sender {
    void send(String msg) throws Exception;
}
