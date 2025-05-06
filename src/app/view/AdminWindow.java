package app.view;

import javax.swing.*;
import java.awt.*;

/**
 * AdminWindow.java
 * --------------------------
 * UI window for the Admin user in the Inventory Management System.
 * The admin can manage users and perform advanced operations.
 *
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class AdminWindow extends JFrame {

    public AdminWindow() {
        // Set window properties
        setTitle("Admin - Inventory System");
        setSize(600, 400); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false); // Disable resizing the window

        initUI(); // Initialize the UI components
        setVisible(true); // Make the window visible
    }

    /**
     * Initializes the UI components for the Admin window.
     * This includes buttons and labels specific to the Admin.
     */
    private void initUI() {
        // Panel to hold components with GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, Admin!", JLabel.CENTER);
        panel.add(welcomeLabel);

        // Button to manage users
        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.addActionListener(e -> manageUsers()); // Action to manage users
        panel.add(manageUsersButton);

        // Button for additional operations, like viewing inventory
        JButton viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.addActionListener(e -> viewInventory()); // Action to view inventory
        panel.add(viewInventoryButton);

        // Add panel to the window
        add(panel);
    }

    /**
     * This method handles the event when the 'Manage Users' button is clicked.
     * It can open a new window for managing users or perform other actions.
     */
    /**
     * Opens the Manage Users window.
     */
    private void manageUsers() {
        SwingUtilities.invokeLater(() -> {
            new app.ui.ManageUsersWindow().setVisible(true);
        });
    }


    /**
     * This method handles the event when the 'View Inventory' button is clicked.
     * It can open a new window or display the inventory list.
     */
    private void viewInventory() {
        JOptionPane.showMessageDialog(this, "Viewing inventory functionality goes here.");
    }
}
