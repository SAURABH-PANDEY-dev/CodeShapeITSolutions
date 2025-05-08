package app.view;

import app.controller.ProductController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * A simple window to allow users to choose a CSV file to import products.
 */
public class ImportCSVWindow extends JFrame {

    public ImportCSVWindow() {
        setTitle("Import Products from CSV");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(false);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        JLabel label = new JLabel("Click below to select a CSV file to import products:", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(label, BorderLayout.CENTER);

        JButton importBtn = new JButton("📂 Select CSV File");
        importBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        importBtn.setBackground(new Color(52, 152, 219));
        importBtn.setForeground(Color.WHITE);
        importBtn.setFocusPainted(false);
        importBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        importBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String path = file.getAbsolutePath();

                ProductController controller = new ProductController();
                int count = controller.importProductsFromCSV(path);

                if (count > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Imported " + count + " products.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ No products imported.");
                }

                dispose(); // Close window after import
            }
        });

        add(importBtn, BorderLayout.SOUTH);
        setVisible(true);
    }
}