package file_handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import candidates.BallotOld;
import candidates.Ballot;
import candidates.Candidate;
import collections.list.ArrayList;
import collections.list.LinkedList;
import collections.list.List;

public class CSVUtils {

	
	private static final String PATH = "data\\input\\";

	//TODO
	//Invalid ballots: due to empty ballots, repeated candidates, 
	
	private CSVUtils() {
		
	}
	
	public static List<Candidate> getCandidates(String file) throws FileNotFoundException {
		List<Candidate> candidates = new ArrayList<Candidate>(5); 
		
		Scanner scanner = new Scanner(new File(Paths.get(PATH + file).toString()));
		
		while (scanner.hasNext()) {
			candidates.add(new Candidate(scanner.nextLine()));
		}
		scanner.close();
		
		return candidates;
	}
	
	public static List<Ballot> getBallots(String file) throws FileNotFoundException {
		List<Ballot> ballots = new LinkedList<>();
		
		Scanner scanner = new Scanner(new File(Paths.get(PATH + file).toString()));
		
		while (scanner.hasNext()) {
			ballots.add(new Ballot(scanner.nextLine()));
		}
		scanner.close();
		
		return ballots; 
	}
	
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
