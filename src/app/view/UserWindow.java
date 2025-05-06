package app.view;

import javax.swing.*;
import java.awt.*;

/**
 * UserWindow.java
 * ---------------------------
 * UI window for the regular user in the Inventory Management System.
 * The user can view inventory and perform basic operations.
 *
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class UserWindow extends JFrame {

    public UserWindow() {
        // Set window properties
        setTitle("User - Inventory System");
        setSize(600, 400); // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false); // Disable resizing the window

        initUI(); // Initialize the UI components
        setVisible(true); // Make the window visible
    }

    /**
     * Initializes the UI components for the User window.
     * This includes buttons and labels specific to the User.
     */
    private void initUI() {
        // Panel to hold components with GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, User!", JLabel.CENTER);
        panel.add(welcomeLabel);

        // Button to view inventory
        JButton viewInventoryButton = new JButton("View Inventory");
        viewInventoryButton.addActionListener(e -> viewInventory()); // Action to view inventory
        panel.add(viewInventoryButton);

        // Add panel to the window
        add(panel);
    }

    /**
     * This method handles the event when the 'View Inventory' button is clicked.
     * It can open a new window or display the inventory list.
     */
    private void viewInventory() {
        JOptionPane.showMessageDialog(this, "Viewing inventory functionality goes here.");
    }
}
