package app;

import app.model.Product;
import app.dao.ProductDAO;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== Welcome to CodeShapeIT Project ===");

        // Create DAO instance
        ProductDAO dao = new ProductDAO();

        // Add some products
        Product p1 = new Product(101, "Dell Laptop", 5, 60000);
        Product p2 = new Product(102, "HP Laptop", 3, 55000);
        dao.addProduct(p1);
        dao.addProduct(p2);

        // List all products
        System.out.println("\nAll Products:");
        for (Product p : dao.getAllProducts()) {
            System.out.println(p);
        }

        // Get a product by ID
        int id;
        System.out.print("Enter the id to search : ");
        Scanner input = new Scanner(System.in);
        id = input.nextInt();
        System.out.println("\nSearching for product with ID :" + id);
        Product found = dao.getProductById(id);
        System.out.println(found != null ? found : "Product not found");

        // Remove a product
        System.out.println("\nRemoving product with ID 101...");
        boolean removed = dao.removeProductById(101);
        System.out.println(removed ? "Removed successfully" : "Product not found");

        // List all products again
        System.out.println("\nUpdated Product List:");
        for (Product p : dao.getAllProducts()) {
            System.out.println(p);
        }
    }
}
