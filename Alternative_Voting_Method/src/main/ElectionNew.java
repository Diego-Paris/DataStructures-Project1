package main;

import java.io.FileNotFoundException;

import candidates.Ballot;
import candidates.Candidate;
import collections.list.LinkedList;
import collections.list.List;
import file_handlers.CSVUtils;
import file_handlers.TXTwriter;

public class ElectionNew {

	List<Candidate> candidatesList;
	List<Ballot> ballotsList;
	int emptyBallots;
	int invalidBallots;
	int totalBallots;
	boolean winnerFound = false;
	int winnerID = 0;
	Candidate winner;
	int round;

	public ElectionNew() {
		emptyBallots = 0;
		invalidBallots = 0;
		totalBallots = 0;
		round = 1;
	}

	public static void main(String[] args) throws FileNotFoundException {
		ElectionNew election = new ElectionNew();
		election.run();
	}

	public void run() throws FileNotFoundException {


		candidatesList = CSVUtils.getCandidates("candidates.csv");

		ballotsList = CSVUtils.getBallots("ballots2.csv");

		totalBallots = ballotsList.size();

		TXTwriter.write("Number of ballots: " + totalBallots);


		for (Ballot ballot : ballotsList) {
			// If an empty ballot is found then add to counter,
			// then remove the ballot
			if(ballot.isEmpty()) {
				System.out.println("empty removed");
				emptyBallots++;
				totalBallots--;
				ballotsList.remove(ballot);
				continue;
			}
			// If an invalid ballot is found then add to
			// its counter as well. Remove said ballot.
			if(ballot.isInvalid(candidatesList)) {
				System.out.println("invalid removed");
				invalidBallots++;
				totalBallots--;
				ballotsList.remove(ballot);
				continue;
			}
		}

		// Outputs the total amount of invalid and blank ballots
		TXTwriter.write("Number of blank ballots " + emptyBallots);
		TXTwriter.write("Number of invalid ballots " + invalidBallots);

		// gives everyone their first choice
		for(Ballot ballot: ballotsList) {
			int candiID = -1;

			// gets current ballot's first choice
			candiID = (ballot.getCandidatebyRank(1))-1;
			candidatesList.get(candiID).addBallot(ballot);

			//TODO for debugging
			System.out.print("Ballot: [");
			ballot.printElements();
			System.out.println("] added to: " + candidatesList.get(candiID).getName() + " (" + candidatesList.get(candiID).getID()+")");

		}

		this.printCandidates();
		int round2 = 1;
		while(!winnerFound) {
			System.out.println("ROUND: " + round2);
			//gets list of canidates to remove
			
			List<Candidate> toElim = getCandisToElim(candidatesList);
            removeCandidateNew(recursiveTieSolver(toElim,2));
            this.printCandidates();
            
            for(Candidate candidate: candidatesList) {
				if(candidate.votes.size() > totalBallots/2) {
					winnerID = candidate.getID();
					winner = new Candidate(candidate);
					winnerFound = true;
					break;
				}
			}
            
            System.out.println("END OF ROUND " + round2);
            
		}
		CSVUtils.write("Winner: " + winner.getName() + " wins with " + winner.votes.size() + " #1's");
		System.out.println("Winner ID is " + winnerID);

		//removes the candidates id
		

		





	}//end of run

	public void printCandidates() {
		for (Candidate candi : candidatesList) {
			System.out.println(candi.getName() + " (" + candi.getID() + ") ");
			for (Ballot ballot : candi.votes) {
				ballot.printElements();
				System.out.println("\n");
			}
		}
	}//end of print candidates

	//	public void removeCandidate(int candidateID) {
	//		//Goes through each candidate
	//		for (Candidate candidate : candidatesList) {
	//			//Goes through each of the candidates votes
	//			for(Ballot ballot: candidate.votes) {
	//				//Eliminates the losing candidate from the ballots
	//				ballot.eliminate(candidateID);
	//				//If the ballot is empty then remove that ballot
	//				if(ballot.isEmpty()) {
	//					totalBallots--;
	//					candidate.votes.remove(ballot);
	//				}
	//			}
	//		}
	//
	//		//Goes through each candidate
	//		for (Candidate candidate : candidatesList) {
	//			//If the losing candidate is found
	//			if(candidate.getID() == candidateID) {
	//				//we go through the losing candidates ballots
	//				for(Ballot ballot: candidate.votes) {
	//					//Gets the new first choice in the ballot
	//					int newFirstChoice = ballot.getCandidatebyRank(1);
	//					//searches for the new first choice
	//					for (Candidate candidateToFind : candidatesList) {
	//						// If the new choice is found then give that canidate
	//						// the current ballot
	//						if(candidateToFind.getID() == newFirstChoice) {
	//							candidateToFind.addBallot(ballot);
	//							System.out.println("added " + ballot.getBallotNumber() + " to " + candidateToFind.getName());
	//							break;
	//						}
	//					}
	//				}
	//
	//				candidatesList.remove(candidate);
	//				break;
	//			}
	//		}
	//	}//end of remove candidates

	public void removeCandidateNew(int candidateID) {
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
			if(candidate.getID() == candidateID) {
				candiToRemove = candidate;
			}
		}//end of for in candidates

		System.out.println(candiToRemove.getName());
		for(Ballot ballot: candiToRemove.votes) {
			System.out.println("ballot number" + ballot.getBallotNumber());
		}

		for(Ballot ballot: candiToRemove.votes) {
			int newFirstChoice = ballot.getCandidatebyRank(1);
			for(Candidate candidate: candidatesList) {
				if(candidate.getID() == newFirstChoice)
					candidate.addBallot(ballot);
			}
		}
		
		CSVUtils.write("Round " + (round++) + ": " + candiToRemove.getName() + " was eliminated with " + candiToRemove.votes.size() + " #1's");
		candidatesList.remove(candiToRemove);


	}

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


		/* Due to time constraints and limited use of data
		 * structures mentioned in class until now, I could 
		 * not think of an alternative method to avoid a complex 
		 * triple nested for loop without reimagining my project's
		 * implementation and meeting the project's deadline.
		 */
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
