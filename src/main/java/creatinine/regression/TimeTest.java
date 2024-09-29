package creatinine.regression;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class TimeTest {

    private static XYDataset createDataset() throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        TimeSeries series = new TimeSeries("Temperature");
        series.add(new Minute(f.parse("Sat Nov 20 12:41:00 PST 2021")), 72.5);
        series.add(new Minute(f.parse("Sat Nov 20 13:11:00 PST 2021")), 70.7);
        series.add(new Minute(f.parse("Sat Nov 20 13:32:00 PST 2021")), 80.7);
        return new TimeSeriesCollection(series);
    }

    private static JFreeChart createChart(final XYDataset dataset) {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        f.setTimeZone(TimeZone.getTimeZone("PST"));
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Test", "Time", "Temperture °F", dataset, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        r.setDefaultShapesVisible(true);
        DateAxis domain = (DateAxis) plot.getDomainAxis();
        domain.setDateFormatOverride(f);
        domain.setVerticalTickLabels(true);
        return chart;
    }

    public static void main(String[] args) throws ParseException {

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 250);
            }
        };
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}