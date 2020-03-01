package file_handlers;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to write the results
 * of the votes given as per the project's
 * requirements.
 * 
 * @see data\\requirements\\specs.pdf
 * @author Diego Paris
 */
public class TXTwriter {

	public void outputResults() {
		try {
            FileWriter writer = new FileWriter("data\\output\\output.txt", true);
            writer.write("Hello World");
            writer.write("\r\n");   // write new line
            writer.write("wow!");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		
}
