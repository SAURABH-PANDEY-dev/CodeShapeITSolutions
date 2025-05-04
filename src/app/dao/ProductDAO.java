package app.dao;

import app.model.Product;
import app.util.DBUtil;

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
 * Date: [Insert today's date]
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
    public List<Product> getAllProducts() {
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
}
