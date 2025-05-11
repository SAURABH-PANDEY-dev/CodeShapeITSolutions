package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBUtil.java
 * -----------------------
 * This utility class is responsible for establishing a connection
 * to the SQLite database used in the Local Vendor Inventory Tracker system.
 * <p>
 * Why we need this:
 * - To centralize database connection logic
 * - To avoid repeating the same connection code in every DAO class
 * Note: This uses a file-based SQLite database named 'inventory.db'.
 */
public class DBUtil {

    // URL for connecting to SQLite database file
    private static final String DB_URL = "jdbc:sqlite:inventory.db";

    /**
     * Establishes and returns a Connection to the SQLite database.
     *
     * @return Connection object to interact with the database
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load SQLite JDBC driver (must be available in classpath)
            Class.forName("org.sqlite.JDBC");

            // Connect to the database file; it will be created if it doesn't exist
            conn = DriverManager.getConnection(DB_URL);
            //System.out.println("✅ Connected to SQLite database successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ SQLite JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection to SQLite database failed.");
            e.printStackTrace();
        }

        return conn;
    }
}
