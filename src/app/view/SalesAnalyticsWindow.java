//package app.view;
//
//import app.dao.SaleDAO;
//import app.model.Sale;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.List;
//
///**
// * SalesAnalyticsWindow.java
// * --------------------------
// * Displays a bar chart of total sales by product.
// */
//public class SalesAnalyticsWindow extends JFrame {
//
//    public SalesAnalyticsWindow() {
//        setTitle("Sales Analytics");
//        setSize(800, 500);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // Load and create chart
//        ChartPanel chartPanel = new ChartPanel(createChart());
//        add(chartPanel, BorderLayout.CENTER);
//
//        setVisible(true);
//    }
//
//    /**
//     * Creates a bar chart showing total quantity sold per product.
//     */
//    private JFreeChart createChart() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        // Load all sales data
//        List<Sale> sales = SaleDAO.getAllSales();
//
//        // Aggregate sales by product name
//        for (Sale sale : sales) {
//            String productName = sale.getProductName();
//            int quantity = sale.getQuantity();
//
//            Number current = dataset.getValue("Quantity Sold", productName);
//            int updatedQuantity = (current == null ? 0 : current.intValue()) + quantity;
//            dataset.setValue(updatedQuantity, "Quantity Sold", productName);
//        }
//
//        // Create the bar chart
//        return ChartFactory.createBarChart(
//                "Product Sales Analytics",
//                "Product",
//                "Quantity Sold",
//                dataset
//        );
//    }
//}