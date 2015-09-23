
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;


public class Graph {

    private int secondTracker;
    private boolean shouldWriteNewLine;
    private String[] jsonElements;
    private String jsonToSend;
    private boolean isFirstElement;
    public Graph(){
    	
    	secondTracker = 0;
    	shouldWriteNewLine = false;
    	jsonElements = new String[300];
    	isFirstElement = true;
    
    	 try {
             String url = "http://localhost:63342/Lab_One/main.html";
             java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
           }
           catch (java.io.IOException e) {
               System.out.println("Trouble with opening HTML file: " + e.getMessage());
           }
    }
    
    void sendToGraph (double temp){
    	
    	 //write data to file
		try {

			System.out.println("Read temp: " + temp);
			String jsonElement;
			String openArray = "[";
			String closeArray = "]";
			
			//if 300th second reset counter and don't add comma to json el
			if(secondTracker == 300){
				secondTracker = 0;
				jsonElement =  "{" + " \"seconds\": " + secondTracker + ',' + " \"temperature\" : " + temp + "}" + "\n";
				shouldWriteNewLine = false;
			}
			
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
			
			System.out.println("jsonToSend: " + jsonToSend);
			File file = new File("H://Lab_One/temps/temps.json");

			// if file doesn't exists, then create it
			if (!file.exists()) {	
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(jsonToSend);
			bw.close();

			System.out.println("Done writing to file");

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
