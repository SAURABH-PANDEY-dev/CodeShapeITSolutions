package app.model;

/**
 * Model to hold summary statistics of the inventory.
 */
public class InventoryStats {
    private int totalProducts;
    private int lowStockCount;
    private int outOfStockCount;
    private Product mostStockedProduct;

    // Constructor
    public InventoryStats(int totalProducts, int lowStockCount, int outOfStockCount, Product mostStockedProduct) {
        this.totalProducts = totalProducts;
        this.lowStockCount = lowStockCount;
        this.outOfStockCount = outOfStockCount;
        this.mostStockedProduct = mostStockedProduct;
    }

    // Getters
    public int getTotalProducts() {
        return totalProducts;
    }

    public int getLowStockCount() {
        return lowStockCount;
    }

    public int getOutOfStockCount() {
        return outOfStockCount;
    }

    public Product getMostStockedProduct() {
        return mostStockedProduct;
    }
}