package app.view;

import app.model.SalesAnalytics;
import app.model.Sale;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 * SalesAnalyticsWindow.java
 * ---------------------------
 * This window displays the sales analytics including:
 * - Total Revenue
 * - Best-Selling Products
 * - Sales by Category
 * - Sales Over Time (Daily/Weekly/Monthly)
 */
public class SalesAnalyticsWindow extends JFrame {

    private final SalesAnalytics salesAnalytics;

    public SalesAnalyticsWindow() {
        salesAnalytics = new SalesAnalytics();
        initializeUI();
    }

    /**
     * Initializes the user interface for the sales analytics window.
     */
    private void initializeUI() {
        setTitle("Sales Analytics Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel to display total revenue and other summary data
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridLayout(4, 2));

        // Display Total Revenue
        JLabel totalRevenueLabel = new JLabel("Total Revenue: " + salesAnalytics.getTotalRevenue());
        summaryPanel.add(totalRevenueLabel);

        // Display Best Selling Products (Top 5)
        List<Sale> bestSellingProducts = salesAnalytics.getBestSellingProducts(5);
        StringBuilder bestSellingProductsText = new StringBuilder("<html><ul>");
        for (Sale sale : bestSellingProducts) {
            bestSellingProductsText.append("<li>").append(sale.getProductName()).append(" - ").append(sale.getQuantitySold()).append(" sold</li>");
        }
        bestSellingProductsText.append("</ul></html>");
        JLabel bestSellingLabel = new JLabel(bestSellingProductsText.toString());
        summaryPanel.add(bestSellingLabel);

        // Display Sales by Category
        Map<String, Double> salesByCategory = salesAnalytics.getSalesByCategory();
        StringBuilder categorySalesText = new StringBuilder("<html><ul>");
        for (Map.Entry<String, Double> entry : salesByCategory.entrySet()) {
            categorySalesText.append("<li>").append(entry.getKey()).append(": $").append(entry.getValue()).append("</li>");
        }
        categorySalesText.append("</ul></html>");
        JLabel categorySalesLabel = new JLabel(categorySalesText.toString());
        summaryPanel.add(categorySalesLabel);

        // Display Sales Over Time (Monthly)
        Map<String, Double> salesOverTime = salesAnalytics.getSalesOverTime("monthly");
        StringBuilder salesTimeText = new StringBuilder("<html><ul>");
        for (Map.Entry<String, Double> entry : salesOverTime.entrySet()) {
            salesTimeText.append("<li>").append(entry.getKey()).append(": $").append(entry.getValue()).append("</li>");
        }
        salesTimeText.append("</ul></html>");
        JLabel salesTimeLabel = new JLabel(salesTimeText.toString());
        summaryPanel.add(salesTimeLabel);

        // Adding the summary panel to the main window
        add(summaryPanel, BorderLayout.NORTH);

        // Making the window visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesAnalyticsWindow());
    }
}