
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
import info.monitorenter.gui.chart.events.AxisActionSetTitle;
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
    private IAxis axisX;
    private IAxis axisY;
    
    private Range yRangeCelsius;
    private Range yRangeFarenheit;
    private ITrace2D trace;
    private JFrame frame;
    private RangePolicyFixedViewport yPolicy;
    
    public static void main (String[] args) {
        Graph myDemo = new Graph();
        myDemo.dynamicChartInit();
    }
    public Graph(){
    	
    	 // Create a chart:
        chart = new Chart2D();
        
        // Create an ITrace: Note that dynamic charts need limited amount of values!!!
        trace = new Trace2DLtd(200);
        
        //instantiate range instance for degrees Celsius
    	yRangeCelsius = new Range(-10, 63);
    	yRangeFarenheit = new Range(63, 145);
    
    	yPolicy = new RangePolicyFixedViewport();
    	
    	// Create a frame.
        frame = new JFrame("Real Time Temperature Chart");
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
    }

    void dynamicChartInit() {
        
        //iTrace2D implementation will notify chart 2D instance about changes 
        trace.setColor(Color.RED);
        trace.setName("Temperature");

        // Add the trace to the chart:
        chart.addTrace(trace);

        chart.setBackground(Color.WHITE);
        chart.setForeground(Color.BLUE);
        chart.setGridColor(Color.GREEN);

        /************Handle X Axis********************/
        axisX = chart.getAxisX();
        axisX.setPaintGrid(true);
        axisX.setTitle("Time (seconds ago)");
        
        /***********Handle Y Axis*************************/
        axisY = chart.getAxisY();
        axisY.setPaintGrid(true);
        axisY.setTitle("Temp Celsius");
        axisY.setRangePolicy(yPolicy);
        
        setTempRangePolicy(axisY, yRangeCelsius);

        // Starts an internal Thread that adds value to the chart
        tempCollector = new tempDataCollectorTimeStamped(trace, 1000);
        tempCollector.start();	
        
    }
    
    protected void stopCollector(){
    	tempCollector.stop();    	
    }
    
    protected void startCollector(){
    	tempCollector.start();
    }

	public void setAxisCelsius() {
        axisY.setTitle("Temp (Celsius)");	
        setTempRangePolicy(axisY, yRangeCelsius);
	}
	
	public void setAxisFarenheit(){
		axisY.setTitle("Temp (Farenheit)");
		setTempRangePolicy(axisY, yRangeFarenheit);
	}
	
	public void setTempRangePolicy(IAxis axis, Range range){
		axisY.setRangePolicy(new RangePolicyFixedViewport(range));
	}
	
}
