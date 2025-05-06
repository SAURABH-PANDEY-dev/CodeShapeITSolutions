package app.view;

import app.dao.SalesDAO;
import app.model.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ViewSalesWindow.java
 * ---------------------
 * Displays a table showing all recorded sales from the database.
 */
public class ViewSalesWindow extends JFrame {

    public ViewSalesWindow() {
        setTitle("Sales History");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table column names
        String[] columnNames = {
                "Product ID", "Product Name", "Quantity Sold",
                "Total Price", "Date-Time", "Category"
        };

        // Fetch sales data
        SalesDAO salesDAO = new SalesDAO();
        List<Sale> salesList = salesDAO.getAllSales();

        // Table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Add each sale to the table
        for (Sale sale : salesList) {
            Object[] row = {
                    sale.getProductId(),
                    sale.getProductName(),
                    sale.getQuantitySold(),
                    sale.getTotalPrice(),
                    sale.getSaleDateTime().toString(),
                    sale.getCategory()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}