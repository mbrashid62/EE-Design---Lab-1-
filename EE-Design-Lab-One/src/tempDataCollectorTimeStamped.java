import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.io.ADataCollector;

public class tempDataCollectorTimeStamped extends ADataCollector {
	
	
	//last collected value
	private double m_y = 0.0;
	private double data = 0.0;
	private int[] anArray = {0, 1, 2, 3, 4, 5, 6};
	
	//this constructor creates an instance that will collect every latency ms a point and add it to the trace
	public tempDataCollectorTimeStamped(final ITrace2D trace,final long latency) {
		super(trace, latency);
	}

	@Override
	public TracePoint2D collectData() {
		
//		int randInt = (int) Math.random(10);
		double numToDisplay = 20;
		this.m_y = numToDisplay;
	
		return new TracePoint2D(System.currentTimeMillis(), this.m_y);
		
	}

}
