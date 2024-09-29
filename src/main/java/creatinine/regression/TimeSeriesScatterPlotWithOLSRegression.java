package creatinine.regression;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.text.SimpleDateFormat;

import static org.jfree.chart.ui.UIUtils.centerFrameOnScreen;

public class TimeSeriesScatterPlotWithOLSRegression extends ApplicationFrame {

    public TimeSeriesScatterPlotWithOLSRegression(String title) {
        super(title);
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private XYDataset createDataset() {
        TimeSeries series = new TimeSeries("Random Data");
        Millisecond current = new Millisecond();
        for (int i = 0; i < 100; i++) {
            series.add(current, Math.random() * 100);
            current = (Millisecond) current.next();
        }
        return new TimeSeriesCollection(series);
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Time Series Scatter Plot with OLS Regression Line",
                "Time",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis dateAxis = new DateAxis("Time");
        plot.setDomainAxis(dateAxis);
        dateAxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
        plot.setRangeGridlinePaint(Color.BLACK);

        // Add OLS regression line
        double[] coefficients = Regression.getOLSRegression(dataset, 0);
        double slope = coefficients[0];
        double intercept = coefficients[1];

        // Create regression line dataset
        XYSeries regressionSeries = createRegressionSeries((TimeSeriesCollection) dataset, slope, intercept);
        XYDataset regressionDataset = new XYSeriesCollection(regressionSeries);
        plot.setDataset(1, regressionDataset);
        plot.mapDatasetToRangeAxis(1, 0); // Use same range axis as the original dataset

        // Customize the regression line appearance
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        renderer.setSeriesPaint(0, Color.RED); // Set color of the regression line
        plot.setRenderer(1, renderer);

        return chart;
    }

    private XYSeries createRegressionSeries(TimeSeriesCollection dataset, double slope, double intercept) {
        TimeSeries originalSeries = dataset.getSeries(0);
        XYSeries regressionSeries = new XYSeries("OLS Regression Line");

        // Generate points for the regression line
        RegularTimePeriod period = originalSeries.getTimePeriod(0);
        for (int i = 0; i < originalSeries.getItemCount(); i++) {
            double x = period.getFirstMillisecond();
            double y = intercept + slope * x;
            regressionSeries.add(x, y);
            period = period.next();
        }

        return regressionSeries;
    }

    public static void main(String[] args) {
        TimeSeriesScatterPlotWithOLSRegression example = new TimeSeriesScatterPlotWithOLSRegression("Time Series Scatter Plot with OLS Regression Line");
        example.pack();
        centerFrameOnScreen(example);
        example.setVisible(true);
    }
}
