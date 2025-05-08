package app.view;

import javax.swing.*;

/**
 * SearchProductWindow.java
 * This class represents the UI for searching a product by ID.
 * Currently, a placeholder for future implementation.
 */
public class SearchProductWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Search Product by ID");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instruction label
        JLabel instructionLabel = new JLabel("Enter Product ID:");
        panel.add(instructionLabel);

        // Input field
        JTextField idField = new JTextField(10);
        panel.add(idField);

        // Search button
        JButton searchButton = new JButton("Search Product");
        panel.add(Box.createVerticalStrut(10)); // Add spacing
        panel.add(searchButton);

        // Area to show result
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(Box.createVerticalStrut(10)); // Add spacing
        panel.add(scrollPane);

        // Action when button is clicked
        searchButton.addActionListener(e -> {
            String input = idField.getText().trim();
            if (input.isEmpty()) {
                resultArea.setText("Please enter a Product ID.");
                return;
            }

            try {
                int productId = Integer.parseInt(input);
                app.controller.ProductController controller = new app.controller.ProductController();
                app.model.Product product = controller.getProductById(productId);

                if (product != null) {
                    resultArea.setText(
                            "Product Found:\n" +
                                    "ID: " + product.getId() + "\n" +
                                    "Name: " + product.getName() + "\n" +
                                    "Quantity: " + product.getQuantity() + "\n" +
                                    "Price: " + product.getPrice()
                    );
                } else {
                    resultArea.setText("No product found with ID: " + productId);
                }
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid ID. Please enter a numeric value.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}