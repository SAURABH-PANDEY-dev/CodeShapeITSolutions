package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ViewAllProductsWindow extends JFrame {

    private JTable productTable;
    private DefaultTableModel tableModel;

    public ViewAllProductsWindow() {
        setTitle("View All Products");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Column headers
        String[] columns = {"ID", "Name", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columns, 0);

        // Fetch and insert data
        ProductController controller = new ProductController();
        List<Product> productList = controller.getAllProducts();

        for (Product p : productList) {
            Object[] row = {
                    p.getId(),
                    p.getName(),
                    p.getQuantity(),
                    p.getPrice()
            };
            tableModel.addRow(row);
        }

        // Table setup
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.setRowHeight(30);
        productTable.setSelectionBackground(new Color(135, 206, 235)); // Highlight color
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Styling
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for scrollPane
        add(scrollPane, BorderLayout.CENTER);

        // Button panel for additional actions (e.g., Export)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton exportButton = new JButton("Export to CSV");
        exportButton.setFont(new Font("Arial", Font.BOLD, 14));
        exportButton.setBackground(new Color(0, 123, 255)); // Blue background
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.add(exportButton);

        // Add the panel at the bottom of the window
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to export button
        exportButton.addActionListener(e -> exportTableToCSV());
    }

    public void createAndShowGUI() {
        setVisible(true);
    }

    /**
     * Exports the table data to a CSV file.
     */
    private void exportTableToCSV() {
        try (FileWriter writer = new FileWriter("products.csv")) {
            // Write the header
            writer.append("ID,Name,Quantity,Price\n");

            // Write the table data
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.append(String.format("%d,%s,%d,%.2f\n",
                        tableModel.getValueAt(i, 0),
                        tableModel.getValueAt(i, 1),
                        tableModel.getValueAt(i, 2),
                        tableModel.getValueAt(i, 3)));
            }

            JOptionPane.showMessageDialog(this, "✅ Products exported to products.csv", "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "❌ Error exporting to CSV.", "Export Failed", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}