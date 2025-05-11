package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * LowStockWindow.java
 * --------------------------------------
 * Displays a table of products that are low in stock (<= 5 units).
 * Helps vendors quickly identify which products need restocking.
 * <p>
 * Author: Saurabh Pandey
 * Date: 04 May 2025
 */
public class LowStockWindow extends JFrame {

    public LowStockWindow() {
        setTitle("Low Stock Products");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Low Stock Products (<= 5 Units)");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Table
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch low stock products and populate table
        ProductController controller = new ProductController();
        List<Product> lowStockList = controller.getLowStockProducts();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Quantity", "Price"}, 0);

        for (Product p : lowStockList) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getQuantity(), p.getPrice()});
        }

        table.setModel(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(24);

        // Message if no low stock items
        if (lowStockList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All products are sufficiently stocked!", "No Low Stock", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }

        setVisible(true);
    }
}