package app.view;

import app.model.SalesAnalytics;
import app.model.Sale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * SalesAnalyticsWindow
 *
 * This class creates a Sales Analytics Dashboard with two sections:
 * 1. Top: Sales by Category (Table + Chart)
 * 2. Bottom: Sales by Product (Table + Chart)
 *
 * Each section includes:
 * - A scrollable table displaying sales data
 * - A dropdown below the table to select chart type (bar, pie, etc.)
 * - A chart that updates based on dropdown selection
 *
 * Dark theme styling is applied to all components for consistency.
 */
public class SalesAnalyticsWindow extends JFrame {

    private final SalesAnalytics salesAnalytics;
    private JTable categoryTable, productTable;
    private JPanel categoryChartPanel, productChartPanel;

    public SalesAnalyticsWindow() {
        salesAnalytics = new SalesAnalytics();
        initializeUI();
    }

    /**
     * Initializes the UI layout and components.
     */
    private void initializeUI() {
        setTitle("Sales Analytics Dashboard");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1, 5, 5)); // Two rows: category and product sections

        // ===== TOP HALF: Sales by Category =====
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        // Category table and dropdown (left side)
        categoryTable = new JTable();
        JScrollPane categoryScroll = new JScrollPane(categoryTable);
        categoryScroll.setBorder(BorderFactory.createTitledBorder("Sales by Category"));

        JComboBox<String> categoryChartDropdown = createChartTypeDropdown();
        categoryChartDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        categoryChartDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryChartDropdown.addActionListener(e ->
                updateCategoryChart(categoryChartPanel, (String) categoryChartDropdown.getSelectedItem()));

        // Stack table and dropdown vertically
        Box categoryLeftBox = Box.createVerticalBox();
        categoryLeftBox.add(categoryScroll);
        categoryLeftBox.add(Box.createVerticalStrut(5));
        categoryLeftBox.add(categoryChartDropdown);

        // Category chart panel (right side)
        categoryChartPanel = new JPanel(new BorderLayout());
        categoryChartPanel.setBorder(BorderFactory.createTitledBorder("Category Chart"));

        topPanel.add(categoryLeftBox, BorderLayout.WEST);
        topPanel.add(categoryChartPanel, BorderLayout.CENTER);

        // ===== BOTTOM HALF: Sales by Product =====
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        // Product table and dropdown (left side)
        productTable = new JTable();
        JScrollPane productScroll = new JScrollPane(productTable);
        productScroll.setBorder(BorderFactory.createTitledBorder("Sales by Product"));

        JComboBox<String> productChartDropdown = createChartTypeDropdown();
        productChartDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        productChartDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        productChartDropdown.addActionListener(e ->
                updateProductChart(productChartPanel, (String) productChartDropdown.getSelectedItem()));

        // Stack table and dropdown vertically
        Box productLeftBox = Box.createVerticalBox();
        productLeftBox.add(productScroll);
        productLeftBox.add(Box.createVerticalStrut(5));
        productLeftBox.add(productChartDropdown);

        // Product chart panel (right side)
        productChartPanel = new JPanel(new BorderLayout());
        productChartPanel.setBorder(BorderFactory.createTitledBorder("Product Chart"));

        bottomPanel.add(productLeftBox, BorderLayout.WEST);
        bottomPanel.add(productChartPanel, BorderLayout.CENTER);

        // Add both panels to the main frame
        add(topPanel);
        add(bottomPanel);

        // Load initial data
        loadTableData();
        updateCategoryChart(categoryChartPanel, "Bar Chart");
        updateProductChart(productChartPanel, "Bar Chart");

        // Optional: Apply dark theme
        applyDarkTheme(getContentPane());

        setVisible(true);
    }

    /**
     * Creates a dropdown containing chart type options.
     */
    private JComboBox<String> createChartTypeDropdown() {
        return new JComboBox<>(new String[]{
                "Bar Chart", "Pie Chart", "Line Chart", "Area Chart",
                "Stacked Bar Chart", "3D Pie Chart", "Ring Chart", "Donut Chart"
        });
    }

    /**
     * Loads sales data into both tables.
     */
    private void loadTableData() {
        // Category Table
        Map<String, Double> salesByCategory = salesAnalytics.getSalesByCategory();
        Map<String, Integer> quantityByCategory = salesAnalytics.getSoldQuantitiesByCategory();
        DefaultTableModel categoryModel = new DefaultTableModel(new String[]{"Category", "Quantity", "Amount"}, 0);
        for (String category : salesByCategory.keySet()) {
            double amount = salesByCategory.get(category);
            int quantity = quantityByCategory.getOrDefault(category, 0);
            categoryModel.addRow(new Object[]{category, quantity, amount});
        }
        categoryTable.setModel(categoryModel);

        // Product Table
        List<Sale> productSales = salesAnalytics.getProductSales();
        DefaultTableModel productModel = new DefaultTableModel(new String[]{"Product", "Quantity", "Revenue"}, 0);
        for (Sale sale : productSales) {
            productModel.addRow(new Object[]{sale.getProductName(), sale.getQuantitySold(), sale.getTotalPrice()});
        }
        productTable.setModel(productModel);
    }

    /**
     * Updates the category chart based on the selected type.
     */
    private void updateCategoryChart(JPanel panel, String type) {
        JFreeChart chart = salesAnalytics.createChartForCategoryTable(categoryTable, type);
        applyChartTheme(chart.getPlot());
        panel.removeAll();
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Updates the product chart based on the selected type.
     */
    private void updateProductChart(JPanel panel, String type) {
        JFreeChart chart = salesAnalytics.createChartForProductTable(productTable, type);
        applyChartTheme(chart.getPlot());
        panel.removeAll();
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Applies a dark theme to the container and all child components.
     */
    private void applyDarkTheme(Container container) {
        Color bg = Color.DARK_GRAY;
        Color fg = Color.WHITE;

        container.setBackground(bg);
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel panel) {
                panel.setBackground(bg);
                applyDarkTheme(panel);
            } else if (comp instanceof JScrollPane sp) {
                sp.setBackground(bg);
                sp.getViewport().setBackground(bg);
            } else if (comp instanceof JTable table) {
                table.setBackground(bg);
                table.setForeground(fg);
                table.setGridColor(fg);
                table.setSelectionBackground(Color.GRAY);
                table.getTableHeader().setBackground(bg);
                table.getTableHeader().setForeground(fg);
            } else if (comp instanceof JComboBox<?> combo) {
                combo.setBackground(Color.DARK_GRAY);
                combo.setForeground(Color.WHITE);
                combo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                combo.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                  boolean isSelected, boolean cellHasFocus) {
                        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        c.setBackground(isSelected ? Color.GRAY : Color.DARK_GRAY);
                        c.setForeground(Color.WHITE);
                        return c;
                    }
                });
            }
        }
    }

    /**
     * Applies dark-themed styling to the chart's plot.
     */
    private void applyChartTheme(Plot plot) {
        Color bg = new Color(0,255,0);
        Color fg = new Color(0,0,255);

        plot.setBackgroundPaint(bg);

        if (plot instanceof CategoryPlot cp) {
            cp.getDomainAxis().setTickLabelPaint(fg);
            cp.getRangeAxis().setTickLabelPaint(fg);
            cp.setDomainGridlinePaint(Color.GRAY);
            cp.setRangeGridlinePaint(Color.GRAY);

            if (cp.getRenderer() instanceof BarRenderer br) {
                br.setSeriesPaint(0, Color.ORANGE);
                br.setItemLabelPaint(fg);
            } else if (cp.getRenderer() instanceof LineAndShapeRenderer lr) {
                lr.setSeriesPaint(0, Color.CYAN);
            }
        } else if (plot instanceof PiePlot pp) {
            pp.setLabelPaint(fg);
            pp.setBackgroundPaint(bg);
            pp.setOutlinePaint(Color.GRAY);
        }
    }

    /**
     * Launches the dashboard.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalesAnalyticsWindow::new);
    }
}