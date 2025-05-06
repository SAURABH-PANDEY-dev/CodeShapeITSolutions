package app.dao;

import app.model.User;
import app.util.DBUtil;
import java.util.ArrayList;
import java.util.List;

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
            password TEXT NOT NULL,
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

//    public void printAllUsers() {
//        String sql = "SELECT * FROM users";
//
//        try (Connection conn = DBUtil.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            System.out.println("------ Users in DB ------");
//            while (rs.next()) {
//                System.out.println("Username: " + rs.getString("username") +
//                        ", Password: " + rs.getString("password") +
//                        ", Role: " + rs.getString("role"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * Creates a default admin account if not already present.
     */
    public void createTestAdmin() {
        String sql = "INSERT OR IGNORE INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "admin");
            stmt.setString(2, "admin123");
            stmt.setString(3, "admin");

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
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
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
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("❌ Login failed due to DB error.");
            e.printStackTrace();
        }

        return null; // User not found
    }

    /**
     * Retrieves all users from the database.
     * Only used by admin to manage user accounts.
     *
     * @return List of User objects (excluding passwords for display).
     */
    /**
     * Fetches all users from the database (id, username, role only).
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

                users.add(new User(id, username, role)); // use new constructor
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch users.");
            e.printStackTrace();
        }

        return users;
    }



}