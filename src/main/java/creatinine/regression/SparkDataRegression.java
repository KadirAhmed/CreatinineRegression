package creatinine.regression;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;


import javax.swing.*;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SparkDataRegression {
    static int referenceId = 7356324;
    public static void main(String[] args) {
        // Initialize Spark session
        SparkSession spark = SparkSession.builder()
                .appName("SparkJFreeChartExample")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> df  = spark.read().format("csv")
                .option("header", true)
                .option("delimiter", ";")
                .load("/home/ahmed/Desktop/gary/merged_56_files.csv");  // Can also load from relational DB

          df.createOrReplaceTempView("table ");

        List<Row> rows =  spark.sql("SELECT " +
                "`LIS Reference Datetime`," +
                "`LIS Result: Numeric Result`" + " FROM table" +
                " where `Reference Key` = " + referenceId +
                " order by `LIS Reference Datetime` asc").collectAsList();

        String[] xData = new String[rows.size()];
        double[] yData = new double[rows.size()];
//
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            xData[i] = row.getString(0); // Assuming first column is x-axis data
            yData[i] = Double.parseDouble(row.getString(1)); // Assuming second column is y-axis data
            System.out.println(  xData[i]+ " " + yData[i] );
        }

        // Stop Spark session
        spark.stop();

        RegressionTest rt = new RegressionTest( String.valueOf(referenceId));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        XYDataset dataset = rt.createDataset(yData);
        JFreeChart chart = rt.createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        };
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    // Method to create and display the JFreeChart
    private static void createAndShowChart(String[] xData, double[] yData) {
        // Create dataset
       // XYSeries series = new XYSeries("Data Series");
        TimeSeries series = new TimeSeries("Data");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (int i = 0; i < xData.length; i++) {
            try {
                series.add(new Minute(dateFormat.parse(xData[i])), yData[i]);
            } catch (ParseException | SeriesException e) {
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
      //  XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Create chart
//        JFreeChart chart = ChartFactory.createXYLineChart(
//                "Spark Data Visualization", // Chart title
//                "X Axis", // X-axis label
//                "Y Axis", // Y-axis label
//                dataset, // Dataset
//                PlotOrientation.VERTICAL, // Plot orientation
//                true, // Show legend
//                true, // Use tooltips
//                false // Configure URLs
//        );
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Date Regression Plot Example",
                "Date",
                "Value",
                dataset,
                true,
                true,
                false
        );


        // Display chart
        JFrame frame = new JFrame("Spark Data Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}

