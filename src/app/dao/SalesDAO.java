package app.dao;

import app.model.Sale;
import app.util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SalesDAO.java
 * Handles database operations related to sales.
 */
public class SalesDAO {

    // Constructor where we create the sales table if it doesn't exist
    public SalesDAO() {
        // Automatically create the sales table when this object is instantiated
        createSalesTable();
    }

    /**
     * Creates or updates the sales table to include the category field.
     */
    private void createSalesTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS sales (" +
                "product_id INTEGER PRIMARY KEY, " +
                "product_name TEXT, " +
                "quantity_sold INTEGER, " +
                "total_price REAL, " +
                "sale_datetime TEXT, " +
                "category TEXT" +  // Adding category column to the table
                ")";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("✅ Sales table created (if not exists).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Records a sale in the database.
     *
     * @param sale The sale object to be recorded
     */
    public void recordSale(Sale sale) {
        String insertSaleSQL = "INSERT OR REPLACE INTO sales (product_id, product_name, quantity_sold, total_price, sale_datetime, category) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSaleSQL)) {

            // Setting the values for the prepared statement
            pstmt.setInt(1, sale.getProductId());
            pstmt.setString(2, sale.getProductName());
            pstmt.setInt(3, sale.getQuantitySold());
            pstmt.setDouble(4, sale.getTotalPrice());
            pstmt.setString(5, sale.getSaleDateTime().toString());  // Date-Time as string
            pstmt.setString(6, sale.getCategory());  // Product category

            pstmt.executeUpdate();  // Execute the update (insert)
            System.out.println("✅ Sale recorded: " + sale.getProductName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all sales records from the database.
     *
     * @return List of all sales
     */
    public List<Sale> getAllSales() {
        List<Sale> salesList = new ArrayList<>();
        String selectSalesSQL = "SELECT * FROM sales";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSalesSQL)) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int quantitySold = rs.getInt("quantity_sold");
                double totalPrice = rs.getDouble("total_price");
                LocalDateTime saleDateTime = LocalDateTime.parse(rs.getString("sale_datetime"));
                String category = rs.getString("category");  // Retrieve category from the database

                // Create a Sale object and add it to the list
                Sale sale = new Sale(productId, productName, quantitySold, totalPrice, saleDateTime, category);
                salesList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesList;
    }
}