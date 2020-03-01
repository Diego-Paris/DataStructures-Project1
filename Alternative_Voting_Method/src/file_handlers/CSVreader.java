package file_handlers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/** 
 * This class is used to facilitate reading
 * ballots given in .csv format. It is designed
 * to read directly from this project's folder
 * labeled "data".
 * 
 * @author Diego Paris
 */
public class CSVreader {
	
	private String filename;
	private List<String> lines;
	
	public CSVreader(String filename) {
		this.filename = filename;
	}
	
	
	/**
	 * Reads the file given to the object and writes to the console.
	 * 
	 * @param none
	 * @return none
	 */
	public void readFile() {
		try 
		{
			lines = Files.readAllLines(Paths.get("data\\ballots\\" + filename));
    		
			for(String line: lines) 
			{
				System.out.println(line);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
}
