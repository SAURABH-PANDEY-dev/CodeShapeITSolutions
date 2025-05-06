package app.view;

import app.dao.ProductDAO;
import app.dao.SalesDAO;
import app.model.BillItem;
import app.model.Product;
import app.model.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

/**
 * BillingWindow.java
 * --------------------------------------------------
 * Allows users to select products, input quantities, generate a bill,
 * and record the sale in the database with a POS-style experience.
 *
 * Author: Saurabh Pandey
 * Date: 06 May 2025
 */
public class BillingWindow extends JFrame {

    private JComboBox<String> productDropdown;
    private JTextField quantityField;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    private List<BillItem> billItems = new ArrayList<>();
    private ProductDAO productDAO = new ProductDAO();
    private SalesDAO salesDAO = new SalesDAO();

    public BillingWindow() {
        setTitle("POS - Billing System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        // Top panel with product dropdown and quantity field
        JPanel topPanel = new JPanel(new FlowLayout());

        productDropdown = new JComboBox<>();
        for (Product p : ProductDAO.getAllProducts()) {
            productDropdown.addItem(p.getId() + " - " + p.getName());
        }

        quantityField = new JTextField(5);
        JButton addButton = new JButton("Add to Bill");

        addButton.addActionListener(this::handleAddToBill);

        topPanel.add(new JLabel("Select Product:"));
        topPanel.add(productDropdown);
        topPanel.add(new JLabel("Quantity:"));
        topPanel.add(quantityField);
        topPanel.add(addButton);

        // Table to show current bill
        String[] columns = {"Product ID", "Name", "Category", "Qty", "Unit Price", "Total"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable billTable = new JTable(tableModel);

        JScrollPane tableScroll = new JScrollPane(billTable);

        // Bottom panel with total and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();

        JButton confirmButton = new JButton("Confirm & Generate Bill");
        JButton printButton = new JButton("Print Preview");
        JButton pdfButton = new JButton("Save as PDF");

        confirmButton.addActionListener(this::handleConfirmSale);
        printButton.addActionListener(this::handlePrintPreview);
        pdfButton.addActionListener(this::handleSaveAsPDF);

        buttonPanel.add(printButton);
        buttonPanel.add(pdfButton);
        buttonPanel.add(confirmButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void handlePrintPreview(ActionEvent e) {
        JTable printTable = new JTable(tableModel);
        try {
            MessageFormat header = new MessageFormat("Bill - POS Preview");
            MessageFormat footer = new MessageFormat("Page {0}");
            printTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Failed to print: " + ex.getMessage());
        }
    }

    private void handleSaveAsPDF(ActionEvent e) {
        if (billItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to generate PDF.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("Bill.pdf"));
        int option = fileChooser.showSaveDialog(this);

        if (option != JFileChooser.APPROVE_OPTION) return;

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("BILL SUMMARY", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            document.add(new Paragraph("Date: " + LocalDateTime.now()));
            document.add(Chunk.NEWLINE);

            PdfPTable pdfTable = new PdfPTable(6);
            pdfTable.setWidthPercentage(100);
            pdfTable.setSpacingBefore(10f);
            pdfTable.setSpacingAfter(10f);

            String[] headers = {"Product ID", "Name", "Category", "Qty", "Unit Price", "Total"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell);
            }

            for (BillItem item : billItems) {
                pdfTable.addCell(String.valueOf(item.getProductId()));
                pdfTable.addCell(item.getProductName());
                pdfTable.addCell(item.getCategory());
                pdfTable.addCell(String.valueOf(item.getQuantity()));
                pdfTable.addCell("₹" + item.getPricePerUnit());
                pdfTable.addCell("₹" + item.getTotalPrice());
            }

            double total = billItems.stream().mapToDouble(BillItem::getTotalPrice).sum();
            document.add(pdfTable);
            document.add(new Paragraph("Total Amount: ₹" + String.format("%.2f", total)));

            document.close();

            JOptionPane.showMessageDialog(this, "PDF saved successfully to:\n" + filePath);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to save PDF: " + ex.getMessage());
        }
    }

    private void handleAddToBill(ActionEvent e) {
        String selected = (String) productDropdown.getSelectedItem();
        if (selected == null || quantityField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a product and enter quantity.");
            return;
        }

        int productId = Integer.parseInt(selected.split(" - ")[0]);
        int qty;

        try {
            qty = Integer.parseInt(quantityField.getText().trim());
            if (qty <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity entered.");
            return;
        }

        Product product = productDAO.getProductById(productId);
        if (product == null || product.getQuantity() < qty) {
            JOptionPane.showMessageDialog(this, "Insufficient stock.");
            return;
        }

        // Check if already in bill
        for (BillItem item : billItems) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + qty);
                refreshTable();
                updateTotal();
                return;
            }
        }

        // New bill item
        BillItem item = new BillItem(productId, product.getName(), qty, product.getPrice(), product.getCategory());
        billItems.add(item);
        refreshTable();
        updateTotal();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (BillItem item : billItems) {
            tableModel.addRow(new Object[]{
                    item.getProductId(),
                    item.getProductName(),
                    item.getCategory(),
                    item.getQuantity(),
                    String.format("₹%.2f", item.getPricePerUnit()),
                    String.format("₹%.2f", item.getTotalPrice())
            });
        }
    }

    private void updateTotal() {
        double total = 0;
        for (BillItem item : billItems) {
            total += item.getTotalPrice();
        }
        totalLabel.setText(String.format("Total: ₹%.2f", total));
    }

    private void handleConfirmSale(ActionEvent e) {
        if (billItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items in bill.");
            return;
        }

        for (BillItem item : billItems) {
            // Reduce stock
            boolean reduced = productDAO.reduceStock(item.getProductId(), item.getQuantity());
            if (!reduced) {
                JOptionPane.showMessageDialog(this, "Failed to update stock for: " + item.getProductName());
                return;
            }

            // Record sale
            Sale sale = new Sale(
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getTotalPrice(),
                    LocalDateTime.now(),
                    item.getCategory()
            );
            salesDAO.recordSale(sale);
        }

        JOptionPane.showMessageDialog(this, "✅ Sale recorded and bill generated!");
        billItems.clear();
        refreshTable();
        updateTotal();
    }
}