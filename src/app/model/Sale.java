package app.model;

public class Sale {
    private int productId;
    private String productName;
    private int quantitySold;
    private double totalPrice;

    public Sale(int productId, String productName, int quantitySold, double totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantitySold() { return quantitySold; }
    public double getTotalPrice() { return totalPrice; }
}
