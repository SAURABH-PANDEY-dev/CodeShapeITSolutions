package app.dao;

import app.model.User;
import app.util.DBUtil;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * UserDAO.java
 * -------------------------------------
 * Handles database operations related to user login, authentication,
 * and user management with secure password hashing using SHA-256 and salt.
 *
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class UserDAO {

    // Constructor: ensures users table and default admin are created
    public UserDAO() {
        createTableIfNotExists();
        createTestAdmin(); // Create default admin if not exists
    }

    /**
     * Creates the 'users' table if it does not already exist.
     * Uses id as primary key and includes salt column for password security.
     */
    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                salt TEXT NOT NULL,
                role TEXT NOT NULL DEFAULT 'user'
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
     * Generates a cryptographically secure random salt (Base64 encoded).
     */
    private String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes the password with the given salt using SHA-256.
     *
     * @param password Plain password
     * @param salt     Random salt
     * @return Base64 encoded hashed password
     */
    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashed = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Creates a default admin account if not already present.
     * Username: admin, Password: admin123, Role: admin
     */
    public void createTestAdmin() {
        String sql = "INSERT OR IGNORE INTO users (username, password, salt, role) VALUES (?, ?, ?, ?)";

        String salt = generateSalt();
        String hashedPassword = hashPassword("admin123", salt);

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "admin");
            stmt.setString(2, hashedPassword);
            stmt.setString(3, salt);
            stmt.setString(4, "admin");

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
     * Adds a new user to the database with hashed password and random salt.
     *
     * @param user User object (username, password, role)
     * @return true if user added successfully
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, salt, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String salt = generateSalt();
            String hashedPassword = hashPassword(user.getPassword(), salt);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, salt);
            stmt.setString(4, user.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates user credentials using stored hash and salt.
     *
     * @param username Entered username
     * @param password Entered password
     * @return User object if authenticated, null otherwise
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String storedSalt = rs.getString("salt");
                String computedHash = hashPassword(password, storedSalt);

                if (storedHash.equals(computedHash)) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Login failed due to DB error.");
            e.printStackTrace();
        }

        return null; // Authentication failed
    }

    /**
     * Fetches all users (id, username, role only) for admin management.
     *
     * @return List of User objects without passwords
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String role = rs.getString("role");

                users.add(new User(id, username, role)); // using constructor with id
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch users.");
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Deletes a user from the database by ID.
     *
     * @param id User ID to delete
     * @return true if deletion was successful
     */
    public boolean deleteUserById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ Failed to delete user with ID: " + id);
            e.printStackTrace();
            return false;
        }
    }
}
