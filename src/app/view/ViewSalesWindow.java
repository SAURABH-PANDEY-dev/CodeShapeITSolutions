package app.view;

import app.dao.SalesDAO;
import app.model.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ViewSalesWindow.java
 * ---------------------
 * Displays a table showing all recorded sales from the database,
 * with options to filter by category and date range, and export filtered data.
 */
public class ViewSalesWindow extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> categoryComboBox;
    private JTextField fromDateField;
    private JTextField toDateField;
    private List<Sale> allSales;

    public ViewSalesWindow() {
        setTitle("Sales History");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Top Panel with filters
        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem("All");
        topPanel.add(categoryComboBox);

        topPanel.add(new JLabel("From Date (yyyy-MM-dd):"));
        fromDateField = new JTextField(10);
        topPanel.add(fromDateField);

        topPanel.add(new JLabel("To Date (yyyy-MM-dd):"));
        toDateField = new JTextField(10);
        topPanel.add(toDateField);

        JButton filterButton = new JButton("Apply Filters");
        JButton exportButton = new JButton("Export Filtered to CSV");

        topPanel.add(filterButton);
        topPanel.add(exportButton);

        add(topPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Product ID", "Product Name", "Quantity Sold", "Total Price", "Date-Time", "Category"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load all sales data
        SalesDAO salesDAO = new SalesDAO();
        allSales = salesDAO.getAllSales();

        // Populate category filter dropdown
        for (Sale sale : allSales) {
            if (sale.getCategory() != null && !sale.getCategory().isEmpty()
                    && ((DefaultComboBoxModel<String>) categoryComboBox.getModel()).getIndexOf(sale.getCategory()) == -1) {
                categoryComboBox.addItem(sale.getCategory());
            }
        }

        // Show all data initially
        updateTable(allSales);

        // Filter button logic
        filterButton.addActionListener(e -> applyFilters());

        // Export button logic
        exportButton.addActionListener(e -> exportFilteredSalesToCSV());

        setVisible(true);
    }

    /**
     * Applies category and date range filters to the sales list and updates the table.
     */
    private void applyFilters() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String fromDateStr = fromDateField.getText().trim();
        String toDateStr = toDateField.getText().trim();

        List<Sale> filtered = allSales.stream()
                .filter(sale -> {
                    boolean matchesCategory = selectedCategory.equals("All") || selectedCategory.equals(sale.getCategory());

                    boolean matchesDate = true;
                    try {
                        LocalDateTime dateTime = sale.getSaleDateTime();
                        if (!fromDateStr.isEmpty()) {
                            LocalDate from = LocalDate.parse(fromDateStr);
                            matchesDate &= !dateTime.toLocalDate().isBefore(from);
                        }
                        if (!toDateStr.isEmpty()) {
                            LocalDate to = LocalDate.parse(toDateStr);
                            matchesDate &= !dateTime.toLocalDate().isAfter(to);
                        }
                    } catch (Exception ignored) {
                        // Invalid format ignored; show all
                    }

                    return matchesCategory && matchesDate;
                })
                .collect(Collectors.toList());

        updateTable(filtered);
    }

    /**
     * Updates the JTable with the given list of sales.
     */
    private void updateTable(List<Sale> salesList) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Sale sale : salesList) {
            tableModel.addRow(new Object[]{
                    sale.getProductId(),
                    sale.getProductName(),
                    sale.getQuantitySold(),
                    sale.getTotalPrice(),
                    sale.getSaleDateTime().toString(),
                    sale.getCategory()
            });
        }
    }

    /**
     * Exports currently visible (filtered) rows to a CSV file.
     */
    private void exportFilteredSalesToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Filtered Sales CSV");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (java.io.FileWriter writer = new java.io.FileWriter(fileChooser.getSelectedFile())) {
                writer.write("Product ID,Product Name,Quantity Sold,Total Price,Date-Time,Category\n");

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    writer.write(String.format("%s,%s,%s,%.2f,%s,%s\n",
                            tableModel.getValueAt(i, 0),
                            tableModel.getValueAt(i, 1),
                            tableModel.getValueAt(i, 2),
                            Double.parseDouble(tableModel.getValueAt(i, 3).toString()),
                            tableModel.getValueAt(i, 4),
                            tableModel.getValueAt(i, 5)));
                }

                JOptionPane.showMessageDialog(this, "✅ Export successful!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Failed to export CSV.");
                e.printStackTrace();
            }
        }
    }
}