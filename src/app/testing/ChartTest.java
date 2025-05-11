package app.testing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class ChartTest {
    public static void main(String[] args) {
        // Sample dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15000, "Sales", "January");
        dataset.addValue(20000, "Sales", "February");
        dataset.addValue(10000, "Sales", "March");

        // Create a bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Sales Report", // Chart title
                "Month",                // X-axis label
                "Sales Amount",         // Y-axis label
                dataset                 // Dataset
        );

        // Create and display chart in a JFrame
        JFrame frame = new JFrame("Sales Analytics Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
}