package main;

import java.io.FileNotFoundException;

import candidates.Ballot;
import candidates.Candidate;
import collections.list.List;
import collections.set.DynamicSet;
import collections.set.Set;
import file_handlers.CSVUtils;

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

	public static void main(String[] args) throws FileNotFoundException {
    	 
    	/**
    	 * Update string value below to match the
    	 * name of the csv file being used.
    	 */
    	 		Election election = new Election();
    	 		election.run();
		
//		List<Candidate> candiList = CSVUtils.getCandidates("candidates.csv");
//		List<Ballot> BallotList = CSVUtils.getBallots("ballots.csv");
//		
//		Set<String> setOfStrings = new DynamicSet(10);
//		setOfStrings.add("Diego1");
//		setOfStrings.add("Diego12");
//		setOfStrings.add("Diego13");
//		Object[] arrayOfStings = (Object[])setOfStrings.toArray();
//		
//		for (int i = 0; i < arrayOfStings.length; i++) {
//			System.out.println((String)arrayOfStings[i]);
//		}
//		System.out.println(arrayOfStings.length);
  		//List<Ballot> ballot2 = CSVUtils.getBallots("ballots2.csv");
  		//List<Candidate> candidates = CSVUtils.getCandidates("candidates.csv");
  		//Ballot leballot = new Ballot("6969,3:1,2:2,1:3");
  		//System.out.println(leballot.getCandidatebyRank(1));
  		//System.out.println(leballot.getRankbyCandidate(3));
  		
		
		
		
  		//leballot.eliminate(3);
  		//System.out.println();
			
//  		List<Candidate> candidates = CSVUtils.getCandidates("candidates.csv");
//  		List<Ballot> ballot2 = CSVUtils.getBallots("ballots.csv");
//  		
//  		for (Ballot ballot : ballot2) {
//  			for (Integer element : ballot.data) {
//  				System.out.print(element + ", ");
//  			}
//  			  System.out.println ("\n invalid due to rank: "+ballot.isInvalidDueToRank+ " invalid due to repeated votes: " + ballot.isInvalid(candidates) +"\n");
//  		}
//  		
//  		System.out.println();
		

    }
}
