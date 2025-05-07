package app.view;

import app.controller.ProductController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * MainWindow.java
 * Icons-only version with dark theme, scrollable button panel, and hover effects.
 */
public class MainWindow {

    private final String userType; // "admin" or "user"

    public MainWindow(String userType) {
        this.userType = userType;
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new BorderLayout());

        // === Title Panel ===
        JLabel titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(0,0,0));
        titlePanel.setPreferredSize(new Dimension(600, 60));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // === Button Panel ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(112,128,144)); // color of the main panel
        buttonPanel.setLayout(new GridLayout(3, 3, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Add icon-only buttons with tooltips
        addMenuButton(buttonPanel, "Dashboard", "resources/icons/dashboard.png", e -> new DashboardWindow());
        addMenuButton(buttonPanel, "Add Product", "resources/icons/add-product.png", e -> ProductController.showAddProductWindow());
        addMenuButton(buttonPanel, "View All Products", "resources/icons/view-all-product.png", e -> ProductController.showViewAllProducts());
        addMenuButton(buttonPanel, "Search Product by ID", "resources/icons/search-product.png", e -> ProductController.showSearchProductWindow());
        addMenuButton(buttonPanel, "Update Product", "resources/icons/update-product.png", e -> ProductController.showUpdateProductWindow());
        addMenuButton(buttonPanel, "Delete Product", "resources/icons/delete-product.png", e -> ProductController.showDeleteProductWindow());
        addMenuButton(buttonPanel, "Delete All Products", "resources/icons/delete-all-product.png", e -> new app.ui.DeleteAllConfirmationWindow());
        addMenuButton(buttonPanel, "Export Products to CSV", "resources/icons/export-csv.png", e -> ProductController.exportProductsToCSV());
        addMenuButton(buttonPanel, "Import Products from CSV", "resources/icons/import-csv.png", e -> new ImportCSVWindow());
        addMenuButton(buttonPanel, "Low Stock Alerts", "resources/icons/low.png", e -> new LowStockWindow());
        addMenuButton(buttonPanel, "Restock Products", "resources/icons/restock.png", e -> ProductController.showRestockProductsWindow());
        addMenuButton(buttonPanel, "Billing / POS", "resources/icons/Billing.png", e -> new BillingWindow());
        addMenuButton(buttonPanel, "Record Sale", "resources/icons/record-sales.png", e -> new RecordSaleWindow());
        addMenuButton(buttonPanel, "View Sales History", "resources/icons/sales-history.png", e -> new ViewSalesWindow());
        addMenuButton(buttonPanel, "View Sales Analysis", "resources/icons/analytics.png", e -> new SalesAnalyticsWindow());

        // === Scrollable Button Panel ===
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scroll
        scrollPane.setBackground(new Color(0,0,0));

        // === Back Button Panel ===
        JButton backButton = new JButton(loadIcon("resources/icons/back.png"));
        backButton.setToolTipText("Back");
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBackground(new Color(52, 152, 219));

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backButton.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent evt) {
                backButton.setBackground(new Color(52, 152, 219));
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            if ("admin".equalsIgnoreCase(userType)) {
                new AdminWindow();
            } else {
                new UserWindow();
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(52, 73, 94));
        bottomPanel.add(backButton);

        // === Add to Frame ===
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // === Button Utility ===
    private void addMenuButton(JPanel panel, String tooltip, String iconPath, java.awt.event.ActionListener action) {
        JButton btn = new JButton(loadIcon(iconPath));
        btn.setToolTipText(tooltip);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBackground(new Color(52, 73, 94));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(52, 73, 94));
            }
        });

        btn.addActionListener(action);
        panel.add(btn);
    }

    // === Icon Loader ===
    private ImageIcon loadIcon(String iconPath) {
        try {
            java.net.URL iconURL = getClass().getClassLoader().getResource(iconPath);
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                Image scaled = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else {
                System.err.println("Icon not found: " + iconPath);
                return new ImageIcon();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon();
        }
    }
}