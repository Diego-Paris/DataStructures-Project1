package main;

import java.io.FileNotFoundException;

import candidates.Ballot;
import candidates.Candidate;
import collections.list.LinkedList;
import collections.list.List;
import file_handlers.CSVUtils;

/**
 * This class executes all of the logic
 * that is to be used in the project. The main
 * method is found here as per project's requirements.
 * The election begins by creating lists which pertains
 * to candidates and ballots. Firstly it iterates through
 * each ballot and indicates invalid and empty ballots to
 * dispose of them. Once these ballots have been removed 
 * they are given to the candidate of their first choice,
 * in which they are store in the instances' Sets.
 * Once this is done, we begin each round. A more detailed
 * of the work done in each round is found below.
 * 
 * @author Diego Paris
 * @see data\\requirements\\specs.pdf
 */
public class Election {
	
	// Saves the name of the file used for ballots
	String ballotFile;
	
	// Saves the name of the file used for candidates
	String candidatesFile;
	
	// List to store each candidate object
	List<Candidate> candidatesList;
	// Temporary list to store ALL ballots
	List<Ballot> ballotsList;
	// Counter to keep track of empty ballots
	int emptyBallots;
	// Counter to keep track of invalid ballots
	int invalidBallots;
	// Keeps track of the total ballots being considered
	// for each round
	int totalBallots;
	// Stores true if the winner has been found, false otherwise
	boolean winnerFound = false;
	// Saves the winning candidate into a seperate object
	Candidate winner;
	// Keeps track of the current round
	int round;

	// Constructs a new election object
	public Election() {
		// Sets each counter their first iterations respectively
		emptyBallots = 0;
		invalidBallots = 0;
		totalBallots = 0;
		round = 1;
		
	}

	/**
	 * For sake of cleanliness and preference I have opted to create
	 * a new Election object and invoke it's run method, in which
	 * all the logic for the project is being executed.
	 * 
	 * @param none
	 * @return none
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		/**
		 * This project is design to read files from the data folder -> input package
		 * to test out this project, please drag and drop your files into the input package
		 * and make sure to copy them into it when prompted. If the names are different then
		 * you may change them below at the beginning of the run function. Thank you <3.
		 */
		
