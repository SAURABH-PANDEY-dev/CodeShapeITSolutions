package app.view;

import app.controller.ProductController;

import javax.swing.*;
import java.awt.*;

/**
 * DeleteAllConfirmationWindow.java
 * ----------------------------------------
 * UI window that asks for confirmation before deleting all products.
 * <p>
 * Author: Saurabh Pandey
 * Date: 05 May 2025
 */
public class DeleteAllConfirmationWindow extends JFrame {

    private final ProductController controller = new ProductController();

    public DeleteAllConfirmationWindow() {
        setTitle("Confirm Delete All Products");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(false);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        JLabel label = new JLabel("Are you sure you want to delete ALL products?");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel btnPanel = getPanel();

        add(label, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel getPanel() {
        JButton yesBtn = new JButton("Yes, Delete All");
        JButton cancelBtn = new JButton("Cancel");

        yesBtn.addActionListener(e -> {
            boolean success = controller.deleteAllProducts();
            if (success) {
                JOptionPane.showMessageDialog(this, "All products have been deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete products.");
            }
            dispose(); // Close this window
        });

        cancelBtn.addActionListener(e -> dispose());

        JPanel btnPanel = new JPanel();
        btnPanel.add(yesBtn);
        btnPanel.add(cancelBtn);
        return btnPanel;
    }
}