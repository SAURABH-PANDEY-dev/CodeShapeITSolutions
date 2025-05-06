package app.service;

import app.model.Sale;
import app.model.SalesAnalytics;

import java.util.List;
import java.util.Map;

/**
 * SalesAnalyticsService.java
 * ---------------------------
 * Acts as a service layer between the GUI and the SalesAnalytics logic.
 * This allows the frontend to easily access sales statistics and data.
 */
public class SalesAnalyticsService {

    private final SalesAnalytics analytics;

    /**
     * Constructor to initialize the SalesAnalytics object.
     */
    public SalesAnalyticsService() {
        this.analytics = new SalesAnalytics();
    }

    /**
     * Returns the total revenue from all sales.
     *
     * @return Total revenue as a double.
     */
    public double getTotalRevenue() {
        return analytics.getTotalRevenue();
    }

    /**
     * Returns a list of top N selling products sorted by quantity sold.
     *
     * @param topN Number of top products to retrieve.
     * @return List of Sale objects with productName and quantitySold.
     */
    public List<Sale> getTopSellingProducts(int topN) {
        return analytics.getBestSellingProducts(topN);
    }

    /**
     * Returns total revenue grouped by product category.
     *
     * @return Map of category name to total revenue.
     */
    public Map<String, Double> getRevenueByCategory() {
        return analytics.getSalesByCategory();
    }

    /**
     * Returns revenue over time, grouped by day/week/month.
     *
     * @param period "daily", "weekly", or "monthly"
     * @return Map of period string to revenue
     */
    public Map<String, Double> getRevenueOverTime(String period) {
        return analytics.getSalesOverTime(period);
    }
}