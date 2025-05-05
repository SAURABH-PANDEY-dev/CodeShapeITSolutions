//package app;
//
//import app.model.Product;
//import app.service.ProductService;
//
//import java.util.List;
//import java.util.Scanner;
//
///**
// * MainApp.java
// * -------------------------------
// * Console-based user interface for the Inventory Tracker.
// * Provides options to add, view, search, update, and delete products.
// *
// * Author: Saurabh Pandey
// * Date: 04 May 2025
// */
//public class MainApp {
//
//    private static final Scanner scanner = new Scanner(System.in);
//    private static final ProductService service = new ProductService();
//
//    public static void main(String[] args) {
//        System.out.println("=== Welcome to Local Vendor Inventory Tracker ===");
//    // Export
//        while (true) {
//            showMenu();
//            int choice = getIntInput("Enter your choice: ");
//
//            switch (choice) {
//                case 1 -> addProduct();
//                case 2 -> viewAllProducts();
//                case 3 -> searchProduct();
//                case 4 -> updateProduct();
//                case 5 -> deleteProduct();
//                case 6 -> exportToCSV();
//                case 7 -> {
//                    System.out.println("✅ Exiting. Thank you!");
//                    return;
//                }
//                default -> System.out.println("❌ Invalid choice. Please try again.");
//            }
//        }
//
//    }
//    private static void exportToCSV() {
//        System.out.println("\n--- Export Products to CSV ---");
//        String path = getStringInput("Enter file path to save (e.g., exports/products.csv): ");
//        boolean success = service.exportProductsToCSV(path);
//        if (success) {
//            System.out.println("✅ Products exported to " + path);
//        } else {
//            System.out.println("❌ Failed to export products.");
//        }
//    }
//
//    private static void showMenu() {
//        System.out.println("\n--- Menu ---");
//        System.out.println("1. Add Product");
//        System.out.println("2. View All Products");
//        System.out.println("3. Search Product by ID");
//        System.out.println("4. Update Product");
//        System.out.println("5. Delete Product");
//        System.out.println("6. Export products to CSV");
//        System.out.println("7. Exit");
//    }
//
//    private static void addProduct() {
//        System.out.println("\n--- Add Product ---");
//        int id = getIntInput("Enter product ID: ");
//        String name = getStringInput("Enter product name: ");
//        int qty = getIntInput("Enter quantity: ");
//        double price = getDoubleInput("Enter price: ");
//
//        Product p = new Product(id, name, qty, price);
//        if (service.addProduct(p)) {
//            System.out.println("✅ Product added successfully.");
//        } else {
//            System.out.println("❌ Failed to add product.");
//        }
//    }
//
//    private static void viewAllProducts() {
//        System.out.println("\n--- All Products ---");
//        List<Product> list = service.getAllProducts();
//        if (list.isEmpty()) {
//            System.out.println("No products found.");
//            return;
//        }
//        for (Product p : list) {
//            System.out.println(p);
//        }
//    }
//
//    private static void searchProduct() {
//        System.out.println("\n--- Search Product ---");
//        int id = getIntInput("Enter product ID to search: ");
//        Product p = service.getProductById(id);
//        if (p != null) {
//            System.out.println("✅ Product Found:\n" + p);
//        } else {
//            System.out.println("❌ Product not found.");
//        }
//    }
//
//    private static void updateProduct() {
//        System.out.println("\n--- Update Product ---");
//        int id = getIntInput("Enter product ID to update: ");
//        Product existing = service.getProductById(id);
//        if (existing == null) {
//            System.out.println("❌ Product with ID " + id + " does not exist.");
//            return;
//        }
//
//        String name = getStringInput("Enter new name: ");
//        int qty = getIntInput("Enter new quantity: ");
//        double price = getDoubleInput("Enter new price: ");
//
//        Product updated = new Product(id, name, qty, price);
//        if (service.updateProduct(updated)) {
//            System.out.println("✅ Product updated.");
//        } else {
//            System.out.println("❌ Update failed.");
//        }
//    }
//
//    private static void deleteProduct() {
//        System.out.println("\n--- Delete Product ---");
//        int id = getIntInput("Enter product ID to delete: ");
//        if (service.removeProductById(id)) {
//            System.out.println("✅ Product deleted.");
//        } else {
//            System.out.println("❌ Product not found.");
//        }
//    }
//
//    // Utility methods
//    private static int getIntInput(String prompt) {
//        System.out.print(prompt);
//        while (!scanner.hasNextInt()) {
//            System.out.print("Please enter a valid number: ");
//            scanner.next(); // Clear invalid input
//        }
//        return scanner.nextInt();
//    }
//
//    private static double getDoubleInput(String prompt) {
//        System.out.print(prompt);
//        while (!scanner.hasNextDouble()) {
//            System.out.print("Please enter a valid number: ");
//            scanner.next(); // Clear invalid input
//        }
//        return scanner.nextDouble();
//    }
//
//    private static String getStringInput(String prompt) {
//        System.out.print(prompt);
//        scanner.nextLine(); // Clear newline from previous input
//        return scanner.nextLine();
//    }
//}


// from here the gui code starts

package app;

import app.view.MainWindow;
import app.view.LoginWindow;
import javax.swing.*;

/**
 * MainApp.java
 * Entry point of the Inventory Management Application.
 */
public class MainApp {
    public static void main(String[] args) {
        // Start the application from login screen
        SwingUtilities.invokeLater(LoginWindow::new);
    }
}