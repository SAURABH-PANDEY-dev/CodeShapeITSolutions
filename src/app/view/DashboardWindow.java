package app.view;

import app.controller.ProductController;
import app.model.Product;
import app.model.InventoryStats;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard Window to show inventory statistics.
 */
public class DashboardWindow extends JFrame {

    public DashboardWindow() {
        setTitle("Inventory Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        InventoryStats stats = ProductController.getInventoryStats();

        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 250, 255));

        // Cards
        panel.add(createCard("Total Products", String.valueOf(stats.getTotalProducts()), new Color(70, 130, 180)));
        panel.add(createCard("Low Stock", String.valueOf(stats.getLowStockCount()), new Color(255, 165, 0)));
        panel.add(createCard("Out of Stock", String.valueOf(stats.getOutOfStockCount()), new Color(220, 20, 60)));

        String mostStocked = stats.getMostStockedProduct() != null
                ? stats.getMostStockedProduct().getName() + " (" + stats.getMostStockedProduct().getQuantity() + ")"
                : "N/A";
        panel.add(createCard("Most Stocked Product", mostStocked, new Color(60, 179, 113)));

        add(panel);
    }

    private JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setBackground(color);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        card.setPreferredSize(new Dimension(200, 100));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(Color.WHITE);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}