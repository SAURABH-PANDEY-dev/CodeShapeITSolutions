package app.view;

import javax.swing.*;

/**
 * DeleteProductWindow.java
 * This class represents the UI for deleting a product from the inventory.
 * Currently a placeholder for future implementation.
 */
public class DeleteProductWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Delete Product");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Delete Product (to be implemented)"));
        frame.add(panel);
        frame.setVisible(true);
    }
}