package file_handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;


import candidates.Ballot;
import candidates.Candidate;
import collections.list.ArrayList;
import collections.list.LinkedList;
import collections.list.List;


/**
 * This class is created to facilitate
 * the process of reading and writing to files.
 * This is a class with the intention of only using
 * its static methods. It stores the path within the
 * project to access the input package within the data
 * folder as a static variable named -PATH-. This allows 
 * us to avoid using strict computer dependant paths and 
 * allows the Paths object to do it dynamically. Each method
 * reads only the name of the file, considering that this 
 * project will be graded using different named files, you
 * need only to change the appropiate String within the Election
 * class.
 * 
 *  @author Diego Paris
 */
public class CSVUtils {

	// Save the path to the input package within the project 
	// (Source Folder) data -> (Package) input
	private static final String PATH = "data\\input\\";

	// Constructor made private as we do not want to
	// make an instance of this class
	private CSVUtils() {}
	
	
	
	/**
	 * Reads the file address to the candidates csv file and returns an
	 * ArrayList of Candidate objects found in the file
	 * 
	 * 
	 * @param name of file as String
	 * @return ArrayList of candidates
	 */
	public static List<Candidate> getCandidates(String file) throws FileNotFoundException {
		// Creates a new arraylist of candidates
		List<Candidate> candidates = new ArrayList<Candidate>(5); 
		// Creates a new scanner object and passes the file string into the path
		// object which uses the path to locate the file within the project
		Scanner scanner = new Scanner(new File(Paths.get(PATH + file).toString()));
		
		// Iterates through each line
		while (scanner.hasNext()) {
			// Adds a newly constructed candidate from each line
			candidates.add(new Candidate(scanner.nextLine()));
		}
		// closes scanner
		scanner.close();
		// returns arrayList of candidates
		return candidates;
	}
	
	/**
	 * Reads the file address given in the parameter and returns a linkedlist
	 * of ballot objects.
	 *
	 *
	 *@param name of file as String
	 *@return a linkList of ballot objects
	 */
	public static List<Ballot> getBallots(String file) throws FileNotFoundException {
		// Creates a new linkedlist to store each ballot object
		List<Ballot> ballots = new LinkedList<>();
		
		// Creates a new scanner object and passes the file string into the path
		// object which uses the path to locate the file within the project
		Scanner scanner = new Scanner(new File(Paths.get(PATH + file).toString()));
		
		// Iterates through each line in the file
		while (scanner.hasNext()) {
			// Adds each newly constructed ballot into the linkedlist
			ballots.add(new Ballot(scanner.nextLine()));
		}
		// Closes the scanner
		scanner.close();
		
		// Returns a LinkedList of ballots
		return ballots; 
	}
	
	
	/**
	 * Writes the line given in the parameter to the results.txt file
	 * as told per the project's requirements
	 * 
	 * @param line to write to file as String
	 * @return none
	 */
	public static void write(String line) {
		try {
			// Creates a new FileWriter Object to the path given in the constructor
			// the -true- parameter indicates it will append to the end of the file.
            FileWriter writer = new FileWriter("data\\output\\results.txt", true);
            // Writes the line to the file
            writer.write(line);
            // Writes a return character for a new line
            writer.write("\r\n"); 
            // Closes the writer
            writer.close();
        } catch (IOException e) {
        	// Returns an exception
            e.printStackTrace();
        }
	}
}	
