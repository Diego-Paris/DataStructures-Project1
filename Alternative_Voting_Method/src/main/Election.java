package main;

import java.io.FileNotFoundException;

import candidates.Ballot;
import candidates.Candidate;
import collections.list.LinkedList;
import collections.list.List;
import file_handlers.CSVUtils;
import file_handlers.TXTwriter;


/**
 * The purpose of this class is to handle
 * all of the project's logic using it's
 * components and data structures to 
 * execute the alternative voting method process. 
 * 
 * @see data\\requirements\\specs.pdf
 * @author Diego Paris 
 */
public class Election {

	List<Candidate> candidatesList;
	List<Ballot> ballotsList;
	int emptyBallots;
	int invalidBallots;
	int totalBallots;
	boolean winnerFound = false;
	int winnerID = 0;

	public Election() {
		emptyBallots = 0;
		invalidBallots = 0;
		totalBallots = 0;
	}

	/**
	 * Runs the programs logic.
	 * 
	 * @param none
	 * @return none
	 * @throws FileNotFoundException 
	 */
	public void run() throws FileNotFoundException {
		// Gets the candidate.csv and creates a list of candidate objects
		candidatesList = CSVUtils.getCandidates("candidates.csv");

		// Get the ballots.csv and creates a list of ballot objects
		ballotsList = CSVUtils.getBallots("ballots.csv");

		// Assigns the total number of ballots that were entered.
		totalBallots = ballotsList.size();
		TXTwriter.write("Number of ballots: " + totalBallots);

		// Iterates through the ballots and finds any invalid or
		// empty ballot. If one of the prior is found, then we 
		// remove it and add to the specified counter.
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


		//TODO ^^ when iterating through the array use the toArray method
		//to avoid modifying the iterable directly


		//iterates through each ballot and assigns it to it's first choice
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

		//TODO for debugging
		for (Candidate candi : candidatesList) {
			System.out.println(candi.getName() + " (" + candi.getID() + ") ");
			for (Ballot ballot : candi.votes) {
				ballot.printElements();
				System.out.println("\n");
			}
		}

		List<Candidate> toElim;

//		for(Candidate candi : toElim) {
//			System.out.println(candi.getName());				
//		}
//
//		// will check the current list to eliminate, and begin
//		// checking the value of their ballots starting from 2. 
//		System.out.println(recursiveTieSolver(toElim, 2));
//		System.out.println(totalBallots);
		//System.out.println();

		//Goes through each round
		while(!winnerFound) {
			
			System.out.println("start of round");	

			// Iterate through each candidate and check if they have over 50%
			// of the current total ballots, if so, then they win
//			for (Candidate candi : candidatesList) {
//				// If total votes is greater than 50%, candidate won
//				if(candi.votes.size() > totalBallots/2) {
//					// winner has been found, set to true
//					winnerFound = true;
//					// save the winner's ID
//					winnerID = candi.getID();
//					// break out of the while
//					break;
//				}
//			}
			
			if(candidatesList.size() == 1) {
				winnerID = candidatesList.get(0).getID();
				winnerFound = true;
				break;
			}
			

			// Get the list of potential candidates to remove
			toElim = getCandisToElim(candidatesList);

			// Verifies if the amount of potential candidates
			// to remove this round is one
			if(toElim.size() == 1) {
				// If the list is one, then we remove that candidate
				System.out.println("We removed: " + toElim.get(0).getName() + " " + toElim.get(0).getID());
				this.removeCandidate(toElim.get(0).getID());

			} else {
				// If there is a tie, then we give the list, to the 
				// tieSolver method to return the losing candidate'sID
				System.out.println("We removed candidate with this ID " + this.recursiveTieSolver(toElim, 2));
				this.removeCandidate(this.recursiveTieSolver(toElim, 2));

			}
			System.out.println("Candidates currently in round: ");
			for (Candidate candidate : candidatesList) {
				System.out.println(".)" + candidate.getName());
			}



			System.out.println("End of round");
		}
		System.out.println("Winner ID is : " + winnerID);
		//move ballot method
		// check if the current candidate is still first choice
		// if it is, leave be. If not, then get the first choice
		// and get that candidate and add it the same ballot, once
		// added remove the one from the instance candidate


	}

	public void removeCandidate(int candidateID) {
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
				}
			}
		}

		//Goes through each candidate
		for (Candidate candidate : candidatesList) {
			//If the losing candidate is found
			if(candidate.getID() == candidateID) {
				//we go through the losing candidates ballots
				for(Ballot ballot: candidate.votes) {
					//Gets the new first choice in the ballot
					int newFirstChoice = ballot.getCandidatebyRank(1);
					//searches for the new first choice
					for (Candidate candidateToFind : candidatesList) {
						// If the new choice is found then give that canidate
						// the current ballot
						if(candidateToFind.getID() == newFirstChoice) {
							candidateToFind.addBallot(ballot);
						}
					}
				}

				candidatesList.remove(candidate);
				break;
			}
		}





	}




	// TODO when a candidate is to be removed then update the ballots and reallocate
	// those ballots
	public void removeCandidateOld(int candidateID) {
		Object[] candidatesAsArray = candidatesList.toArray();


		//Iterates through each candidate
		for (Candidate candidate : candidatesList) {
			//Iteratres through their votes
			Object[] ballotArray = candidate.votes.toArray();

			for(Ballot ballot: candidate.votes) {
				// Eliminates the candidate that lost 
				// from the remaining ballots
				ballot.eliminate(candidateID);

				//if the ballot is empty than we remove the ballot
				if(ballot.isEmpty())
					totalBallots--;
				candidate.votes.remove(ballot);
			}
		}

		//Iterates through the candidates running
		for(Candidate candi: candidatesList) {
			//Checks if we found the one we want to remove
			if(candi.getID() == candidateID) {
				// for each of those ballots we need to move them
				for(Ballot ballot: candi.votes) {
					//iterates through the list outside again
					for(Candidate candidate: candidatesList) {
						// checks if the new first choice of the ballot
						// is the candidate we're currently at
						if(ballot.getCandidatebyRank(1) == candi.getID()) {
							// if so then we add that ballot to them
							candidate.addBallot(ballot);
						}
					}	
				}
			}
			//after we're done with this then remove that person from the race
			candidatesList.remove(candi);
			break;
		}
		
		

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
	}



	// TODO +++NOTES+++
	// ballot.eliminate(3)  receives a candidate ID and eliminates it
	// everything that had a higher rank now must be adjusted
	// seperation of concerns, the original design
	// every set that has a ballot where the number 1 has been eliminated must be moved 
	// to a different set
	// number one is the highest priority 

	// remove(E obj)
	// remove(int index)
	// what happens if your objects are ints as well?
	// implement -> removePos(int index) // removes a position specifically
	// and remove an int  
}
