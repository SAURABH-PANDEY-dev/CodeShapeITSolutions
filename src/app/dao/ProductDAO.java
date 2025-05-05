package app.dao;

import app.model.Product;
import app.util.DBUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDAO.java
 * --------------------------------
 * This class handles all database operations related to products using SQLite.
 * It connects through DBUtil and creates the product table if it does not exist.
 *
 * Author: Saurabh Pandey
 * Date: 04 May 2025
 */

public class ProductDAO {

    // Constructor — auto-creates table if not exists
    public ProductDAO() {
        createTableIfNotExists();
    }

    /**
     * Create the 'products' table if it does not already exist.
     */
    private void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS products (
                    id INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    quantity INTEGER NOT NULL,
                    price REAL NOT NULL
                );
                """;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Table 'products' verified/created.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to create 'products' table.");
            e.printStackTrace();
        }
    }

    /**
     * Add a new product to the database.
     */
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (id, name, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setInt(3, product.getQuantity());
            stmt.setDouble(4, product.getPrice());

            stmt.executeUpdate();
            System.out.println("✅ Product added to database: " + product.getName());

        } catch (SQLException e) {
            System.out.println("❌ Failed to add product to database.");
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all products from the database.
     */
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                products.add(p);
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch products from database.");
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Find a product by ID from the database.
     */
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product p = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error while fetching product with ID: " + id);
            e.printStackTrace();
        }

        return p;
    }

    /**
     * Delete a product by ID from the database.
     */
    public boolean removeProductById(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();

            return affected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Failed to delete product with ID: " + id);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update an existing product in the database.
     * If the ID does not exist, nothing will be changed.
     *
     * @param product The product object with updated values.
     * @return true if updated successfully, false otherwise
     */
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getId());

            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Failed to update product with ID: " + product.getId());
            e.printStackTrace();
        }

        return false;
    }

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

    /**
     * Returns a list of products that are low in stock (quantity <= 5).
     *
     * @return List of low stock products
     */
    public List<Product> getLowStockProducts() {
        List<Product> allProducts = getAllProducts();
        List<Product> lowStock = new ArrayList<>();

        for (Product p : allProducts) {
            if (p.getQuantity() <= 5) {
                lowStock.add(p);
            }
        }

        return lowStock;
    }
    /**
     * Updates the quantity of a product by its ID.
     */
    public boolean updateProductQuantity(int id, int newQuantity) {
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, id);

            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Failed to update quantity for product ID: " + id);
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Imports products from a CSV file.
     * Assumes the CSV has header: ID,Name,Quantity,Price
     */
    public int importFromCSV(String filePath) {
        int importCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 4) continue;

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                int qty = Integer.parseInt(parts[2].trim());
                double price = Double.parseDouble(parts[3].trim());

                Product p = new Product(id, name, qty, price);
                addProduct(p); // Reuse existing addProduct method
                importCount++;
            }

            System.out.println("✅ Imported " + importCount + " products.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("❌ Failed to import products.");
            e.printStackTrace();
        }

        return importCount;
    }

    public boolean reduceStock(int productId, int quantity) {
        Product p = getProductById(productId);
        if (p != null && p.getQuantity() >= quantity) {
            p.setQuantity(p.getQuantity() - quantity);
            return updateProduct(p); // reuse update
        }
        return false;
    }

}