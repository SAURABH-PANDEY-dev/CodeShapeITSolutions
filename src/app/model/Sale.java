package app.model;

import java.time.LocalDateTime;

/**
 * Sale class represents a sale transaction, including product details, quantity, total price,
 * date and time of the sale, and the product's category.
 */
public class Sale {
    private final int productId;
    private final String productName;
    private int quantitySold;
    private double totalPrice;
    private final LocalDateTime saleDateTime;
    private final String category; // New field for product category

    /**
     * Constructor to create a Sale object.
     *
     * @param productId     ID of the product sold
     * @param productName   Name of the product sold
     * @param quantitySold  Quantity of the product sold
     * @param totalPrice    Total price of the sale
     * @param saleDateTime  Date and time when the sale occurred
     * @param category      Category of the product
     */
    public Sale(int productId, String productName, int quantitySold, double totalPrice, LocalDateTime saleDateTime, String category) {
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.saleDateTime = saleDateTime;
        this.category = category; // Set the category
    }

    // Getters for all the fields

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantitySold() { return quantitySold; }
    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }
    public LocalDateTime getSaleDateTime() { return saleDateTime; }
    public String getCategory() { return category; } // Getter for category

}