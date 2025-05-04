package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false; // disable editing for all the cells
            }
        };

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
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void createAndShowGUI() {
        setVisible(true);
    }
}