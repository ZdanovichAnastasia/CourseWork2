package view.grafic;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Chart extends ApplicationFrame {
    public Chart( String title ) {
        super( title );
    }

    private static CategoryDataset createDataset(ArrayList<String[]> data) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (String[] val : data) {
            dataset.addValue(Double.parseDouble(val[1]), val[0], val[2]);
        }
        return dataset;
    }
    private static JFreeChart createChart(CategoryDataset dataset )
    {
        JFreeChart chart = ChartFactory.createLineChart(
                "Прибыль компаний",  // title
                "",
                "",                // y-axis label
                dataset
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setShapesVisible(true);
        return chart;
    }
    public static JPanel createDemoPanel(ArrayList<String[]> data)
    {
        JFreeChart chart = createChart(createDataset(data) );
        return new ChartPanel(chart);
    }
}

