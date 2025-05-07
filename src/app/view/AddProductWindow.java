package app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import app.controller.ProductController;
import app.model.Product;

public class AddProductWindow extends JFrame {
    private JTextField idField, nameField, quantityField, priceField;
    private JButton addButton;
    private JComboBox<String> categoryDropdown;  // ✅ Category dropdown

    public AddProductWindow() {
        // Frame setup
        setTitle("Add Product");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the form

        // Create panel and layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.setBackground(new Color(84,84,84));


        // Add labels and text fields
        panel.add(new JLabel("Product ID:"));
        idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        // ✅ Add category label and dropdown
        panel.add(new JLabel("Category:"));
        String[] categories = {"Electronics", "Stationery", "Groceries", "Fashion", "Other"};
        categoryDropdown = new JComboBox<>(categories);
        panel.add(categoryDropdown);


        // Add button
        addButton = new JButton("Add Product");
        panel.add(addButton);

        // Add panel to frame
        add(panel);

        // Button listener to add product
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
    }

    private void addProduct() {
        try {
            // Get data from text fields
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            // Create a new product object
            String category = categoryDropdown.getSelectedItem().toString(); // ✅ Get selected value
            Product product = new Product(id, name, quantity, price, category); // ✅ Pass to constructor

            // Call the controller to add the product
            ProductController productController = new ProductController();
            productController.addProduct(product);

            // Show success message
            JOptionPane.showMessageDialog(this, "Product added successfully!");
            clearFields(); // Clear the fields after adding
        } catch (NumberFormatException ex) {
            // Handle invalid input
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for ID, quantity, and price.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        categoryDropdown.setSelectedIndex(0); // ✅ Reset dropdown
    }

    public static void main(String[] args) {
        // Show the Add Product window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddProductWindow().setVisible(true);
            }
        });
    }

    public void createAndShowGUI() {
        setVisible(true);
    }
}