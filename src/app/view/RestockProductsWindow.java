package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Window to display low stock products and allow restocking.
 */
public class RestockProductsWindow extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private ProductController controller;

    public RestockProductsWindow(ProductController controller) {
        this.controller = controller;
        setTitle("Restock Low Stock Products");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    /**
     * Initializes UI components.
     */
    private void initComponents() {
        // Panel for layout and styling
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Low Stock Products - Restock");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 75, 99));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Table model
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only allow editing the quantity column
            }
        };

        // Table setup
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(200, 230, 250));
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button
        JButton updateBtn = new JButton("Update Stock");
        updateBtn.setBackground(new Color(0, 123, 255));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateBtn.setFocusPainted(false);
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBtn.addActionListener(e -> updateStock());

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(panel.getBackground());
        btnPanel.add(updateBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        // Populate table with low stock
        loadLowStockProducts();

        add(panel);
    }

    /**
     * Loads low stock products into the table.
     */
    private void loadLowStockProducts() {
        List<Product> lowStockProducts = controller.getLowStockProducts();
        for (Product p : lowStockProducts) {
            tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getQuantity()});
        }
    }

    /**
     * Updates product stock based on table edits.
     */
    private void updateStock() {
        boolean success = true;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int id = (int) tableModel.getValueAt(i, 0);
            int newQty;

            try {
                newQty = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                if (newQty < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity at row " + (i + 1),
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean updated = controller.restockProduct(id, newQty);
            if (!updated) {
                success = false;
            }
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Stock updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Some products could not be updated.",
                    "Update Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}