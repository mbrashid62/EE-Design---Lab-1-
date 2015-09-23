
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;


// TODO: Auto-generated Javadoc
/**
 * The Class Graph.
 */
public class Graph {

	
   /** The frame. */
   private JFrame frame;

    /**
     * Instantiates a new graph class.
     */
    public Graph(){
    
    	 try {
             String url = "http://localhost:63342/Lab_One/main.html";
             java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
           }
           catch (java.io.IOException e) {
               System.out.println(e.getMessage());
           }

    }
    
    /**
     * Send data.
     *
     * @param temp the temp
     */
    void sendData(double temp){
    	 //write data to file
		try {

			System.out.println("Read temp: " + temp);
			String content = "Test data";

			File file = new File("H://Lab_One/temps/temps.txt");

			// if file doesn't exists, then create it
			if (!file.exists()) {
				
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
    }


	
}