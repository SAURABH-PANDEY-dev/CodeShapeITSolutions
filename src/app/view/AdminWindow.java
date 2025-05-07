package app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * AdminWindow.java (Enhanced UI)
 * --------------------------
 * Enhanced Admin window with icon-based interface and tooltips on hover.
 *
 * Author: Saurabh Pandey
 * Date: 07 May 2025
 */
public class AdminWindow extends JFrame {

    private final String MANAGE_USERS_ICON = "resources/icons/manage-users.png";
    private final String INVENTORY_ICON = "resources/icons/manage-inventory.png";

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
        // main panel layout/design
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(34,40,49));
//        mainPanel.setBackground(new Color(0,0,0));
//        mainPanel.setBackground(new Color(38,50,56));
//        mainPanel.setBackground(new Color(230,230,230));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Welcome, Label
        JLabel welcomeLabel = new JLabel("Welcome, Admin!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(welcomeLabel);

        // Manage Users Icon Button
        JButton manageUsersBtn = createIconButton(MANAGE_USERS_ICON, "Manage Users");
        manageUsersBtn.addActionListener(e -> manageUsers());
        manageUsersBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(manageUsersBtn);

        // Inventory Icon Button
        JButton viewInventoryBtn = createIconButton(INVENTORY_ICON, "View Inventory");
        viewInventoryBtn.addActionListener(e -> viewInventory());
        mainPanel.add(Box.createVerticalStrut(20)); // space between icons
        viewInventoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(viewInventoryBtn);

        add(mainPanel);
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
        SwingUtilities.invokeLater(() -> new MainWindow().createAndShowGUI());
    }
}