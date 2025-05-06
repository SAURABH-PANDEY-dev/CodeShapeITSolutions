package app.view;

import app.dao.SalesDAO;
import app.model.Product;
import app.model.Sale;
import app.util.DBUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

/**
 * RecordSaleWindow.java
 * ---------------------
 * This window allows the user to select a product, enter the quantity sold, and record the sale.
 */
public class RecordSaleWindow extends JFrame {
    private JComboBox<Product> productComboBox;
    private JTextField quantityField;
    private JButton recordButton;

    private SalesDAO salesDAO;

    public RecordSaleWindow() {
        setTitle("Record Sale");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize DAO
        salesDAO = new SalesDAO();

        // Build UI
        initComponents();

        setVisible(true);
    }

    /**
     * Initializes and lays out UI components.
     */
    private void initComponents() {
        productComboBox = new JComboBox<>();
        quantityField = new JTextField(10);
        recordButton = new JButton("Record Sale");

        // TODO: Replace this with actual product loading from your ProductDAO
        // Dummy example:
        // productComboBox.addItem(new Product(1, "Pen", "Stationery", 10.0, 50));
        for (Product p : app.dao.ProductDAO.getAllProducts()) {
            productComboBox.addItem(p);
        }

        recordButton.addActionListener(this::handleRecordSale);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Select Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Quantity Sold:"));
        panel.add(quantityField);
        panel.add(new JLabel());
        panel.add(recordButton);

        add(panel);
    }

    /**
     * Event handler to process the sale recording logic.
     */
    private void handleRecordSale(ActionEvent e) {
        Product selectedProduct = (Product) productComboBox.getSelectedItem();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        String qtyText = quantityField.getText().trim();
        if (qtyText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter quantity.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.");
            return;
        }

        if (quantity <= 0 || quantity > selectedProduct.getQuantity()) {
            JOptionPane.showMessageDialog(this, "Quantity must be > 0 and <= available stock.");
            return;
        }

        // Update product quantity in DB
        selectedProduct.setQuantity(selectedProduct.getQuantity() - quantity);
        app.dao.ProductDAO.updateProduct(selectedProduct);

        // Prepare Sale object
        double totalPrice = quantity * selectedProduct.getPrice();
        Sale sale = new Sale(
                selectedProduct.getId(),
                selectedProduct.getName(),
                quantity,
                totalPrice,
                LocalDateTime.now(),
                selectedProduct.getCategory()
        );

        // Record sale to DB
        salesDAO.recordSale(sale);

        JOptionPane.showMessageDialog(this, "Sale recorded successfully.");
        dispose();
    }
}