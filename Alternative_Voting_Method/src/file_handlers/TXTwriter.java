package file_handlers;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to write the results
 * of the votes given to a text file as per 
 * the project's requirements.
 * 
 * @see data\\requirements\\specs.pdf
 * @author Diego Paris
 */
public class TXTwriter {

	public static void write(String line) {
		try {
            FileWriter writer = new FileWriter("data\\output\\output.txt", true);
            writer.write(line);
            writer.write("\r\n"); 
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
		
}
