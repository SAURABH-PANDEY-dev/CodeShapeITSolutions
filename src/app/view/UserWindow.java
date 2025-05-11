package app.view;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * UserWindow.java
 * ---------------------------
 * UI window for the regular user in the Inventory Management System.
 * The user can view inventory and perform basic operations.
 * <p>
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class UserWindow extends JFrame {

    public UserWindow() {
        setTitle("User - Inventory System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        // === COLORS ===
        Color bgColor = new Color(20, 20, 20);
        Color buttonBg = new Color(50, 50, 50);
        Color buttonHover = new Color(70, 70, 70);
        Color textColor = Color.WHITE;

        // === MAIN PANEL (BorderLayout) ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        // === CENTER PANEL (GridLayout for content) ===
        JPanel panel = new JPanel(new GridLayout(3, 1, 20, 20));
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // === WELCOME LABEL ===
        JLabel welcomeLabel = new JLabel("Welcome, User!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        welcomeLabel.setForeground(textColor);
        panel.add(welcomeLabel);

        // === ICON SETUP ===
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/icons/manage-inventory.png")));
        Image scaledImage = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        // === VIEW INVENTORY BUTTON ===
        JButton viewInventoryButton = new JButton(icon);
        viewInventoryButton.setToolTipText("View Inventory");
        viewInventoryButton.setBackground(buttonBg);
        viewInventoryButton.setBorderPainted(false);
        viewInventoryButton.setFocusPainted(false);
        viewInventoryButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        viewInventoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewInventoryButton.setBackground(buttonHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewInventoryButton.setBackground(buttonBg);
            }
        });

        viewInventoryButton.addActionListener(e -> viewInventory());
        panel.add(viewInventoryButton);

        // Spacer
        panel.add(Box.createVerticalStrut(30));
        mainPanel.add(panel, BorderLayout.CENTER);

        // === LOGOUT BUTTON PANEL (Bottom-right) ===
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(bgColor);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(textColor);
        logoutButton.setBackground(buttonBg);
        logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setToolTipText("Logout");

        // Hover effect
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(buttonHover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(buttonBg);
            }
        });

        logoutButton.addActionListener(e -> logout());
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "You have been logged out.");
        dispose(); // Close current window
        SwingUtilities.invokeLater(LoginWindow::new); // Return to log in window
        dispose();
    }

    private void viewInventory() {
        SwingUtilities.invokeLater(() -> new MainWindow("user"));
        dispose();
    }
}