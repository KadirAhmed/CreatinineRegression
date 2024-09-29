package creatinine.regression;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class ScatterPlot {

    public static void main(String[] args) throws IOException {

        // Generate some sample data
        int numPoints = 100;
        long[] timestamps = new long[numPoints];
        double[] values = new double[numPoints];
        Random rand = new Random();
        long startTime = Instant.now().minus(numPoints, ChronoUnit.DAYS).toEpochMilli();
        for (int i = 0; i < numPoints; i++) {
            timestamps[i] = startTime + i * 24 * 60 * 60 * 1000; // 1 day increment
            values[i] = rand.nextDouble() * 100;
        }

        // Create XChart
        XYChart chart = new XYChartBuilder()
                .width(800).height(600)
                .title("Scatter Plot")
                .xAxisTitle("Time")
                .yAxisTitle("Value")
                .build();

        // Customize chart style
        chart.getStyler().setMarkerSize(6);

        // Add scatter plot (data points)
        for (int i = 0; i < numPoints; i++) {
            chart.addSeries("Data Point " + i, new double[]{timestamps[i]}, new double[]{values[i]}).setMarker(SeriesMarkers.CIRCLE);
        }

        // Save the chart as a PNG file
        BitmapEncoder.saveBitmap(chart, "./ScatterPlot", BitmapFormat.PNG);

        // Show the chart (optional)
        new SwingWrapper(chart).displayChart();
    }
}
