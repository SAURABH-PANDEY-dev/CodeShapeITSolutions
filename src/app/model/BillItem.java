package app.model;

/**
 * BillItem.java
 * ----------------------------------------
 * Represents an item in the bill during a sales transaction.
 * Contains product details, quantity being sold, and computed total price.
 *
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class BillItem {
    private int productId;
    private String productName;
    private int quantity;
    private double pricePerUnit;
    private String category;

    public BillItem(int productId, String productName, int quantity, double pricePerUnit, String category) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public String getCategory() {
        return category;
    }

    public double getTotalPrice() {
        return quantity * pricePerUnit;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}