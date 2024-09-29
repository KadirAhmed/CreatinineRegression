package creatinine.regression;

import org.jfree.chart.ui.ApplicationFrame;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.awt.Color;

import static org.jfree.chart.ui.UIUtils.centerFrameOnScreen;

public class TimeSeriesAnalysis extends ApplicationFrame {

    public TimeSeriesAnalysis(String title) {
        super(title);
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private XYDataset createDataset() {
        TimeSeries series = new TimeSeries("Data");
        series.add(new Month(1, 2023), 100);
        series.add(new Month(2, 2023), 150);
        series.add(new Month(3, 2023), 200);
        series.add(new Month(4, 2023), 180);
        series.add(new Month(5, 2023), 210);
        series.add(new Month(6, 2023), 220);
        series.add(new Month(7, 2023), 250);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Date Regression Plot Example",
                "Date",
                "Value",
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // Add a regression line (example: linear regression)
        // Example code for adding a regression line:
        // You need to calculate the regression line based on your dataset
        // and add it similarly to how the original series is added.

        return chart;
    }

    public static void main(String[] args) {
        TimeSeriesAnalysis example = new TimeSeriesAnalysis("Date Regression Plot Example");
        example.pack();
        centerFrameOnScreen(example);
        example.setVisible(true);
    }
}
