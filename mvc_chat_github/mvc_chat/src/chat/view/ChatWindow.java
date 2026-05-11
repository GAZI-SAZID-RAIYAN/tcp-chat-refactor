package chat.view;

import javax.swing.*;
import java.awt.*;

/**
 * VIEW - Swing GUI implementation
 * MVC Role: V (View)
 * SOLID: SRP - Only handles UI display
 * SOLID: OCP - New UI can implement ChatView without changing controller
 */
public class ChatWindow implements ChatView {

    private final JTextArea  chatArea;
    private final JTextField inputField;
    private final JLabel     statusLabel;
    private       MessageListener listener;

    public ChatWindow(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // ── Status bar (top) ──
        statusLabel = new JLabel(" Connecting...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(statusLabel, BorderLayout.NORTH);

        // ── Chat area (center) ──
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        chatArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // ── Input panel (bottom) ──
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 13));
        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(59, 130, 246));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // ── Send action ──
        sendButton.addActionListener(e -> handleSend());
        inputField.addActionListener(e -> handleSend());

        frame.setVisible(true);
    }

    private void handleSend() {
        String msg = inputField.getText().trim();
        if (!msg.isEmpty() && listener != null) {
            listener.onSend(msg);
            inputField.setText("");
        }
    }

    @Override
    public void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    @Override
    public void displaySystemMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append("*** " + message + " ***\n");
            statusLabel.setText(" " + message);
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    @Override
    public void setOnSendListener(MessageListener listener) {
        this.listener = listener;
    }
}
