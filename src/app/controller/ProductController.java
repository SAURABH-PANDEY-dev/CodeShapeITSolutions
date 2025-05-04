package app.controller;

import app.dao.ProductDAO;
import app.model.Product;
import app.dao.ProductDAO;
import app.model.Product;
import app.view.AddProductWindow;
import app.view.ViewAllProductsWindow;
import app.view.SearchProductWindow;
import app.view.UpdateProductWindow;
import app.view.DeleteProductWindow;
import app.view.ExportCSVWindow;

import java.util.List;

/**
 * ProductController.java
 * This class acts as the intermediary between the UI (views) and the business logic (services).
 * It handles button actions from the UI and invokes the appropriate methods.
 */
public class ProductController {

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
     * Exports the list of products to a CSV file.
     */
    public static void exportProductsToCSV() {
        // This will be implemented in the future
        System.out.println("Export Products to CSV (not yet implemented)");
    }


    /**
     * ProductController.java
     * ----------------------
     * This class handles the communication between the view and the model (DAO).
     * It contains the logic for adding products, viewing products, and other actions.
     *
     * Author: Saurabh Pandey
     * Date: 04 May 2025
     */
    private ProductDAO productDAO;

    // Constructor
    public ProductController() {
        this.productDAO = new ProductDAO();  // Initialize the DAO to interact with database
    }

    // Method to add a product
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }// Call the DAO method to add product to the database

    public List<Product> getAllProducts(){
        return productDAO.getAllProducts();
    }

    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }
}