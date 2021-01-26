package view.grafic;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Diagrama extends ApplicationFrame {
    public Diagrama( String title ) {
        super( title );
    }

    private static PieDataset createDataset(ArrayList<String[]> data, String nameC) {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        for(String[] mas: data){
            if(mas[0].equals(nameC)) {
                dataset.setValue(mas[1], Double.parseDouble(mas[2]));
            }
        }

        return dataset;
    }
    private static JFreeChart createChart(PieDataset dataset )
    {
        JFreeChart chart = ChartFactory.createPieChart(
                "Расходы объектов затарт",  // chart title
                dataset,        // data
                true,           // include legend
                true,
                false);

        return chart;
    }
    public static JPanel createDemoPanel(ArrayList<String[]> data,  String name)
    {
        JFreeChart chart = createChart(createDataset(data, name) );
        return new ChartPanel(chart);
    }
}
