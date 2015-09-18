
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
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyMinimumViewport;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyUnbounded;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.util.Range;



public class Graph {

	 // must be a static field so the shutdown hook can get at it
    private static Chart2D staticChart;
    private Chart2D chart;
    private ADataCollector tempCollector;
    public static void main (String[] args) {
    	
        Graph myDemo = new Graph();
        myDemo.dynamicChartInit();

//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                // save the chart to a file
//                try {
//                    BufferedImage bi = staticChart.snapShot();
//                    ImageIO.write(bi, "JPEG", new File("chart.jpg"));
//                    // other possible file formats are PNG and BMP
//                } catch (Exception ex) {
//                    System.err.println("couldn't write chart to file: "+ex.getMessage());
//                }
//            }
//        });
    }

    void dynamicChartInit() {
        // Create a chart:
        chart = new Chart2D();
        // Create an ITrace:
        // Note that dynamic charts need limited amount of values!!!
        ITrace2D trace = new Trace2DLtd(200);
        
        //iTrace2D implementation will notify chart 2D instance about changes 
        trace.setColor(Color.RED);
        trace.setName("Temperature");

        // Add the trace to the chart:
        chart.addTrace(trace);

        chart.setBackground(Color.WHITE);
        chart.setForeground(Color.BLUE);
        chart.setGridColor(Color.GREEN);

        IAxis axisX = chart.getAxisX();
        axisX.setPaintGrid(true);
        IAxis axisY = chart.getAxisY();
        axisY.setPaintGrid(true);
        
        //instantiate range instance for degrees Celsius
        Range yRange = new Range(-10.0, 63.0);
        //create policy for y axis range - we want a fixed viewport
        RangePolicyFixedViewport yPolicy = new RangePolicyFixedViewport(yRange);
        //set policy to axis obj
        axisY.setRangePolicy(yPolicy);
        //print to check
        //System.out.println("Result of getRange(): " + axisY.getRange());
        
        //TODO: fix x range
        
        // Range xRange = new Range(0, 300);
//         RangePolicyUnbounded xPolicy = new RangePolicyUnbounded(xRange);
//         axisX.setRangePolicy(xPolicy);
        

//        System.out.println("Result of getRange(): " + axisX.getRange());
        
        // Create a frame.
        JFrame frame = new JFrame("Real Time Temperature Chart");
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
        // Every 1000 milliseconds a new random value is collected.
        //ADataCollector collector = new RandomDataCollectorTimeStamped(trace, 1000);
        
        // Starts an internal Thread that adds value to the chart
        tempCollector = new tempDataCollectorTimeStamped(trace, 1000);
        tempCollector.start();	
        

    }
    
    protected void stopCollector(){
    	tempCollector.stop();    	
    }
}
