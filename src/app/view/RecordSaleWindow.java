package app.view;

import app.controller.ProductController;
import app.model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecordSaleWindow extends JFrame {

    private JComboBox<String> productDropdown;
    private JTextField quantityField;
    private List<Product> productList;

    public RecordSaleWindow() {
        setTitle("Record Sale");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));
        setResizable(false);

        ProductController controller = new ProductController();
        productList = controller.getAllProducts();

        productDropdown = new JComboBox<>();
        for (Product p : productList) {
            productDropdown.addItem(p.getId() + " - " + p.getName());
        }

        quantityField = new JTextField();

        JButton sellBtn = new JButton("Confirm Sale");
        sellBtn.setBackground(new Color(39, 174, 96));
        sellBtn.setForeground(Color.WHITE);
        sellBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sellBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        sellBtn.addActionListener(e -> {
            int index = productDropdown.getSelectedIndex();
            String qtyText = quantityField.getText();

            if (index < 0 || qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select product and enter quantity.");
                return;
            }

            try {
                int qty = Integer.parseInt(qtyText);
                if (qty <= 0) throw new NumberFormatException();

                int productId = productList.get(index).getId();
                boolean result = controller.recordSale(productId, qty);

                if (result) {
                    JOptionPane.showMessageDialog(this, "✅ Sale recorded.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Sale failed. Check stock or quantity.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid quantity.");
            }
        });

        add(new JLabel("Select Product:", SwingConstants.CENTER));
        add(productDropdown);
        add(new JLabel("Quantity to Sell:", SwingConstants.CENTER));
        add(quantityField);
        add(sellBtn);

        setVisible(true);
    }
}
