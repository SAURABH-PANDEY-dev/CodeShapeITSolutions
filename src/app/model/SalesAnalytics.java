package app.model;

import app.dao.SalesDAO;

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
        // A map to store the total quantity sold for each product
        Map<String, Integer> salesCount = new HashMap<>();

        for (Sale sale : salesDAO.getAllSales()) {
            salesCount.merge(sale.getProductName(), sale.getQuantitySold(), Integer::sum);
        }

        // Sort the products by quantity sold and limit the results to topN
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

        // Loop through all sales and accumulate the revenue by category
        for (Sale sale : salesDAO.getAllSales()) {
            categoryRevenue.merge(sale.getCategory(), sale.getTotalPrice(), Double::sum);
        }

        return categoryRevenue;
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

        // Select the appropriate time period format
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

        // Loop through sales and accumulate the revenue by the selected time period
        for (Sale sale : salesDAO.getAllSales()) {
            String key = sale.getSaleDateTime().format(formatter);
            salesTimeMap.merge(key, sale.getTotalPrice(), Double::sum);
        }

        return salesTimeMap;
    }
}