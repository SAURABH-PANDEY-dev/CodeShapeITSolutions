package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * UpdateProductWindow.java
 * This class represents the UI for updating a product's details.
 * Fully implemented.
 */
public class UpdateProductWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Update Product");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2, 5, 5));

        JLabel idLabel = new JLabel("Enter Product ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("New Name:");
        JTextField nameField = new JTextField();

        JLabel qtyLabel = new JLabel("New Quantity:");
        JTextField qtyField = new JTextField();

        JLabel priceLabel = new JLabel("New Price:");
        JTextField priceField = new JTextField();

        JButton fetchButton = new JButton("Fetch Product");
        JButton updateButton = new JButton("Update");

        frame.add(idLabel); frame.add(idField);
        frame.add(fetchButton); frame.add(new JLabel("")); // Blank cell
        frame.add(nameLabel); frame.add(nameField);
        frame.add(qtyLabel); frame.add(qtyField);
        frame.add(priceLabel); frame.add(priceField);
        frame.add(updateButton);

        // Hide editable fields and update button initially
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        qtyLabel.setVisible(false);
        qtyField.setVisible(false);
        priceLabel.setVisible(false);
        priceField.setVisible(false);
        updateButton.setVisible(false);

        ProductController controller = new ProductController();

        fetchButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Product product = controller.getProductById(id);
                if (product != null) {
                    nameField.setText(product.getName());
                    qtyField.setText(String.valueOf(product.getQuantity()));
                    priceField.setText(String.valueOf(product.getPrice()));

                    // Show fields
                    nameLabel.setVisible(true);
                    nameField.setVisible(true);
                    qtyLabel.setVisible(true);
                    qtyField.setVisible(true);
                    priceLabel.setVisible(true);
                    priceField.setVisible(true);
                    updateButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Product not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ID format.");
            }
        });

        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                double price = Double.parseDouble(priceField.getText());

                Product updated = new Product(id, name, qty, price);
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