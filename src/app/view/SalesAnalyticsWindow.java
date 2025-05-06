package app.view;

import app.model.SalesAnalytics;
import app.model.Sale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

/**
 * SalesAnalyticsWindow.java
 * ---------------------------
 * Displays analytics with chart switching and dark/light theme support.
 */
public class SalesAnalyticsWindow extends JFrame {

    private final SalesAnalytics salesAnalytics;
    private JPanel chartPanel;
    private JPanel summaryPanel;
    private boolean isDarkMode = false;
    private JButton toggleButton;
    private JComboBox<String> chartTypeDropdown;

    public SalesAnalyticsWindow() {
        salesAnalytics = new SalesAnalytics();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sales Analytics Dashboard");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Summary Panel
        summaryPanel = new JPanel(new GridLayout(5, 2));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalRevenueLabel = new JLabel("Total Revenue: " + salesAnalytics.getTotalRevenue());

        List<Sale> bestSellingProducts = salesAnalytics.getBestSellingProducts(5);
        StringBuilder bestSellingProductsText = new StringBuilder("<html><ul>");
        for (Sale sale : bestSellingProducts) {
            bestSellingProductsText.append("<li>").append(sale.getProductName()).append(" - ").append(sale.getQuantitySold()).append(" sold</li>");
        }
        bestSellingProductsText.append("</ul></html>");
        JLabel bestSellingLabel = new JLabel(bestSellingProductsText.toString());

        Map<String, Double> salesByCategory = salesAnalytics.getSalesByCategory();
        StringBuilder categorySalesText = new StringBuilder("<html><ul>");
        for (Map.Entry<String, Double> entry : salesByCategory.entrySet()) {
            categorySalesText.append("<li>").append(entry.getKey()).append(": $").append(entry.getValue()).append("</li>");
        }
        categorySalesText.append("</ul></html>");
        JLabel categorySalesLabel = new JLabel(categorySalesText.toString());

        Map<String, Double> salesOverTime = salesAnalytics.getSalesOverTime("monthly");
        StringBuilder salesTimeText = new StringBuilder("<html><ul>");
        for (Map.Entry<String, Double> entry : salesOverTime.entrySet()) {
            salesTimeText.append("<li>").append(entry.getKey()).append(": $").append(entry.getValue()).append("</li>");
        }
        salesTimeText.append("</ul></html>");
        JLabel salesTimeLabel = new JLabel(salesTimeText.toString());

        chartTypeDropdown = new JComboBox<>(new String[]{"Bar Chart", "Pie Chart"});
        chartTypeDropdown.addActionListener(e -> updateChart((String) chartTypeDropdown.getSelectedItem()));

        toggleButton = new JButton("Switch to Dark Mode");
        toggleButton.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            toggleButton.setText(isDarkMode ? "Switch to Light Mode" : "Switch to Dark Mode");
            applyTheme();
        });

        summaryPanel.add(totalRevenueLabel);
        summaryPanel.add(bestSellingLabel);
        summaryPanel.add(categorySalesLabel);
        summaryPanel.add(salesTimeLabel);
        summaryPanel.add(chartTypeDropdown);
        summaryPanel.add(toggleButton);

        add(summaryPanel, BorderLayout.NORTH);

        chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(800, 500));
        add(chartPanel, BorderLayout.CENTER);

        applyTheme(); // Load UI and chart
        setVisible(true);
    }

    private void updateChart(String chartType) {
        JFreeChart chart = chartType.equals("Pie Chart") ? createPieChart() : createBarChart();
        applyChartTheme(chart.getPlot());
        chartPanel.removeAll();
        chartPanel.add(new ChartPanel(chart));
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    private JFreeChart createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> data = salesAnalytics.getSalesOverTime("monthly");
        data.forEach((key, value) -> dataset.addValue(value, "Sales", key));

        return ChartFactory.createBarChart("Sales Over Time", "Month", "Revenue", dataset);
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Double> data = salesAnalytics.getSalesByCategory();
        data.forEach(dataset::setValue);

        return ChartFactory.createPieChart("Sales by Category", dataset, true, true, false);
    }

    private void applyTheme() {
        Color bg = isDarkMode ? Color.DARK_GRAY : Color.WHITE;
        Color fg = isDarkMode ? Color.WHITE : Color.BLACK;

        getContentPane().setBackground(bg);
        summaryPanel.setBackground(bg);
        chartPanel.setBackground(bg);

        for (Component comp : summaryPanel.getComponents()) {
            comp.setForeground(fg);
            comp.setBackground(bg);
            if (comp instanceof JLabel || comp instanceof JButton || comp instanceof JComboBox) {
                comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }
        }

        updateChart((String) chartTypeDropdown.getSelectedItem()); // Refresh chart theme
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void applyChartTheme(Plot plot) {
        if (isDarkMode) {
            plot.setBackgroundPaint(Color.DARK_GRAY);
            if (plot instanceof CategoryPlot cp) {
                cp.getDomainAxis().setTickLabelPaint(Color.WHITE);
                cp.getRangeAxis().setTickLabelPaint(Color.WHITE);
                cp.setDomainGridlinePaint(Color.LIGHT_GRAY);
                cp.setRangeGridlinePaint(Color.LIGHT_GRAY);

                if (cp.getRenderer() instanceof BarRenderer barRenderer) {
                    barRenderer.setItemLabelPaint(Color.WHITE);
                }
            } else if (plot instanceof PiePlot pp) {
                pp.setLabelPaint(isDarkMode ? Color.WHITE : Color.BLACK); // Section label color
                pp.setBackgroundPaint(isDarkMode ? Color.DARK_GRAY : Color.WHITE);
                pp.setOutlinePaint(isDarkMode ? Color.LIGHT_GRAY : Color.GRAY);
                pp.setBaseSectionOutlinePaint(isDarkMode ? Color.LIGHT_GRAY : Color.GRAY); // Optional: section outlines

                // Set section colors for labels explicitly
                pp.setSectionPaint("Electronics", Color.CYAN); // or any visible color
                pp.setSectionPaint("Stationery", Color.ORANGE); // or any visible color

                // Change legend label text color
                pp.setLegendLabelToolTipGenerator((PieSectionLabelGenerator) (isDarkMode ? Color.WHITE : Color.BLACK));
            }
        } else {
            plot.setBackgroundPaint(Color.WHITE);
            if (plot instanceof CategoryPlot cp) {
                cp.getDomainAxis().setTickLabelPaint(Color.BLACK);
                cp.getRangeAxis().setTickLabelPaint(Color.BLACK);
                cp.setDomainGridlinePaint(Color.GRAY);
                cp.setRangeGridlinePaint(Color.GRAY);

                if (cp.getRenderer() instanceof BarRenderer barRenderer) {
                    barRenderer.setItemLabelPaint(Color.BLACK);
                }
            } else if (plot instanceof PiePlot pp) {
                pp.setLabelPaint(Color.BLACK);
                pp.setBackgroundPaint(Color.WHITE);
                pp.setOutlinePaint(Color.GRAY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalesAnalyticsWindow::new);
    }
}