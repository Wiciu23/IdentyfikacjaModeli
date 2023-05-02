import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PlotFunction implements Observer {

    private Double[] parameters;
    private Double[] arguments;
    private Particle.FunctionType functionType;

    public PlotFunction(Double[] parameters, Double[] arguments, Particle.FunctionType functionType) {
        this.parameters = parameters;
        this.arguments = arguments;
        this.functionType = functionType;
    }

    public static void main(String[] args) {
        XYSeries series = new XYSeries("sin(x)");
        for (double i = 0; i <= Math.PI * 2; i += 0.1) {
            series.add(i, Math.sin(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart("Function","x","y",dataset, PlotOrientation.VERTICAL,true,true,false);

        ChartFrame frame = new ChartFrame("Wykres", chart);
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
    }

    @Override
    public void update(Observable o, Object arg) {
        Double[] a = (Double[]) arg;

    }
}

