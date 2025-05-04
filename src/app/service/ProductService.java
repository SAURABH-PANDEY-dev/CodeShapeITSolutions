package app.service;

import app.dao.ProductDAO;
import app.model.Product;

import java.util.List;

/**
 * ProductService.java
 * --------------------------------
 * Acts as a service layer between the controller (MainApp)
 * and data access layer (ProductDAO).
 * This layer is responsible for business logic and validation.
 *
 * Author: Saurabh Pandey
 * Date: 04 May 2025
 */
public class ProductService {

    private ProductDAO productDAO;

    // Constructor
    public ProductService() {
        this.productDAO = new ProductDAO(); // In real apps, this could be injected
    }

    // Add product after basic validation
    public boolean addProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            System.out.println("❌ Product name cannot be empty.");
            return false;
        }
        if (product.getPrice() < 0 || product.getQuantity() < 0) {
            System.out.println("❌ Price or quantity cannot be negative.");
            return false;
        }

        productDAO.addProduct(product);
        return true;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    // Get product by ID
    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }

    // Remove product by ID
    public boolean removeProductById(int id) {
        return productDAO.removeProductById(id);
    }

    /**
     * Update an existing product after validation.
     *
     * @param product The product with updated data
     * @return true if update was successful, false otherwise
     */
    public boolean updateProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            System.out.println("❌ Product name cannot be empty.");
            return false;
        }
        if (product.getQuantity() < 0 || product.getPrice() < 0) {
            System.out.println("❌ Quantity or price cannot be negative.");
            return false;
        }

        return productDAO.updateProduct(product);
    }

    // Export products to CSV
    public boolean exportProductsToCSV(String filePath) {
        return productDAO.exportProductsToCSV(filePath);
    }


}
