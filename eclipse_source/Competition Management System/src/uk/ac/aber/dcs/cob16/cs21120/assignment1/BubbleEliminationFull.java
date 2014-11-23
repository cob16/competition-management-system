/**
 * THIS FILE WILL NOT RUN AND IS ONLY TO SHOW WORK DONE
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
public class BubbleEliminationFull implements IManager {
		
	private Player Tournament[];

	private myStack<String> roundOrder = new myStack<String>(); 
	
	private String currentPlayer;
	private Boolean currentPlayerLoss = true;
	
	private Match currentMatch;
	
	private int round;
	private Boolean roundOver = true;
	private String winners[];
	
	boolean roundsOver = false;
	
	
	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#setPlayers(java.util.ArrayList)
	 */
	@Override
	public void setPlayers(ArrayList<String> players) {
		round = 0;
		winners = new String[players.size()];		
		Tournament = new Player[players.size() + 1];
		Tournament[0] = null;  //we are starting at 1 so we do not have to -1 loads later
		
		for (int current = 1; current -1 < players.size(); current++) {
			
			Player player = new Player(players.get(current -1 ));
			Tournament[current] = player;
			setMyChirdren(current, players);
			roundOrder.push(player.getName());
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
		
		isPlayers();
		if (roundsOver) { //if the last winner is found
			return false;
		}
		return true;
	}
	private void isPlayers() throws NoNextMatchException {
		if (Tournament == null) {
			throw new NoNextMatchException("setPlayers() method was never used");
		}
		if (!(Tournament.length > 1)) {
			throw new NoNextMatchException("setPlayers() method was never givin any players");
		}
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#nextMatch()
	 */
	@Override
	public Match nextMatch() throws NoNextMatchException {

		if(hasNextMatch()) {
			return determineNextMatch();
		}else throw new NoNextMatchException("There was no more matches to play, use bool method hasNextMatch() to avoid This"); 

	}

	private Match determineNextMatch() {
		
		if (winners[0] == null) {
			if (roundOrder.isEmpty()) {
				currentMatch = new Match(null, null);
				currentMatch = BubbleDownMatchMaker();
			} else {
				System.out.println("there is more to be done for the first round");
				currentMatch = BubbleUpMatchMaker();
			}
		} else {
			currentMatch = BubbleDownMatchMaker();
		}
		
		return currentMatch;
	}
	/*
	 * returns the parent of the child using supper complex math :P
	 */
	private String findMyParent(String player){
		int parent = findPlayerIndex(player);
		if (parent > 1) {
			parent = parent / 2;
			return Tournament[parent].getName();			
		}
		return null;
	}

	/*
	 * looks through the array for the location on a player
	 * @param type Player
	 * @returns -1 if not found
	 */
	private int findPlayerIndex(String player){
		if (player != null) {
			int i;
			for (i = 1;  i < Tournament.length - round; i++) {
				System.out.println(Tournament[i].getName());
				if (Tournament[i].getName().equals(player)){
					return i;
				}
			}
		}
		return 0;
	}
	
	/*
	 * builds a list of matches for the current player
	 */
	private Match BubbleUpMatchMaker(){

		if (currentPlayerLoss || findMyParent(currentPlayer) == null) {
			currentPlayer = roundOrder.pop();
			currentPlayerLoss = false;
		}
		String oponnent = findMyParent(currentPlayer);
		System.out.println("oponnent" + oponnent); 
		return new Match(currentPlayer, oponnent);
	}
	
	private void nextRound(){
		
		if ((Tournament.length - 1) - round < 3) {
			roundsOver = true;
		}
		
		winners[round] = Tournament[1].getName();
		System.out.println(round + "th winner found: " + winners[round]);
		
		int lastElimint = (Tournament.length - 1) - round;
		Tournament[1].setName(Tournament[lastElimint].getName());
		Tournament[lastElimint] = null;
		if (lastElimint % 2 == 0) {
			 Tournament[lastElimint / 2].setLeftChild(null);
		} else 	Tournament[lastElimint / 2].setRightChild(null);
		
		currentPlayer = Tournament[1].getName();
		round++;
		roundOver = false;
	}
	
	private Match BubbleDownMatchMaker(){
		if (roundOver) {
			nextRound();
		}
		
		int currentLocation = findPlayerIndex(currentPlayer);
			
		int leftChild  = findPlayerIndex(Tournament[currentLocation].getLeftChild());
		int rightChild = findPlayerIndex(Tournament[currentLocation].getRightChild());
		
		if (leftChild == 0 && rightChild == 0) {//bottem of tree
			roundOver = true;
		}
			
		if (currentMatch.getPlayer1() == null && currentMatch.getPlayer2() == null) {// calls on round after first
			return new Match(currentPlayer, Tournament[leftChild].getName());
		}	
		
		if (currentMatch.getPlayer1().equals(currentPlayer) &&
			currentMatch.getPlayer2().equals(Tournament[leftChild].getName())){ //PARENT V LEFT
			if (rightChild == 0) {
				roundOver = true; //STOP condition
			}
			if (currentPlayerLoss) {
				return new Match(Tournament[leftChild].getName(), Tournament[rightChild].getName());
			} else {
				return new Match(currentPlayer, Tournament[rightChild].getName());
			}
		} else if (currentMatch.getPlayer1().equals(currentPlayer) &&
				 currentMatch.getPlayer2().equals(Tournament[rightChild].getName())){ //PARENT V RIGHT
			
			if (currentPlayerLoss) {
				Swap(currentPlayer, currentMatch.getPlayer2());
				return new Match(currentPlayer, Tournament[leftChild].getName());
			}
			else {
				roundOver = true; //STOP condition
			}
		} else if (currentMatch.getPlayer1().equals(Tournament[leftChild].getName()) &&
				 currentMatch.getPlayer2().equals(Tournament[rightChild].getName())){
			
			if (currentPlayerLoss) {
				Swap(currentPlayer, currentMatch.getPlayer2());
				return new Match(currentPlayer, Tournament[leftChild].getName());
			} else {
				Swap(currentPlayer, currentMatch.getPlayer1());
				return new Match(currentPlayer, Tournament[leftChild].getName());
			}
		} 
		return new Match(currentPlayer, Tournament[leftChild].getName());
	}
	
	private void Swap(String parent, String child){
		Tournament[findPlayerIndex(parent)].setName(child);	
		Tournament[findPlayerIndex(child)].setName(parent);
		
		if (findPlayerIndex(parent) > 1) {
			if(findPlayerIndex(child) > 1 && findPlayerIndex(child) % 2 == 0) {
				Tournament[findPlayerIndex(findMyParent(child))].setLeftChild(child);
			}else Tournament[findPlayerIndex(findMyParent(child))].setRightChild(child);
		}
		if (findPlayerIndex(parent) > 1) {
			if(findPlayerIndex(parent) % 2 == 0) {
				Tournament[findPlayerIndex(findMyParent(parent))].setLeftChild(parent);
			}else Tournament[findPlayerIndex(findMyParent(parent))].setRightChild(parent);
		}
	}
	
	

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#setMatchWinner(boolean)
	 */
	@Override
	public void setMatchWinner(boolean player1) {
		if (winners[0] == null) {
			if (player1) {
				Swap(currentPlayer, currentMatch.getPlayer2());
				currentPlayerLoss = false;
			} else {
				currentPlayerLoss = true;
			}
		} else { 
			if (player1) {
				currentPlayerLoss = false;
			} else {
				currentPlayerLoss = true;
			}
		}
	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager#getPosition(int)
	 */
	@Override
	public String getPosition(int n) {
		if (n < winners.length - 1 &&  n >= 0 ) {
			return winners[n];
		} else return null;
	}

}
