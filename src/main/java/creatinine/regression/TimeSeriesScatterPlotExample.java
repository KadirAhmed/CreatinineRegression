package creatinine.regression;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import java.awt.*;

import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;


import org.jfree.chart.axis.DateAxis;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Second;

import static org.jfree.chart.ui.UIUtils.centerFrameOnScreen;


public class TimeSeriesScatterPlotExample extends ApplicationFrame {

    public TimeSeriesScatterPlotExample(String title) {
        super(title);
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private XYDataset createDataset() {
        TimeSeries series = new TimeSeries("Random Data");
        Second current = new Second();
        for (int i = 0; i < 100; i++) {
            series.add(current, Math.random() * 100);
            current = (Second) current.next();
        }
        return new TimeSeriesCollection(series);
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Time Series Scatter Plot Example",
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
        dateAxis.setDateFormatOverride(new java.text.SimpleDateFormat("mm:ss"));
        plot.setRangeGridlinePaint(Color.BLACK);
        return chart;
    }

    public static void main(String[] args) {
        TimeSeriesScatterPlotExample example = new TimeSeriesScatterPlotExample("Time Series Scatter Plot Example");
        example.pack();
        centerFrameOnScreen(example);
        example.setVisible(true);
    }
}

