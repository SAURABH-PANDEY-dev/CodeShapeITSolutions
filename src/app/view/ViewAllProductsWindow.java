package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAllProductsWindow extends JFrame {

    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField filterField;
    private JComboBox<String> sortBox;
    private List<Product> fullProductList; // Store all products before filtering

    public ViewAllProductsWindow() {
        setTitle("View All Products");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout setup
        setLayout(new BorderLayout(10, 10));

        // Top panel: Filter and Sort
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterField = new JTextField(20);
        sortBox = new JComboBox<>(new String[]{
                "Sort: Name (A-Z)", "Sort: Name (Z-A)",
                "Sort: Quantity (Low to High)", "Sort: Quantity (High to Low)"
        });

        topPanel.add(new JLabel("🔍 Filter by Name:"));
        topPanel.add(filterField);
        topPanel.add(sortBox);
        add(topPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"ID", "Name", "Category", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Arial", Font.PLAIN, 14));
        productTable.setRowHeight(28);
        productTable.setSelectionBackground(new Color(135, 206, 235));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel: Export button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton exportButton = new JButton("Export to CSV");
        exportButton.setFont(new Font("Arial", Font.BOLD, 14));
        exportButton.setBackground(new Color(0, 123, 255));
        exportButton.setForeground(Color.WHITE);
        exportButton.setFocusPainted(false);
        exportButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.add(exportButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load and store original product list
        ProductController controller = new ProductController();
        fullProductList = controller.getAllProducts();

        // Initial population
        updateTable();

        // Action Listeners for filtering and sorting
        filterField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateTable(); }
        });

        sortBox.addActionListener(e -> updateTable());

        exportButton.addActionListener(e -> exportTableToCSV());
    }

    public void createAndShowGUI() {
        setVisible(true);
    }

    /**
     * Updates the product table based on filter and sort selections.
     */
    private void updateTable() {
        String keyword = filterField.getText().trim().toLowerCase();
        String sortOption = (String) sortBox.getSelectedItem();

        List<Product> filteredList = fullProductList.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword)
                        || p.getCategory().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        // Apply sorting
        if (sortOption != null) {
            switch (sortOption) {
                case "Sort: Name (A-Z)" ->
                        filteredList.sort(Comparator.comparing(Product::getName));
                case "Sort: Name (Z-A)" ->
                        filteredList.sort(Comparator.comparing(Product::getName).reversed());
                case "Sort: Quantity (Low to High)" ->
                        filteredList.sort(Comparator.comparingInt(Product::getQuantity));
                case "Sort: Quantity (High to Low)" ->
                        filteredList.sort(Comparator.comparingInt(Product::getQuantity).reversed());
            }
        }

        // Update table
        tableModel.setRowCount(0); // Clear table
        for (Product p : filteredList) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getCategory(),
                    p.getQuantity(),
                    p.getPrice()
            });
        }
    }

    /**
     * Exports the table data to a CSV file.
     */
    private void exportTableToCSV() {
        try (FileWriter writer = new FileWriter("products.csv")) {
            writer.append("ID,Name,Quantity,Price\n");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.append(String.format("%s,%s,%s,%s\n",
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
