package app.model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class SalesChartFactory {
    public static JFreeChart create(String type, String title, DefaultCategoryDataset dataset, DefaultPieDataset pieDataset) {
        return switch (type) {
            case "Pie Chart", "3D Pie Chart", "Ring Chart", "Donut Chart" ->
                    ChartFactory.createPieChart(title, pieDataset, true, true, false);
            case "Line Chart" -> ChartFactory.createLineChart(title, "Item", "Revenue", dataset);
            case "Area Chart" -> ChartFactory.createAreaChart(title, "Item", "Revenue", dataset);
            case "Stacked Bar Chart" -> ChartFactory.createStackedBarChart(title, "Item", "Revenue", dataset);
            default -> ChartFactory.createBarChart(title, "Item", "Revenue", dataset);
        };
    }
}