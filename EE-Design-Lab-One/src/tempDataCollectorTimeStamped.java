import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.io.ADataCollector;

public class tempDataCollectorTimeStamped extends ADataCollector {
	
	
	//last collected value
	private double m_y = 0.0;
	private double data = 0.0;
	private double newTemp;
	private int[] anArray = {0, 1, 2, 3, 4, 5, 6};
	
	private int count= 0;
	
	
	//this constructor creates an instance that will collect every latency ms a point and add it to the trace
	public tempDataCollectorTimeStamped(final ITrace2D trace,final long latency) {
		super(trace, latency);
	}

	@Override
	public TracePoint2D collectData() {
		
		double rand = Math.random();
		boolean add = (rand >=0.5) ? true: false;
		this.m_y = (add) ? this.m_y + Math.random() : this.m_y - Math.random();
		this.m_y = this.m_y + 20;
		return new TracePoint2D(System.currentTimeMillis(), this.m_y);
		
		//int randInt = (int) Math.random(10);
//		double numToDisplay = newTemp;
//		this.m_y = numToDisplay;
//	
//		double currTimeSeconds = (System.currentTimeMillis()/1000 - 10000000);
//		System.out.println("Count = " + count);
//		count++;
//		
//		return new TracePoint2D(300 - count, this.m_y);
	}
    
	protected void addDataPoint(double Temp) {
		newTemp = Temp;
	}
}
