package app.controller;

import app.dao.ProductDAO;
import app.dao.SalesDAO;
import app.model.InventoryStats;
import app.model.Product;
import java.util.Comparator;
import java.util.stream.Collectors;

import app.model.Sale;
import app.view.AddProductWindow;
import app.view.ViewAllProductsWindow;
import app.view.SearchProductWindow;
import app.view.UpdateProductWindow;
import app.view.DeleteProductWindow;
import app.view.ExportCSVWindow;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * ProductController.java
 * This class acts as the intermediary between the UI (views) and the business logic (services).
 * It handles button actions from the UI and invokes the appropriate methods.
 */
public class ProductController {

    private ProductDAO productDAO;

    // Constructor
    public ProductController() {
        this.productDAO = new ProductDAO();  // Initialize the DAO to interact with database
    }

    /**
     * Shows the Add Product window.
     */
    public static void showAddProductWindow() {
        // Open the Add Product Window
        new AddProductWindow().createAndShowGUI();
    }

    /**
     * Shows the View All Products window.
     */
    public static void showViewAllProducts() {
        // Open the View All Products Window
        new ViewAllProductsWindow().createAndShowGUI();
    }

    /**
     * Shows the Search Product window.
     */
    public static void showSearchProductWindow() {
        // Open the Search Product by ID Window
        new SearchProductWindow().createAndShowGUI();
    }

    /**
     * Shows the Update Product window.
     */
    public static void showUpdateProductWindow() {
        // Open the Update Product Window
        new UpdateProductWindow().createAndShowGUI();
    }

    /**
     * Shows the Delete Product window.
     */
    public static void showDeleteProductWindow() {
        // Open the Delete Product Window
        new DeleteProductWindow().createAndShowGUI();
    }

    /**
     * Shows the Restock Low Stock Products window.
     */
    public static void showRestockProductsWindow() {
        ProductController controller = new ProductController();
        new app.view.RestockProductsWindow(controller);
    }

    /**
     * Exports the list of products to a CSV file.
     * This method is called when the user clicks the export button.
     * It internally handles the file path (currently hardcoded).
     */
    public static void exportProductsToCSV() {
        // Hardcoding the file path for simplicity (can be customized)
        ProductController controller = new ProductController();
        boolean success = controller.exportProductsToCSV("exports/products.csv");

        // Show success or failure message
        if (success) {
            JOptionPane.showMessageDialog(null, "✅ Products exported to products.csv");
        } else {
            JOptionPane.showMessageDialog(null, "❌ Export failed.");
        }
    }

    /**
     * This method actually exports the products to CSV, using the given file path.
     * @param filePath The file path where the CSV will be saved.
     * @return true if successful, false otherwise
     */
    public boolean exportProductsToCSV(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ID,Name,Quantity,Price\n"); // CSV Header

            for (Product p : getAllProducts()) {
                writer.write(String.format("%d,%s,%d,%.2f\n",
                        p.getId(),
                        p.getName().replace(",", ""),  // Remove commas from names to avoid CSV issues
                        p.getQuantity(),
                        p.getPrice()));
            }

            System.out.println("✅ Products exported successfully to CSV.");
            return true;
        } catch (IOException e) {
            System.out.println("❌ Failed to export products to CSV.");
            e.printStackTrace();
            return false;
        }
    }

    // Methods for handling products

    // Method to add a product
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    // Method to get all products
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    // Method to get a product by its ID
    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    // Method to update a product
    public boolean updateProduct(Product updated) {
        return productDAO.updateProduct(updated);
    }

    // Method to delete a product by ID
    public boolean deleteProductById(int productId) {
        return productDAO.removeProductById(productId);
    }

    /**
     * Deletes all products in the database.
     *
     * @return true if successful, false otherwise
     */
    public boolean deleteAllProducts() {
        return productDAO.deleteAllProducts();
    }

    /**
     * Gets all products that are low in stock using ProductDAO.
     *
     * @return List of low stock products
     */
    public List<Product> getLowStockProducts() {
        return productDAO.getLowStockProducts();
    }

    /**
     * Updates the quantity of a specific product.
     */
    public boolean restockProduct(int id, int newQuantity) {
        return productDAO.updateProductQuantity(id, newQuantity);
    }

    
    /**
     * Returns an InventoryStats object containing summary statistics.
     */
    public static InventoryStats getInventoryStats() {
        try {
            List<Product> allProducts = ProductDAO.getAllProducts(); // Direct DAO call

            if (allProducts == null || allProducts.isEmpty()) {
                return new InventoryStats(0, 0, 0, null);
            }

            int total = allProducts.size();
            int lowStock = (int) allProducts.stream().filter(p -> p.getQuantity() > 0 && p.getQuantity() < 5).count();
            int outOfStock = (int) allProducts.stream().filter(p -> p.getQuantity() == 0).count();

            Product mostStocked = allProducts.stream()
                    .filter(p -> p.getQuantity() > 0)
                    .max(Comparator.comparingInt(Product::getQuantity))
                    .orElse(null);

            return new InventoryStats(total, lowStock, outOfStock, mostStocked);
        } catch (Exception e) {
            System.err.println("Error generating inventory stats: " + e.getMessage());
            return new InventoryStats(0, 0, 0, null);
        }
    }

    /**
     * Wrapper to call import method from DAO.
     */
    public int importProductsFromCSV(String filePath) {
        ProductDAO dao = new ProductDAO();
        return dao.importFromCSV(filePath);
    }

    private SalesDAO salesDAO = new SalesDAO();

    /**
     * Handles sale: reduce stock and save sale info.
     */
    public boolean recordSale(int productId, int quantity) {
        Product product = productDAO.getProductById(productId);

        if (product != null && quantity > 0 && product.getQuantity() >= quantity) {
            boolean reduced = productDAO.reduceStock(productId, quantity);
            if (reduced) {
                double total = quantity * product.getPrice();

                // Create Sale with all required fields
                Sale sale = new Sale(
                        productId,
                        product.getName(),
                        quantity,
                        total,
                        java.time.LocalDateTime.now(), // capture current date-time
                        product.getCategory()           // include category
                );

                salesDAO.recordSale(sale);
                return true;
            }
        }

        return false;
    }

}