package candidates;

import collections.set.DynamicSet;
import collections.set.Set;;

/**
 * This class represents each individual 
 * candidate currently running for the election.
 * It has three attributes which hold its name,
 * ID, and a Set of Ballots which hold ALL ballots
 * in which the candidate is the first choice. As per
 * the project's instructions.
 * 
 * @author Diego Paris
 * @see data\\requirements\\specs.pdf
 */
public class Candidate {
	// Store the candidates name
	private String candidateName;
	// Stores the candidates ID
	private String candidateID;
	// Stores the ballots in a set in which the candidate is the first choice
	// as per the instructions.
	public Set<Ballot> votes;
	
	// Similar to the ballot class, constructs a new candidate
	// object from a line taken by the candidates.csv file
	public Candidate(String line) {
		// Converts the string to an array
		String[] info = line.split(",");
		// Assigns the name to the candidate object
		this.candidateName = info[0];
		// Assigns the ID to the candidate object
		this.candidateID = info[1];
		// Constructs a new DynamicSet in which we can use to store
		// the ballots where the instance is the first choice
		this.votes = new DynamicSet<Ballot>(10);
	}
	
	// Constructs a new candidate from the given candidate
	// object passed through the parameter
	public Candidate(Candidate candi) {
		// Assigns the same name as the parameter
		this.candidateName = candi.candidateName;
		// Assigns the same ID as the parameter
		this.candidateID = candi.candidateID;
		// Constructs a new dynamicSet for the ballots
		this.votes = new DynamicSet<Ballot>(10);
		
		// Iterates through all the ballots of the 
		// given candidate
		for(Ballot ballot: candi.votes) {
			// Adds each ballot to the instance
			// being constructed
			this.votes.add(ballot);
		}
	}
	
	/**
	 * Verifies that the instance and the parameter are 
	 * the same candidate by checking it's ID number. 
	 * 
	 * @param Candidate object
	 * @return Returns true if the ID number is the same; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		// Checks if the object isn't null and 
		// the ID number of each candidate are equal
		return (obj != null && this.getID() == ((Candidate)obj).getID());
	}
	
	
	/**
	 * Takes the ballot given in the parameter and adds it to
	 * the instance's Set of ballots.
	 *
	 *@param a Ballot object.
	 *@return true if the ballots was successfully added; false otherwise.
	 */
	public boolean addBallot(Ballot obj) {
		return votes.add(obj);
	}
	
	/**
	 * Gets the name of the candidate object.
	 * 
	 * @param none
	 * @return The candidate's name as a String
	 */
	public String getName() {
		return this.candidateName;
	}
	
	
	/**
	 * Gets the ID number of the candidate. 
	 * 
	 * @param none
	 * @return the candidate's ID number as an integer.
	 * 
	 */
	public int getID() {
		// Parses the candidate's ID string attribute
		// into a string and returns it
		return Integer.parseInt(this.candidateID);
	}
	
}
