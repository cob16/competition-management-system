/**
 * 
 */
package uk.ac.aber.dcs.cob16.cs21120.assignment1;

import java.util.ArrayList;

import uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.Match;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.NoNextMatchException;
import uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures.Player;
import uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures.myStack;

/**
 * @author cormac
 *
 */
public class BubbleElimination implements IManager {
		
	private Player Tournament[];
	private myStack<Player> roundOrder = new myStack<Player>(); 
	
	private Player currentPlayer;
	private Boolean currentPlayerLoss = true;
	
	private Match currentMatch;
	
	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#setPlayers(java.util.ArrayList)
	 */
	@Override
	public void setPlayers(ArrayList<String> players) {
		Tournament = new Player[players.size() + 1];
		Tournament[0] = null;  //we are starting at 1 so we do not have to -1 loads later
		
		for (int current = 1; current -1 < players.size(); current++) {
			
			Player player = new Player(players.get(current -1 ));
			Tournament[current] = player;
			setMyChirdren(current, players);
			roundOrder.push(player);
		}
	}
	
	private void setMyChirdren(int current, ArrayList<String> list) {
		int lastplayer = list.size() -1;
		int child = current * 2;
		if (child -1 < lastplayer) //if at the Bottom of tree  
			 Tournament[current].setLeftChild(list.get(child -1));
		else Tournament[current].setLeftChild(null);
		if (child < lastplayer) 
			 Tournament[current].setRightChild(list.get(child));
		else Tournament[current].setRightChild(null);
		
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#hasNextMatch()
	 */
	@Override
	public boolean hasNextMatch() {
		return !(roundOrder.isEmpty()); //only will work for 1st place
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#nextMatch()
	 */
	@Override
	public Match nextMatch() throws NoNextMatchException {

		if (roundOrder.isEmpty()) //only work for 1st
			throw new NoNextMatchException("---");
		currentMatch = BubbleUpMatchMaker();
		return currentMatch;
	}
	/*
	 * returns the parent of the child using supper complex math :P
	 */
	private Player findMyParent(Player player){
		int parent = findPlayerIndex(player.getName());
		if (parent == 1) {
			return null;
		}
		parent = parent / 2;
		return Tournament[parent];
	}

	/*
	 * looks through the array for the location on a player
	 * @param type Player
	 * @returns -1 if not found
	 */
	private int findPlayerIndex(String player){
		System.out.println("looking for player " + player);
		int i;
		for (i = 1;  i < Tournament.length; i++) {
			System.out.println(Tournament[i].getName());
			if (Tournament[i].getName() == player){
				System.out.println("found at " + i );
				return i;
			}
		}
		System.exit(1);
		return -1;
	}
	
	/*
	 * builds a list of matches for the current player
	 */
	private Match BubbleUpMatchMaker(){

		if (currentPlayerLoss) {
			currentPlayer = roundOrder.pop();
			currentPlayerLoss = false;
		} else {
			currentPlayer = roundOrder.peek();
		}
		Player oponnent = findMyParent(currentPlayer);
		System.out.println("oponnent" + oponnent.getName()); 
		return new Match(currentPlayer.getName(), oponnent.getName());
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#setMatchWinner(boolean)
	 */
	@Override
	public void setMatchWinner(boolean player1) {
		if (player1) {
			int Player1Index = findPlayerIndex(currentPlayer.getName());
			int Player2Index = findPlayerIndex(currentMatch.getPlayer2());
			Tournament[Player2Index].setName(currentPlayer.getName());	
			Tournament[Player1Index].setName(currentMatch.getPlayer2());
			currentPlayerLoss = false;
		} else {
			currentPlayerLoss = true;
		}
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#getPosition(int)
	 */
	@Override
	public String getPosition(int n) {
		// TODO Auto-generated method stub
		return null;
	}

}
