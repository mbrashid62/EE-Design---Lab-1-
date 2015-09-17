
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.io.ADataCollector;
import info.monitorenter.gui.chart.io.RandomDataCollectorTimeStamped;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DSimple;



public class Graph {

	 // must be a static field so the shutdown hook can get at it
    private static Chart2D staticChart;

    public static void main (String[] args) {
    	
    	String path = System.getProperty("user.dir");
    	System.out.println("path: " + path);

    	
        Graph myDemo = new Graph();
        myDemo.staticDemo();
        myDemo.dynamicDemo();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // save the chart to a file
                try {
                    BufferedImage bi = staticChart.snapShot();
                    ImageIO.write(bi, "JPEG", new File("chart.jpg"));
                    // other possible file formats are PNG and BMP
                } catch (Exception ex) {
                    System.err.println("couldn't write chart to file: "+ex.getMessage());
                }
            }
        });
    }

    private void staticDemo() {
        // Create a chart:
        staticChart = new Chart2D();

        // Create an ITrace:
        ITrace2D trace = new Trace2DSimple();
        // color it red
        trace.setColor(Color.RED);
        // give it a name to display
        trace.setName("Static Demo");

        // make it colorful
        staticChart.setBackground(Color.WHITE);
        staticChart.setForeground(Color.BLUE);
        staticChart.setGridColor(Color.GREEN);

        // turn on grids along both axes
        IAxis axisX = staticChart.getAxisX();
        axisX.setPaintGrid(true);
        IAxis axisY = staticChart.getAxisY();
        axisY.setPaintGrid(true);

        // Add all points, as it is static:
        Random random = new Random();
        for (int i=100; i>=0; i--) {
            trace.addPoint(i, random.nextDouble()*10.0+i);
        }
        // Add the trace to the chart:
        staticChart.addTrace(trace);

        // Create a frame.
        JFrame frame = new JFrame("MinimalStaticChart");
        // add the chart to the frame:
        frame.getContentPane().add(staticChart);
        frame.setSize(600, 400);
        // the program is terminated if one of the windows is closed 
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing (WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        // Make it visible
        frame.setVisible(true);
    }

    private void dynamicDemo() {
        // Create a chart:
        Chart2D chart = new Chart2D();
        // Create an ITrace:
        // Note that dynamic charts need limited amount of values!!!
        ITrace2D trace = new Trace2DLtd(200);
        trace.setColor(Color.RED);
        trace.setName("Dynamic Demo");

        // Add the trace to the chart:
        chart.addTrace(trace);

        chart.setBackground(Color.LIGHT_GRAY);
        chart.setForeground(Color.BLUE);
        chart.setGridColor(Color.GREEN);

        IAxis axisX = chart.getAxisX();
        axisX.setPaintGrid(true);
        IAxis axisY = chart.getAxisY();
        axisY.setPaintGrid(true);

        // Create a frame.
        JFrame frame = new JFrame("MinimalDynamicChart");
        // add the chart to the frame:
        frame.getContentPane().add(chart);
        frame.setSize(600, 400);
        frame.setLocation(200, 200);
        // the program is terminated if one of the windows is closed 
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e){
                    System.exit(0);
                }
            }
        );
        // Make it visible
        frame.setVisible(true);
        // Every 50 milliseconds a new value is collected.
        ADataCollector collector = new RandomDataCollectorTimeStamped(trace, 50);
        // Start an internal Thread that adds the values:
        collector.start();
    }
}
