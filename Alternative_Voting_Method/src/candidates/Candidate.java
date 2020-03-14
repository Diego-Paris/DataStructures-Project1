package candidates;

import collections.set.DynamicSet;
import collections.set.Set;;

/**
 * Class handles each of the candidates information
 * such as their name and list of votes;
 * 
 * @author Diego Paris
 */
public class Candidate {
	private String candidateName;
	private String candidateID;
	public Set<Ballot> votes;
	
	
	public Candidate(String line) {
		String[] info = line.split(",");
		this.candidateName = info[0];
		this.candidateID = info[1];
		this.votes = new DynamicSet<Ballot>(10);
	}
	public Candidate(Candidate candi) {
		this.candidateName = candi.candidateName;
		this.candidateID = candi.candidateID;
		this.votes = new DynamicSet<Ballot>(10);
		
		for(Ballot ballot: candi.votes) {
			this.votes.add(ballot);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.getID() == ((Candidate)obj).getID())
			return true;
		return false;
	}
	
	public boolean addBallot(Ballot obj) {
		return votes.add(obj);
	}
	
	public String getName() {
		return this.candidateName;
	}
	
	public int getID() {
		return Integer.parseInt(this.candidateID);
	}
	
}
