package app.model;

import app.dao.SalesDAO;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SalesAnalytics.java
 * ---------------------
 * Provides methods to calculate and fetch various sales analytics.
 * These include:
 * 1. Total Revenue
 * 2. Best Selling Products
 * 3. Sales by Category
 * 4. Sales Over Time (Daily/Weekly/Monthly)
 * 5. Sold Quantity by Category
 * 6. Product-wise Sales Details
 */
public class SalesAnalytics {

    private final SalesDAO salesDAO;

    /**
     * Constructor to initialize the SalesDAO.
     */
    public SalesAnalytics() {
        this.salesDAO = new SalesDAO();
    }

    /**
     * Calculates the total revenue from all sales in the database.
     *
     * @return Total revenue as a double.
     */
    public double getTotalRevenue() {
        return salesDAO.getAllSales()
                .stream()
                .mapToDouble(Sale::getTotalPrice)
                .sum();
    }

    /**
     * Returns a list of the top N best-selling products, sorted by quantity sold.
     *
     * @param topN The number of top-selling products to retrieve.
     * @return A list of Sale objects representing the best-selling products.
     */
    public List<Sale> getBestSellingProducts(int topN) {
        Map<String, Integer> salesCount = new HashMap<>();

        for (Sale sale : salesDAO.getAllSales()) {
            salesCount.merge(sale.getProductName(), sale.getQuantitySold(), Integer::sum);
        }

        return salesCount.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(topN)
                .map(entry -> new Sale(0, entry.getKey(), entry.getValue(), 0, LocalDateTime.now(), ""))
                .collect(Collectors.toList());
    }

    /**
     * Returns the total revenue grouped by product category.
     *
     * @return A map where the key is the category name and the value is the total revenue for that category.
     */
    public Map<String, Double> getSalesByCategory() {
        Map<String, Double> categoryRevenue = new HashMap<>();

        for (Sale sale : salesDAO.getAllSales()) {
            categoryRevenue.merge(sale.getCategory(), sale.getTotalPrice(), Double::sum);
        }

        return categoryRevenue;
    }

    /**
     * Returns the total quantity sold for each product category.
     *
     * @return A map where the key is the category name and the value is the total quantity sold for that category.
     */
    public Map<String, Integer> getSoldQuantityByCategory() {
        Map<String, Integer> categoryQuantity = new HashMap<>();

        for (Sale sale : salesDAO.getAllSales()) {
            categoryQuantity.merge(sale.getCategory(), sale.getQuantitySold(), Integer::sum);
        }

        return categoryQuantity;
    }

    /**
     * Returns detailed sales information per product: product name, total quantity sold, and total revenue.
     *
     * @return A list of Sale objects with aggregated quantity and revenue per product.
     */
    public List<Sale> getProductSales() {
        Map<String, Sale> productSalesMap = new HashMap<>();

        for (Sale sale : salesDAO.getAllSales()) {
            String productName = sale.getProductName();
            if (productSalesMap.containsKey(productName)) {
                Sale existing = productSalesMap.get(productName);
                existing.setQuantitySold(existing.getQuantitySold() + sale.getQuantitySold());
                existing.setTotalPrice(existing.getTotalPrice() + sale.getTotalPrice());
            } else {
                productSalesMap.put(productName, new Sale(
                        0, productName, sale.getQuantitySold(), sale.getTotalPrice(),
                        LocalDateTime.now(), sale.getCategory()
                ));
            }
        }

        return new ArrayList<>(productSalesMap.values());
    }

    /**
     * Returns sales revenue over time grouped by day, week, or month.
     *
     * @param period The period to group by (daily, weekly, or monthly).
     * @return A map where the key is the time period (day/week/month) and the value is the total revenue for that period.
     */
    public Map<String, Double> getSalesOverTime(String period) {
        Map<String, Double> salesTimeMap = new TreeMap<>();
        DateTimeFormatter formatter;

        switch (period.toLowerCase()) {
            case "daily":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                break;
            case "weekly":
                formatter = DateTimeFormatter.ofPattern("YYYY-'W'ww");
                break;
            case "monthly":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                break;
            default:
                throw new IllegalArgumentException("Invalid period. Use daily, weekly, or monthly.");
        }

        for (Sale sale : salesDAO.getAllSales()) {
            String key = sale.getSaleDateTime().format(formatter);
            salesTimeMap.merge(key, sale.getTotalPrice(), Double::sum);
        }

        return salesTimeMap;
    }

    /**
     * Returns a map of category name to total quantity sold.
     *
     * @return Map with category name as key and total quantity sold as value.
     */
    public Map<String, Integer> getSoldQuantitiesByCategory() {
        Map<String, Integer> quantitiesByCategory = new HashMap<>();

        List<Sale> allSales = salesDAO.getAllSales();
        for (Sale sale : allSales) {
            String category = sale.getCategory();
            int quantity = sale.getQuantitySold();
            quantitiesByCategory.put(category, quantitiesByCategory.getOrDefault(category, 0) + quantity);
        }

        return quantitiesByCategory;
    }

    public JFreeChart createChartForCategoryTable(JTable table, String type) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        for (int i = 0; i < table.getRowCount(); i++) {
            String category = table.getValueAt(i, 0).toString();
            Number amount = Double.parseDouble(table.getValueAt(i, 2).toString());
            dataset.addValue(amount, "Revenue", category);
            pieDataset.setValue(category, amount);
        }

        return SalesChartFactory.create(type, "Category Sales", dataset, pieDataset);
    }

    public JFreeChart createChartForProductTable(JTable table, String type) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        for (int i = 0; i < table.getRowCount(); i++) {
            String product = table.getValueAt(i, 0).toString();
            Number amount = Double.parseDouble(table.getValueAt(i, 2).toString());
            dataset.addValue(amount, "Revenue", product);
            pieDataset.setValue(product, amount);
        }

        return SalesChartFactory.create(type, "Product Sales", dataset, pieDataset);
    }
}