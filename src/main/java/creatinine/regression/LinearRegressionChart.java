package creatinine.regression;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;

public class LinearRegressionChart {

    public static void main(String[] args) {
        // Example data points
        double[] xData = {1, 2, 3, 4, 5};
        double[] yData = {2, 3, 4, 5, 6};

        // Calculate linear regression
        double[] regression = calculateLinearRegression(xData, yData);
        double slope = regression[0];
        double intercept = regression[1];

        // Create dataset
        XYSeries dataSeries = new XYSeries("Data");
        for (int i = 0; i < xData.length; i++) {
            dataSeries.add(xData[i], yData[i]);
        }

        XYSeries regressionSeries = new XYSeries("Linear Regression");
        for (int i = 0; i < xData.length; i++) {
            double yPredicted = slope * xData[i] + intercept;
            regressionSeries.add(xData[i], yPredicted);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(dataSeries);
        dataset.addSeries(regressionSeries);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Linear Regression Chart",  // Chart title
                "X",                      // X-axis label
                "Y",                      // Y-axis label
                dataset,                  // Dataset
                PlotOrientation.VERTICAL,
                true,                     // Show legend
                true,                     // Show tooltips
                false                     // Show URLs
        );

        // Display chart
        JFrame frame = new JFrame("Linear Regression Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }

    private static double[] calculateLinearRegression(double[] xData, double[] yData) {
        // Perform linear regression calculations here
        // You can use Apache Commons Math or implement it manually
        // Example:
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        for (int i = 0; i < xData.length; i++) {
            sumX += xData[i];
            sumY += yData[i];
            sumXY += xData[i] * yData[i];
            sumX2 += xData[i] * xData[i];
        }

        double n = xData.length;
        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        return new double[]{slope, intercept};
    }
}
