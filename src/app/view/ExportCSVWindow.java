package app.view;

import app.controller.ProductController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ExportCSVWindow.java
 * This class represents the UI for exporting all products to a CSV file.
 */
public class ExportCSVWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Export Products to CSV");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton exportButton = new JButton("Export to CSV");
        JLabel messageLabel = new JLabel("Click the button to export products.csv", SwingConstants.CENTER);

        panel.add(exportButton, BorderLayout.CENTER);
        panel.add(messageLabel, BorderLayout.SOUTH);

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductController controller = new ProductController();
                boolean success = controller.exportProductsToCSV("products.csv");

                if (success) {
                    messageLabel.setText("✅ Exported successfully to products.csv");
                } else {
                    messageLabel.setText("❌ Failed to export. Check console.");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}