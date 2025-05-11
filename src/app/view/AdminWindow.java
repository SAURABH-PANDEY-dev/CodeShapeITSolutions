package app.view;

import javax.swing.*;
import java.awt.*;

/**
 * AdminWindow.java
 * --------------------------
 * Author: Saurabh Pandey
 * Date: 07 May 2025
 */
public class AdminWindow extends JFrame {

    public AdminWindow() {
        setTitle("Admin - Inventory System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    /**
     * Initializes the Admin UI with icon-based buttons and tooltips.
     */
    private void initUI() {
        // === MAIN CONTENT PANEL ===
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(34, 40, 49));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // === WELCOME LABEL ===
        JLabel welcomeLabel = new JLabel("Welcome, Admin!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(255,255,255));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(welcomeLabel);

        // === MANAGE USERS BUTTON ===
        String MANAGE_USERS_ICON = "resources/icons/manage-users.png";
        JButton manageUsersBtn = createIconButton(MANAGE_USERS_ICON, "Manage Users");
        manageUsersBtn.addActionListener(_ -> manageUsers());
        manageUsersBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(manageUsersBtn);

        // === INVENTORY BUTTON ===
        String INVENTORY_ICON = "resources/icons/manage-inventory.png";
        JButton viewInventoryBtn = createIconButton(INVENTORY_ICON, "View Inventory");
        viewInventoryBtn.addActionListener(e -> viewInventory());
        mainPanel.add(Box.createVerticalStrut(20));
        viewInventoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(viewInventoryBtn);

        // === WRAPPER PANEL WITH BORDER LAYOUT ===
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(34, 40, 49));
        wrapper.add(mainPanel, BorderLayout.CENTER);

        // === LOGOUT BUTTON PANEL ===
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBackground(new Color(70, 70, 70));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.setPreferredSize(new Dimension(100, 30));
        logoutBtn.setToolTipText("Logout");

        logoutBtn.addActionListener(e -> {
            dispose(); // Close admin window
            SwingUtilities.invokeLater(LoginWindow::new); // Back to log in
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(34, 40, 49));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        bottomPanel.add(logoutBtn);

        wrapper.add(bottomPanel, BorderLayout.SOUTH);
        add(wrapper);
    }

    /**
     * Creates an icon-only JButton with tooltip and hover effect.
     */
    private JButton createIconButton(String iconPath, String tooltipText) {
        ImageIcon icon = loadIcon(iconPath);
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(100, 100));
        button.setMaximumSize(new Dimension(100, 100));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltipText); // only visible on hover
        return button;
    }

    /**
     * Loads and scales icon from resources folder.
     */
    private ImageIcon loadIcon(String path) {
        try {
            java.net.URL iconURL = getClass().getClassLoader().getResource(path);
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                Image scaled = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else {
                System.err.println("Icon not found: " + path);
                return new ImageIcon(); // fallback
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon(); // fallback
        }
    }

    /**
     * Opens Manage Users window.
     */
    private void manageUsers() {
        SwingUtilities.invokeLater(() -> new ManageUsersWindow().setVisible(true));
    }

    /**
     * Opens Inventory (Main) window.
     */
    private void viewInventory() {
        SwingUtilities.invokeLater(() -> new MainWindow("admin"));
        dispose();
    }
}