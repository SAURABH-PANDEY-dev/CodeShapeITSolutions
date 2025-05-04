package app.view;

import javax.swing.*;
import java.awt.*;
import app.controller.ProductController;
import app.view.LowStockWindow;

/**
 * MainWindow.java
 * This class builds the main menu window using Swing.
 * It contains buttons for each main operation in the inventory system.
 */
public class MainWindow {

    /**
     * Creates and displays the main menu UI.
     */
    public void createAndShowGUI() {
        // Create the main frame (window)
        JFrame frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Create a panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2,30,10)); // 7 rows, 1 column, spacing = 10px

        // Create buttons for each operation
        JButton addButton = new JButton("Add Product");
        JButton viewButton = new JButton("View All Products");
        JButton searchButton = new JButton("Search Product by ID");
        JButton updateButton = new JButton("Update Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton exportButton = new JButton("Export Products to CSV");
        JButton lowStockBtn = new JButton("Low Stock Alerts");
        JButton exitButton = new JButton("Exit");

// Styling for the buttons

        // Add product button
        addButton.setFont(new Font("Fira Code",Font.BOLD,16));
        addButton.setBackground(new Color(191,153,143));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //view button
        viewButton.setBackground(new Color(191,153,143));
        viewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewButton.setFont(new Font("Fira Code",Font.BOLD,16));

        // search button
        searchButton.setBackground(new Color(191,153,143));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setFont(new Font("Fira Code",Font.BOLD,16));

        // update button
        updateButton.setBackground(new Color(191,153,143));
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateButton.setFont(new Font("Fira Code",Font.BOLD,16));

        // delete button
        deleteButton.setBackground(new Color(191,153,143));
        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setFont(new Font("Fira Code",Font.BOLD,16));

        // export button
        exportButton.setBackground(new Color(191,153,143));
        exportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setFont(new Font("Fira Code",Font.BOLD,16));

        // low stock button
        lowStockBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lowStockBtn.setForeground(new Color(58,62,77));
        lowStockBtn.setBackground(new Color(220, 53, 69)); // Bootstrap Danger Red
        lowStockBtn.setFocusPainted(false);
        lowStockBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lowStockBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        lowStockBtn.setToolTipText("View products that are low in stock");

        // exit button
        exitButton.setBackground(new Color(191,153,143));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setFont(new Font("Fira Code",Font.BOLD,16));

// Add action
        lowStockBtn.addActionListener(e -> new LowStockWindow());

// Add to the panel
        panel.add(lowStockBtn);


        // Add action listeners to each button
        addButton.addActionListener(e -> ProductController.showAddProductWindow());
        viewButton.addActionListener(e -> ProductController.showViewAllProducts());
        searchButton.addActionListener(e -> ProductController.showSearchProductWindow());
        updateButton.addActionListener(e -> ProductController.showUpdateProductWindow());
        deleteButton.addActionListener(e -> ProductController.showDeleteProductWindow());
        exportButton.addActionListener(e -> ProductController.exportProductsToCSV());
        exitButton.addActionListener(e -> System.exit(0)); // Closes the app

        // Add buttons to the panel
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(searchButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(exportButton);
        panel.add(exitButton);

        // Add panel to the frame
        frame.add(panel);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true); // Show the frame
    }
}