package app.view;

import javax.swing.*;
import java.awt.*;
import app.controller.ProductController;

/**
 * MainWindow.java
 * Modern, styled main menu for the Inventory Management System.
 */
public class MainWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);

        // Title Panel
        JLabel titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(44, 62, 80)); // Dark Blue-Gray
        titlePanel.setPreferredSize(new Dimension(550, 60));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(52, 73, 94));
        buttonPanel.setLayout(new GridLayout(0, 1, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        String[] buttonLabels = {
                "Add Product", "View All Products", "Search Product by ID",
                "Update Product", "Delete Product", "Export Products to CSV",
                "Low Stock Alerts", "Restock Products", "Exit"
        };

        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setForeground(Color.WHITE);
            btn.setBackground(label.equals("Low Stock Alerts") ? new Color(192, 57, 43) : new Color(41, 128, 185));
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(300, 45));

            // Attach actions
            switch (label) {
                case "Add Product" -> btn.addActionListener(e -> ProductController.showAddProductWindow());
                case "View All Products" -> btn.addActionListener(e -> ProductController.showViewAllProducts());
                case "Search Product by ID" -> btn.addActionListener(e -> ProductController.showSearchProductWindow());
                case "Update Product" -> btn.addActionListener(e -> ProductController.showUpdateProductWindow());
                case "Delete Product" -> btn.addActionListener(e -> ProductController.showDeleteProductWindow());
                case "Export Products to CSV" -> btn.addActionListener(e -> ProductController.exportProductsToCSV());
                case "Low Stock Alerts" -> btn.addActionListener(e -> new LowStockWindow());
                case "Restock Products" -> btn.addActionListener(e -> ProductController.showRestockProductsWindow());
                case "Exit" -> btn.addActionListener(e -> System.exit(0));

            }

            buttonPanel.add(btn);
        }

        // Frame Layout
        frame.setLayout(new BorderLayout());
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}