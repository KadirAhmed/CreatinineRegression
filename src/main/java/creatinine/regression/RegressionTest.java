package creatinine.regression;


import java.util.Random;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class RegressionTest {

    private static final int N = 16;
    private static final Random R = new Random();

    public static XYDataset createDataset(double[] yData) {
        XYSeries series = new XYSeries("Data");

        for (int i = 0; i < yData.length-1; i++) {
            series.add(i, yData[i]);
        }
        XYSeriesCollection xyData = new XYSeriesCollection(series);
        double[] coefficients = Regression.getOLSRegression(xyData, 0);
        double b = coefficients[0]; // intercept
        double m = coefficients[1]; // slope
        XYSeries trend = new XYSeries("Trend");
        double x = series.getDataItem(0).getXValue();
        trend.add(x, m * x + b);
        x = series.getDataItem(series.getItemCount() - 1).getXValue();
        trend.add(x, m * x + b);
        xyData.addSeries(trend);
        return xyData;
    }

    public static JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot("Test", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
        r.setSeriesLinesVisible(1, Boolean.TRUE);
        r.setSeriesShapesVisible(1, Boolean.FALSE);
        return chart;
    }
}