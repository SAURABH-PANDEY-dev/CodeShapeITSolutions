package app.dao;

import app.model.User;
import app.util.DBUtil;

import java.sql.*;

/**
 * UserDAO.java
 * -------------------------------------
 * Handles database operations related to user login and authentication.
 *
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class UserDAO {

    // Constructor: ensures users table and default admin are created
    public UserDAO() {
        createTableIfNotExists();
        createTestAdmin(); // Create default admin if not exists
    }

    /**
     * Creates the 'users' table if it does not already exist.
     * Uses id as primary key for scalability.
     */
    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            );
        """;

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Users table checked/created.");
        } catch (SQLException e) {
            System.out.println("❌ Failed to create users table.");
            e.printStackTrace();
        }
    }

    /**
     * Creates a default admin account if not already present.
     */
    public void createTestAdmin() {
        String sql = "INSERT OR IGNORE INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "admin");
            stmt.setString(2, "admin123"); // For production: hash password

            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("✅ Default admin created.");
            } else {
                System.out.println("ℹ️ Admin already exists.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to insert test admin.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a new user to the database.
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // For production: hash password
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Failed to add user.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates user credentials against the database.
     */
    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); // For production: hash password
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // True if user found
        } catch (SQLException e) {
            System.out.println("❌ Login failed due to DB error.");
            e.printStackTrace();
        }
        return false;
    }

}