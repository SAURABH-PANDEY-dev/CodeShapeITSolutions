package app.view;

import javax.swing.*;
import java.awt.*;
import app.controller.ProductController;

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
        panel.setLayout(new GridLayout(7, 1, 10, 10)); // 7 rows, 1 column, spacing = 10px

        // Create buttons for each operation
        JButton addButton = new JButton("Add Product");
        JButton viewButton = new JButton("View All Products");
        JButton searchButton = new JButton("Search Product by ID");
        JButton updateButton = new JButton("Update Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton exportButton = new JButton("Export Products to CSV");
        JButton exitButton = new JButton("Exit");

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