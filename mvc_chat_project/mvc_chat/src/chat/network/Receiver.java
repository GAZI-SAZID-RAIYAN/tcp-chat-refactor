package chat.network;

/**
 * SOLID: ISP - Interface Segregation
 */
public interface Receiver {
    String receive() throws Exception;
}
