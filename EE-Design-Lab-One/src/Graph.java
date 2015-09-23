

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Graph {

    private int secondTracker;
    private String[] jsonElements;
    private String jsonToSend;
    private boolean isFirstElement;
    
    
    public String getHtmlURL() {
		return htmlURL;
	}

	public void setHtmlURL(String htmlURL) {
		this.htmlURL = htmlURL;
	}

	private String htmlURL;
    public Graph(){
    	
    	secondTracker = 0;
    	jsonElements = new String[300];
    	isFirstElement = true;
    	
    	setHtmlURL("http://localhost:63342/Lab_One/static-chart.html");
    	openGraph();
    }
    
    void openGraph() {
    	
    	String url = getHtmlURL();
    	setHtmlURL("http://localhost:63342/Lab_One/dynamic-chart.html");
    	try {

            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
          }
          catch (java.io.IOException e) {
              System.out.println("Trouble with opening HTML file: " + e.getMessage());
          }
	}

	void sendToGraph (double temp, boolean error){
    	
    	 //write data to file
		try {

			String jsonElement;
			
			if(secondTracker == 300){
				secondTracker = 0;
			}
			
			//if it's the first element avoid the comma
			if(isFirstElement){
				jsonElement = "{" + " \"seconds\": " + secondTracker + ',' + " \"temperature\" : " + temp + "}" + "\n";
				isFirstElement = false;
			}
			
			//else add json entry as normal
			else{
				jsonElement = ",{" + " \"seconds\": " + secondTracker + ',' + " \"temperature\" : " + temp + "}" + "\n";
			}
			
			//store json entry in array
			jsonElements[secondTracker] = jsonElement;
			
			//concatenate all array contents and add array brackets for subsequent file write
			jsonToSend =  "[" + addArrayToString(jsonElements) + "]";
			//check
//			System.out.println("jsonToSend: " + jsonToSend);
			

			File file = new File("H://Lab_One/temps/temps.json");

			// if file doesn't exists, then create it
			if (!file.exists()) {	
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(jsonToSend);
			bw.close();

//			System.out.println("Done writing to file");

			secondTracker++;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	private String addArrayToString(String[] jsonElements2) {
		
		int count = 0;
		String result = "";
		while(jsonElements2[count] != null)
		{
		    result = result + jsonElements2[count];
		    count++;
		}
		return result;
	}

   
	
}