		// Constructs a new election object
		Election election = new Election();
		// Invokes the election object's run method
		election.run();
	}
	
	/**
	 * Executes the logic used for the project
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public void run() throws FileNotFoundException {

		// Change the name of the string below for candidates and ballots respectively
		
		// Initializes a new list of candidate objects using the file name given
		candidatesList = CSVUtils.getCandidates("candidates.csv");

		// Initializes a new list of ballot objects using the file name given
		ballotsList = CSVUtils.getBallots("ballots.csv");
		
		// Stores the total number of ballots submitted
		totalBallots = ballotsList.size();
		
		// Writes to results the amount of ballots submitted
		CSVUtils.write("Number of ballots: " + totalBallots);

		// Iterates through each ballot
		for (Ballot ballot : ballotsList) {
			// If an empty ballot is found then add to counter,
			// then remove the ballot
			if(ballot.isEmpty()) {
				// Adds to the total number of empty ballots
				emptyBallots++;
				// Removes from the total number of ballots
				totalBallots--;
				// Removes the current ballot from the list
				ballotsList.remove(ballot);
				continue;
			}
			// If an invalid ballot is found then add to
			// its counter as well. Remove said ballot.
			if(ballot.isInvalid(candidatesList)) {
				// Adds to the total number of invalid ballots
				invalidBallots++;
				// Removes from the total number of ballots
				totalBallots--;
				// removes the current ballot from the list
				ballotsList.remove(ballot);
				continue;
			}
		}

		// Outputs the total amount of invalid and blank ballots
		CSVUtils.write("Number of blank ballots " + emptyBallots);
		CSVUtils.write("Number of invalid ballots " + invalidBallots);

		// Assigns everyone their first choice
		for(Ballot ballot: ballotsList) {
			int candiID = -1;

			// gets current ballot's first choice
			candiID = (ballot.getCandidatebyRank(1))-1;
			
			// Adds the current ballot to its first choice
			candidatesList.get(candiID).addBallot(ballot);
		}

		
		// Continues to run until a winner has been found, each round
		// is executed within this while loop
		while(!winnerFound) {
			
			// Gets the list of potential candidates to remove
			List<Candidate> toElim = getCandisToElim(candidatesList);
			
			// Using recursiveTieSolver returns the ID from the candidate that 
			// is being removed. A more in depth explanation of how
			// recursiveTieSolver does this can be found at its declaration.
			// The number 2 is being give into the parameter as we want it to 
			// begin solving a potential tie by checking the amount of second 
			// preferences and so on.
			// Using removeCandidate uses the candidateID given to eliminate
			// that canidate from each ballot and reorganizes them
			// a more detailed explanation can be found at its declaration.
            removeCandidate(recursiveTieSolver(toElim,2));
            
            
            // Iterates through each candidate
            for(Candidate candidate: candidatesList) {
            	// If the candidate has more than 50 percent of total
            	// amount of votes, then that candidate wins
				if(candidate.votes.size() > totalBallots/2) {
					// Creates a new candidate object using the candidate 
					// who won
					winner = new Candidate(candidate);
					// Set to true since winner has been found
					winnerFound = true;
					break;
				}
			}
            
            
            
		}
		// Writes to results file the winner of the given ballots and candidates
		CSVUtils.write("Winner: " + winner.getName() + " wins with " + winner.votes.size() + " #1's");

	}//end of run

	
	
	/**
	 * Takes in the integer value of a candidate to be removed
	 * from the list being considered for the election.
	 * 
	 * @param candidateID to remove
	 * @return none 
	 */
	public void removeCandidate(int candidateID) {
		// Sets the candidate object to null
		Candidate candiToRemove = null;
		//Goes through each candidate
		for (Candidate candidate : candidatesList) {
			//Goes through each of the candidates votes
			for(Ballot ballot: candidate.votes) {
				//Eliminates the losing candidate from the ballots
				ballot.eliminate(candidateID);
				//If the ballot is empty then remove that ballot
				if(ballot.isEmpty()) {
					totalBallots--;
					candidate.votes.remove(ballot);
				}//end of if
			}//end of for ballots
			// If candidate is found then reference it
			if(candidate.getID() == candidateID) {
				candiToRemove = candidate;
			}
		}//end of for in candidates

		
		// Iterates through each of the losing
		// candidates votes
		for(Ballot ballot: candiToRemove.votes) {
			// Gets the new first choice of the ballot
			int newFirstChoice = ballot.getCandidatebyRank(1);
			// Iterates through the candidates
			for(Candidate candidate: candidatesList) {
				// If the current candidate is first choice then add ballot
				if(candidate.getID() == newFirstChoice)
					candidate.addBallot(ballot);
			}
		}
		// Write to result the current candidate removed for the round
		CSVUtils.write("Round " + (round++) + ": " + candiToRemove.getName() + " was eliminated with " + candiToRemove.votes.size() + " #1's");
		// Removes the candidate from the list
		candidatesList.remove(candiToRemove);


	}

	
	/**
	 * Takes in a list of potential candidates to lose in the current round
	 * and the number for which to start looking for the next preference,
	 * which in this implementation is 2. If the list is one returns that ID
	 * if not then solves for the edge cases explained in the method.
	 * 
	 * @param List of potential candidates to remove and number to begin looking for preference
	 * @return the ID of the candidate who lost the tie
	 */
	public int recursiveTieSolver(List<Candidate>list, int n) {
		// Creates a new list using the given parameter
		// we will modify these candidate objects
		List<Candidate> toEliminate = new LinkedList<Candidate>();
		for (Candidate candidate : list) {
			toEliminate.add(new Candidate(candidate));
		}

		// Creates a variable in which we store
		// the highest ID number, if we exceed
		// the exceed the rank we're considering
		// to solve for
		int maxID = 0;

		// base case, if list size is one
		// then returns that candidate's ID
		if (list.size() == 1)
			return list.get(0).getID();


		// If the rank we are given exceeds the number
		// of current candidates still running in the
		// election, then we received a case where the
		// candidates are tied, even considering every
		// ballot, therefore per the projects specifications
		// we remove the candidate with the highest ID number
		if (n > candidatesList.size()) {
			// Iterates through the candidates we're
			// considering to eliminate
			for(Candidate candi: toEliminate) {
				// If the candidate's ID is higher
				// then the max, then we set the max
				// to the that ID.
				if(candi.getID() > maxID)
					maxID = candi.getID();
			}
			// Returns the ID number of the candidate
			// with the highest ID number value
			return maxID;
		}


		
		// Iterate through the candidates up to eliminate
		for (Candidate candiToElim: toEliminate) {
			// Iterates through the outer candidates list
			for (Candidate candi: candidatesList) {
				// Iterates through each of the candidates ballot
				for (Ballot ballot : candi.votes) {
					// If the candidate at the current rank we're looking
					// for is one of the candidate up to be eliminated, then
					// we remove him
					if(ballot.getCandidatebyRank(n) == candiToElim.getID())
						// we add that ballot
						candiToElim.addBallot(ballot);
				}

			}
		}

		// We pass the list of candidates with their newly appointed
		// ballots to the list we're eliminating from
		toEliminate = getCandisToElim(toEliminate);

		// Pass the newly (likely) shortened list into the function,
		// if the list has shortened to one then it will return that
		// candidate's ID, if not, then it will continue to the next
		// rank of preference and continue the process.
		return recursiveTieSolver(toEliminate, n+1);
	}

	
	/**
	 * Takes in the list of candidates currently running for the election
	 * and checks for the lowest amount of votes and adds them to a list 
	 * to return. May return a list longer than the length of one if there
	 * is a tie for the current round
	 *
	 * @param The list of candidates currently running
	 * @return A list of potential candidates to remove from the election
	 */
	public List<Candidate> getCandisToElim(List<Candidate> list) {
		// Creates a new list to save potential candidates to remove.
		List<Candidate> toEliminate = new LinkedList<Candidate>();

		// saves the int from the first candidate's number of ballots
		int min = list.get(0).votes.size();

		// iterate through each candidate to find the
		// least number of votes
		for (Candidate candi : list) {
			// if the amount of votes in the current
			// candis less than the actual min, then
			// set min to least amount.
			if(candi.votes.size() < min)
				min = candi.votes.size();
		}

		// iterate through each candi to find
		for (Candidate candi : list) {
			// If the current candidate has the minimum
			// amount of votes, then add to the toEliminate
			// list
			if (candi.votes.size() == min) {
				toEliminate.add(new Candidate(candi));
			}	
		}		
		// returns the list of potential candidates to eliminate
		return toEliminate;
	}//end of find candidates to eliminate

}//end of class
