package candidates;

import collections.list.ArrayList;
import collections.list.List;

/**
 * This class stores all the information given in
 * each ballot entry in a single ArrayList. This method
 * of implementation exploits the ArrayList's use case of 
 * constant time .get() and automatic reallocation, where
 * the index of the candidate is it's current rank. At the
 * expense of O(n) complexity when a object is removed.
 * No inserting of values is used in this implementation,
 * after the construction of the object.  
 * 
 * @author Diego Paris
 * @see data\\requirements\\specs.pdf
 */
public class Ballot {

	// Stores the ballot info given in the parameter
	public List<Integer> data;
	// Represents if the ballot is invalid due it skipping a rank
	public boolean isInvalidDueToRank;

	public Ballot(String info) {
		isInvalidDueToRank = false;
		int index = 0;
		int pos = 0;
		int rank = 0;
		int candidate = 0;
		
		// Gets the row of the file as a string and converts it into an array
		String[] dataToConvert = info.split(",");

		// Creates a new ArrayList where the length is equal to the number of
		// columns in the file
		data = new ArrayList<Integer>(dataToConvert.length);

		// Iterates through the array of strings
		for (String string : dataToConvert) 
		{

			// If first index, then add as ballot ID
			if(index == 0) 
			{
				data.add(Integer.parseInt(string));
				index++;
				continue;
			}
			
			// Finds index of delimiter
			pos = string.indexOf(":");
			// get the integer value of rank
			rank = Integer.parseInt(string.substring(pos+1));
			// gets the integer value of candidateID
			candidate = Integer.parseInt(string.substring(0, pos));
			
			// If specified rank is not sorted, then a preference was skipped
			// the current ballot is invalid due to rank
			if(rank != index)
				isInvalidDueToRank = true;
			// adds the candidate to the index associated with it's rank
			data.add(candidate);
			
			// adds to index
			index++;
		}
	}

	/**
	 * Gets the number of the ballot entry. Which in this implementation
	 * is store in the first element of the ArrayList.
	 * 
	 * @param none
	 * @return The ballot's ID number
	 */
	public int getBallotNumber() {
		return data.get(0);
	}

	
	/**
	 * Gets the rank of the candidate as given by the parameter.
	 * Since this implementation uses the index of the ArrayList
	 * to represent the rank, we must iterate through the ArrayList
	 * until we find the desired candidate and then return it's index.
	 * 
	 * @param The candidate's ID.
	 * @return The rank that the candidate currently has. -1 if it wasn't been found 
	 */
	public int getRankbyCandidate(int candidateID) {
		// Iterates through the elements in the ballot
		for (int i = 1; i < data.size(); i++) {
			// If the current iteration matches the ID given
			// then return the index at which it has been found
			if(data.get(i) == candidateID)
				return i;
		}
		// returns -1 if the candidate ID wasn't found
		return -1;
	}
	
	
	
	/**
	 * Gets the candidate at the rank given in the parameters.
	 * Since this implementation uses the ArrayList's index to 
	 * represent it's current rank, then we can exploit this to 
	 * return directly at that current index
	 * 
	 * @param rank of the canidate
	 * @return the candidate at the current rank
	 */
	public int getCandidatebyRank(int rank) {
		return data.get(rank);
	}
	
	/**
	 * Eliminates the candidate as indicated by the value given
	 * in the parameter. Automatically adjusts the order of the 
	 * candidates rank to match that of its index.
	 *
	 * @param the ID of the candidate that is to be removed.
	 * @return true if the candidateID has been removed, false otherwise.
	 */
	public boolean eliminate(int candidateID) {
		//Iterates over the elements to find the ID
		for (int i = 1; i < data.size(); i++) {
			// If the current ID has been found
			// then remove it from the ballot
			if(data.get(i) == candidateID) {
				data.remove(i);
				//If removed, return true
				return true;
			}
		}
		//return false if the ID wasn't found
		return false;
	}

	
	/**
	 * Checks the total number of elements in the ballot 
	 * that is being called.
	 * 
	 * @param none
	 * @return Total number of elements. 
	 */
	public int total() {
		return data.size();
	}
	
	/**
	 * Checks if the ballot is invalid by verifying different edge cases.
	 * -A ballot is invalid if one of the rank preferences has 
	 * been skipped, as checked per the constructor.
	 * -A ballot is invalid if one of the candidateID's is not within the range
	 * of candidates given in the parameter.
	 * -A ballot is invalid if one of the candidates has been voted for more
	 * than once.
	 * 
	 * @param A list containg candidate objects.
	 * @return true if the ballot is invalid, false otherwise.
	 */
	public boolean isInvalid(List<Candidate> candidates) {
		int candiID = -1;
		
		// Checks if the called ballot is invalid, due to 
		// skipping a rank, as checked by the constructor
		if(isInvalidDueToRank == true)
			return true;
		
		// Checks if the ballot is invalid but verifying that
		// each candidateID is within the range of valid amount of candidates
		// using the candidates list given in the parameter.
		for (int i = 1; i < data.size(); i++) {
			// Gets the candidateID at the current iteration.
			candiID = data.get(i);
			// If the current ID is less than 1 -or- greater
			// than the amount of candidates than the ballot is invalid.
			if (candiID < 1 || candiID > candidates.size())
				return true;
		}
		
		// Checks if the ballot is invalid by iterating over the array
		// and checking if that ID matches any other ID in the list, if
		// so then the candidate was voted for twice and the ballot is invalid.
		for (int i = 1; i < data.size(); i++) {
			// Performs the iteration after the object being compared
			// to check for duplicates 
			for (int j = i + 1; j < data.size(); j++) {
				// if the object is not null -and- the ID at i equals the ID at j
				// then the candidate appears more than once in the ballot, therefore
				// it is invalid.
				if (data.get(i) != null && data.get(i).equals(data.get(j))) {
					return true;
				}
			}
		}
		
		// If none of the previous edge cases were 
		// met, then the ballot that is being called
		// is indeed valid and can be used for election.
		// We indicate a valid ballot by returning false;
		return false;
	}
	
	public void printElements() {
		for (int i = 0; i < data.size(); i++) {
			System.out.print(data.get(i)+", ");
		}
	}
	
	
	
	/**
	 * Checks if the ballot contains only one element(the ballot number).
	 * If it does, then the ballot is empty.
	 * 
	 * @param none
	 * @return true if the ballot is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return data.size() == 1;
	}
}
