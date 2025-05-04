package app.view;

import javax.swing.*;

/**
 * SearchProductWindow.java
 * This class represents the UI for searching a product by ID.
 * Currently, a placeholder for future implementation.
 */
public class SearchProductWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Search Product by ID");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Search Product by ID (to be implemented)"));
        frame.add(panel);
        frame.setVisible(true);
    }
}