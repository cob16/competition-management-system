package uk.ac.aber.dcs.cob16.cs21120.assignment1;

import java.util.ArrayList;

import uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.Match;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.NoNextMatchException;
import uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures.myQueue;

public class DoubleElimination implements IManager {

	private myQueue<String> winnersBracketPlayers;
	private myQueue<Match>  winnersBracketMatches;
	
	private myQueue<String> losersBracketPlayers;
	private myQueue<Match>  losersBracketMatches;
	
	private Match currentMatch;
	private myQueue<Match> currentMatchBracket;
	private Boolean results = true;
		
	private Match determineNextMatch() {
		
		
		makeNewMatches(winnersBracketPlayers, winnersBracketMatches);
		makeNewMatches(losersBracketPlayers, losersBracketMatches);
		
		System.out.println(winnersBracketMatches.size());
		System.out.println(losersBracketMatches.size());
						
		if (losersBracketPlayers.size() == 1 && losersBracketMatches.size() == 0 && winnersBracketPlayers.size() == 1 && winnersBracketMatches.size() == 0) {	
			System.out.println("last match");
			currentMatchBracket = null;
			return new Match(winnersBracketPlayers.Remove(), losersBracketPlayers.Remove());
		} 
		else if (winnersBracketMatches.size() > losersBracketMatches.size()) {
			System.out.println("winner pool match");
			currentMatchBracket = winnersBracketMatches;
			return winnersBracketMatches.Remove();
		}
		else  {
			System.out.println("loosers pool match");
			currentMatchBracket = losersBracketMatches;
			return losersBracketMatches.Remove(); //will return this if equal as per requirment
		}
	}
	
	@Override
	public String getPosition(int n) {
		isPlayers();		
		if (!hasNextMatch()){ 
			switch(n) {
				case 0 : 	return currentMatch.getPlayer1();
							//breaks not required due to returns
				case 1 : 	return currentMatch.getPlayer2();
								//breaks not required due to returns
				default: 	return null;
			}
		} else return null;
	}

	@Override
	public boolean hasNextMatch() {
		isPlayers();
		return 	((losersBracketPlayers.size() > 0 || winnersBracketPlayers.size() > 0) || (losersBracketMatches.size() > 0 || winnersBracketMatches.size() > 0));
	}
	
	private void isPlayers() throws NoNextMatchException {
		if (winnersBracketMatches == null && winnersBracketPlayers == null && currentMatch == null) {
			throw new NoNextMatchException("use setPlayers() method to avoid this");
		}
	}

	private void makeNewMatches(myQueue<String> players, myQueue<Match> matches) {
		
		while (players.size() > 1) {
			
			Match match = new Match(players.Remove(), players.Remove());
			matches.insert(match);
		}
	}
	
	private void matchOutcomeLosers(String winer) {
		losersBracketPlayers.insert(winer);
	}

	private void matchOutcomeWinners(String winer, String loser) {
		winnersBracketPlayers.insert(winer);
		losersBracketPlayers.insert(loser);
	}

	@Override
	public Match nextMatch() throws NoNextMatchException {
		if (results) {
			if (hasNextMatch()) {
				currentMatch = determineNextMatch();
				System.out.println(currentMatch.getPlayer1());
				results = false;
				return currentMatch;
			}
			else throw new NoNextMatchException("There was no more matches to play, use bool method hasNextMatch() to avoid This");
		
		}else return currentMatch; // keeps returning the same match until the results are given
	} 
	
	@Override
	public void setMatchWinner(boolean player1) {
		isPlayers();
		if (currentMatchBracket == winnersBracketMatches) {
			if (player1)
				 matchOutcomeWinners(currentMatch.getPlayer1(),currentMatch.getPlayer2());
			else matchOutcomeWinners(currentMatch.getPlayer2(),currentMatch.getPlayer1());
		}
		else if (currentMatchBracket == losersBracketMatches) {
			if (player1)
				matchOutcomeLosers(currentMatch.getPlayer1());
			else matchOutcomeLosers(currentMatch.getPlayer2());
		}
		else if (currentMatchBracket == null && currentMatch != null) {//Special case for last match played
			if (player1 == false) {
				String playerSwap1 = currentMatch.getPlayer1();
				String playerSwap2 = currentMatch.getPlayer2();
				currentMatch = new Match(playerSwap2, playerSwap1);
			}
		}
		else throw new NoCurrentMatchException("There must be a current match before you can set the winner of one. Avoid this by calling nextMatch() first");
		results = true;
	}
	
	@Override
	public void setPlayers(ArrayList<String> players) {

		winnersBracketPlayers = new myQueue<String>(players.toArray());
		winnersBracketMatches = new myQueue<Match>();
		
		losersBracketPlayers = new myQueue<String>();
		losersBracketMatches = new myQueue<Match>();
		
		makeNewMatches(winnersBracketPlayers, winnersBracketMatches);
	}
}
