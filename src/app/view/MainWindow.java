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
                "Dashboard", "Add Product", "View All Products", "Search Product by ID",
                "Update Product", "Delete Product", "Delete All Products",
                "Export Products to CSV", "Import Products from CSV",
                "Low Stock Alerts", "Restock Products", "Record Sale","View Sales History",
                "View Sales Analysis",
                "Exit (Close the Whole App)"
        };


        for (String label : buttonLabels) {
            JButton btn = new JButton(label);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setPreferredSize(new Dimension(300, 45));

            // Custom background colors for specific buttons
            if (label.equals("Low Stock Alerts")) {
                btn.setBackground(new Color(192, 57, 43)); // Red
            } else if (label.equals("Dashboard")) {
                btn.setBackground(new Color(39, 174, 96)); // Green
            } else if (label.equals("Delete All Products")) {
                btn.setBackground(new Color(124,68,173));
            } else {
                btn.setBackground(new Color(41, 128, 185)); // Blue
            }

            // Attach actions
            switch (label) {
                case "Dashboard" -> btn.addActionListener(e -> new DashboardWindow());
                case "Add Product" -> btn.addActionListener(e -> ProductController.showAddProductWindow());
                case "View All Products" -> btn.addActionListener(e -> ProductController.showViewAllProducts());
                case "Search Product by ID" -> btn.addActionListener(e -> ProductController.showSearchProductWindow());
                case "Update Product" -> btn.addActionListener(e -> ProductController.showUpdateProductWindow());
                case "Delete Product" -> btn.addActionListener(e -> ProductController.showDeleteProductWindow());
                case "Export Products to CSV" -> btn.addActionListener(e -> ProductController.exportProductsToCSV());
                case "Low Stock Alerts" -> btn.addActionListener(e -> new LowStockWindow());
                case "Restock Products" -> btn.addActionListener(e -> ProductController.showRestockProductsWindow());
                case "Import Products from CSV" -> btn.addActionListener(e -> new ImportCSVWindow());
                case "Record Sale" -> btn.addActionListener(e -> new RecordSaleWindow());
                case "View Sales History" -> btn.addActionListener(e -> new ViewSalesWindow());
                case "View Sales Analysis" -> btn.addActionListener(e -> new SalesAnalyticsWindow());
                case "Delete All Products" -> btn.addActionListener(e -> new app.ui.DeleteAllConfirmationWindow());
                case "Exit (Close the Whole App)" -> btn.addActionListener(e -> System.exit(0));
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