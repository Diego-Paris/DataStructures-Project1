package candidates;

import collections.list.*;

public class BallotOld {

	public List<String> data = new LinkedList<String>();

	public BallotOld(String info) {

		String[] dataToConvert = info.split(",");
		for (String string : dataToConvert) {
			data.add(string);
		}
	}

	public int getBallotNumber() {
		return Integer.parseInt(data.get(0));
	}

	public int getRankbyCandidate(int candidateID) {
		int index = 0;
		int pos;
		int toCompare;

		// Iterates through the String elements in the list
		for (String element : data) {
			// if it is the first iteration, then it is the ballot
			// number. We skip it
			if(index==0) {
				index++;
				continue;
			}
			// find the index of the delimiter
			pos = element.indexOf(":");

			// parses the string of [a]:b by finding [a] and turning it into a integer
			toCompare = Integer.parseInt(element.substring(0, pos));

			// compares if the current ID is the value we're given
			if(toCompare == candidateID) {
				// if it is, then return the rank
				return Integer.parseInt(element.substring(pos+1));
			}
		}
		return -1;
	}

	public int getCandidatebyRank(int rank) {
		int index = 0;
		int pos;
		int toCompare;

		// Iterates through the String elements in the list
		for (String element : data) {
			// if it is the first iteration, then it is the ballot
			// number. We skip it
			if(index==0) {
				index++;
				continue;
			}
			// find the index of the delimiter
			pos = element.indexOf(":");

			// parses the string of a:[b] by finding [b] and turning it into a integer
			toCompare = Integer.parseInt(element.substring(pos+1));

			// compares if the rank is the value we're given
			if(toCompare == rank) {
				// if it is, then return the candidateID
				return Integer.parseInt(element.substring(0,pos));
			}
		}
		return -1;
	}
	
	public boolean eliminate(int candidateID) {
		int index = 0;
		int pos;
		int toCompare;

		// Iterates through the String elements in the list
		for (String element : data) {
			// if it is the first iteration, then it is the ballot
			// number. We skip it
			if(index==0) {
				index++;
				continue;
			}
			// find the index of the delimiter
			pos = element.indexOf(":");
			
			// parses the string of [a]:b by finding [a] and turning it into a integer
			toCompare = Integer.parseInt(element.substring(0, pos));

			// compares if the current ID is the value we're given
			if(toCompare == candidateID) {
				// if it is, then return the rank
				data.remove(element);
				return true;
			}
		}
		return false;
	}

	public void printElements() {
		int index = 0;
		for (String element : data) {
			if(index==0) {
				System.out.println("Ballot number: " + element);
				index++;
				continue;
			}
			System.out.println("Ballot element["+(index++)+"]: " + element);
		}
	}
	
	public int total() {
		return data.size();
	}
	
	public boolean isEmpty() {
		return data.size() == 1;
	}
	
	
}
