package app.view;

import javax.swing.*;

/**
 * UpdateProductWindow.java
 * This class represents the UI for updating a product's details.
 * Currently a placeholder for future implementation.
 */
public class UpdateProductWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Update Product");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Update Product (to be implemented)"));
        frame.add(panel);
        frame.setVisible(true);
    }
}