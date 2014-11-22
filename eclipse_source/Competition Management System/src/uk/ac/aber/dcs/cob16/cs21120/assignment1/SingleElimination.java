/**
 * 
 */
package uk.ac.aber.dcs.cob16.cs21120.assignment1;

import java.util.ArrayList;

import org.omg.CORBA.PUBLIC_MEMBER;

import uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.Match;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.NoNextMatchException;
import uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures.myQueue;

/**
 * @author cormac
 *
 */
public class SingleElimination implements IManager {
	
	private myQueue<String> SingleEliminationPlayers;
	private myQueue<Match>  SingleEliminationMatches;
	private Match currentMatch;
	private Boolean results = true;

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#getPosition(int)
	 */
	@Override
	public String getPosition(int n) {
		if (!hasNextMatch() && n == 0 && !(currentMatch == null)) {
			return SingleEliminationPlayers.peek();
		}else {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#hasNextMatch()
	 */
	@Override
	public boolean hasNextMatch() {
		
		if (SingleEliminationMatches.size() > 0 || SingleEliminationPlayers.size() > 1) {
			return true;
		}
		else return false;
	}

	/* 
	 * makes Matches from player que 
	 */
	private void makeNewRound() {
		
		while (SingleEliminationPlayers.size() > 1) {
			
			Match match = new Match(SingleEliminationPlayers.Remove(), SingleEliminationPlayers.Remove());
			SingleEliminationMatches.insert(match);
		}
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#nextMatch()
	 */
	@Override
	public Match nextMatch() throws NoNextMatchException {
		
		if (hasNextMatch()) {
			if (results) {
				if (SingleEliminationMatches.size() == 0 ) {
					makeNewRound();	
				}
				currentMatch = (Match) SingleEliminationMatches.Remove();
				results = false;
				return currentMatch;
			} else return currentMatch;
		} 
		else throw new NoNextMatchException("There was no more matches to play, use bool method hasNextMatch() to avoid This");
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#setMatchWinner(boolean)
	 */
	@Override
	public void setMatchWinner(boolean player1) {
		if (currentMatch != null) {
			results = true;
			if (player1)
				 SingleEliminationPlayers.insert(currentMatch.getPlayer1());
			else SingleEliminationPlayers.insert(currentMatch.getPlayer2());
			
		} else throw new NoCurrentMatchException("There must be a current match before you can set the winner of one. Avoid this by calling nextMatch() first");
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#setPlayers(java.util.ArrayList)
	 */
	@Override
	public void setPlayers(ArrayList<String> players) {
				
		int size = players.size(); 
		
		SingleEliminationPlayers = new myQueue<String>(players.toArray()); //add players to Queue
				 
		if ((size % 2) != 0) { //if there is an odd number 
			size = (size -1); 
		} 
		
		SingleEliminationMatches = new myQueue<Match>(size / 2);
		
		makeNewRound();
	}
}
