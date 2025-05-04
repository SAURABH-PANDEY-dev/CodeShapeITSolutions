package app;

import app.model.Product;
import app.service.ProductService;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== ProductService Test ===");

        // Create service object
        ProductService service = new ProductService();

        // Add products
        Product p1 = new Product(1, "Monitor", 5, 12000.0);
        Product p2 = new Product(2, "USB Cable", 25, 250.0);
        Product p3 = new Product(3, "Keyboard", 0, -500.0); // Invalid test

        service.addProduct(p1);
        service.addProduct(p2);
        service.addProduct(p3);  // Should show validation error

        // List all products
        System.out.println("\nAll Products:");
        for (Product p : service.getAllProducts()) {
            System.out.println(p);
        }

        // Remove a product
        System.out.println("\nRemoving product with ID 2:");
        boolean removed = service.removeProductById(2);
        System.out.println("Removed? " + removed);

        // Final list
        System.out.println("\nUpdated Product List:");
        for (Product p : service.getAllProducts()) {
            System.out.println(p);
        }
    }
}
