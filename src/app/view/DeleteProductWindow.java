package app.view;

import app.controller.ProductController;

import javax.swing.*;
import java.awt.*;

/**
 * DeleteProductWindow.java
 * This class represents the UI for deleting a product by ID.
 */
public class DeleteProductWindow {

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Delete Product by ID");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Center window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel idLabel = new JLabel("Enter Product ID to Delete:");
        JTextField idField = new JTextField();
        JButton deleteButton = new JButton("Delete");
        JLabel messageLabel = new JLabel("");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(new JLabel()); // empty cell
        panel.add(deleteButton);
        panel.add(new JLabel()); // empty cell
        panel.add(messageLabel);

        deleteButton.addActionListener(e -> {
            try {
                int productId = Integer.parseInt(idField.getText());
                ProductController controller = new ProductController();
                boolean success = controller.deleteProductById(productId);

                if (success) {
                    messageLabel.setText("Product deleted successfully.");
                } else {
                    messageLabel.setText("Product not found.");
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid numeric ID.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}