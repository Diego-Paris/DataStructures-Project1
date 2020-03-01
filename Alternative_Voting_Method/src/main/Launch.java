package main;

/**
 * CIIC4020 / ICOM4035 - Data Structures
 * Spring 2019-2020
 * Project #1 - Alternative Voting Method 
 * 
 * This class launches the alternative voting
 * method project for Data Structures.
 * 
 * @see data\\requirements\\specs.pdf
 * @author Diego Paris
 * 
 */
public class Launch {

	public static void main(String[] args) {
    	
    	/**
    	 * Update string value below to match the
    	 * name of the csv file being used.
    	 */
    	
    	String file = "Book1.csv";
    	
    	// Creates a new Handler Object and runs the program
    	Handler handler = new Handler(file);
    	handler.run();
    }
}
