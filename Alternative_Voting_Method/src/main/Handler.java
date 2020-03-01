package main;

import file_handlers.*;



/**
 * The purpose of this class is to handle
 * all of the project's logic using it's
 * components and data structures to 
 * execute the alternative voting method process. 
 * 
 * @see data\\requirements\\specs.pdf
 * @author Diego Paris
 * 
 */


public class Handler {

	private String file;

	public Handler(String file) {
		this.file = file;	
	}

	/**
	 * Runs the programs logic.
	 * 
	 * @param none
	 * @return none
	 */
	public void run() {
		
		// Passes the file name given to a csvReader object
		CSVreader reader = new CSVreader(this.file);
		// Reads the file given to the object and writes to console
		reader.readFile();

		// Creates a new text writer object
		TXTwriter write = new TXTwriter();
		// Writes results to \\data\\output\\output.txt
		write.outputResults();
	}




}
