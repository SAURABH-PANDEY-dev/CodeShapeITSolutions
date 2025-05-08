package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.getRootPane;

/**
 * UpdateProductWindow.java
 * This class represents the UI for updating a product's details.
 * Now includes category update.
 */
public class UpdateProductWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Update Product");
        frame.setSize(400, 350);  // Slightly increased height for category
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        frame.setLayout(new GridLayout(7, 2, 5, 5)); // 7 rows for new category

        // --- Input Fields ---
        JLabel idLabel = new JLabel("Enter Product ID:");
        JTextField idField = new JTextField();

        JButton fetchButton = new JButton("Fetch Product");

        JLabel nameLabel = new JLabel("New Name:");
        JTextField nameField = new JTextField();

        JLabel qtyLabel = new JLabel("New Quantity:");
        JTextField qtyField = new JTextField();

        JLabel priceLabel = new JLabel("New Price:");
        JTextField priceField = new JTextField();

        JLabel categoryLabel = new JLabel("New Category:"); // ✅ Category label
        String[] categories = {"Electronics", "Stationery", "Groceries", "Fashion", "Other"};
        JComboBox<String> categoryDropdown = new JComboBox<>(categories); // ✅ Dropdown

        JButton updateButton = new JButton("Update");

        // --- Add Components to Frame ---
        frame.add(idLabel); frame.add(idField);
        frame.add(fetchButton); frame.add(new JLabel("")); // Spacer
        frame.add(nameLabel); frame.add(nameField);
        frame.add(qtyLabel); frame.add(qtyField);
        frame.add(priceLabel); frame.add(priceField);
        frame.add(categoryLabel); frame.add(categoryDropdown); // ✅ Added category field
        frame.add(updateButton); frame.add(new JLabel("")); // Spacer

        // --- Hide fields initially ---
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        qtyLabel.setVisible(false);
        qtyField.setVisible(false);
        priceLabel.setVisible(false);
        priceField.setVisible(false);
        categoryLabel.setVisible(false);         // ✅ Hide category
        categoryDropdown.setVisible(false);      // ✅ Hide dropdown
        updateButton.setVisible(false);

        ProductController controller = new ProductController();

        // --- Fetch Product Action ---
        fetchButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Product product = controller.getProductById(id);
                if (product != null) {
                    nameField.setText(product.getName());
                    qtyField.setText(String.valueOf(product.getQuantity()));
                    priceField.setText(String.valueOf(product.getPrice()));
                    categoryDropdown.setSelectedItem(product.getCategory()); // ✅ Set dropdown

                    // Show fields
                    nameLabel.setVisible(true);
                    nameField.setVisible(true);
                    qtyLabel.setVisible(true);
                    qtyField.setVisible(true);
                    priceLabel.setVisible(true);
                    priceField.setVisible(true);
                    categoryLabel.setVisible(true);        // ✅ Show category
                    categoryDropdown.setVisible(true);     // ✅ Show dropdown
                    updateButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Product not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format.");
            }
        });

        // --- Update Product Action ---
        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                double price = Double.parseDouble(priceField.getText());
                String category = categoryDropdown.getSelectedItem().toString(); // ✅ Get selected category

                Product updated = new Product(id, name, qty, price, category); // ✅ Updated constructor
                boolean success = controller.updateProduct(updated);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Product updated successfully.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Update failed. Product may not exist.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity or price.");
            }
        });

        frame.setVisible(true);
    }
}