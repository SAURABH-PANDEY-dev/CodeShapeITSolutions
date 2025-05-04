package app.view;

import javax.swing.*;

/**
 * ExportCSVWindow.java
 * This class represents the UI for exporting products to a CSV file.
 * Currently a placeholder for future implementation.
 */
public class ExportCSVWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Export Products to CSV");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Export to CSV (to be implemented)"));
        frame.add(panel);
        frame.setVisible(true);
    }
}