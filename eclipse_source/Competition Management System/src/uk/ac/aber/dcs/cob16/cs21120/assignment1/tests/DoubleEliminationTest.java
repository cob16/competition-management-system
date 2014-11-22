package uk.ac.aber.dcs.cob16.cs21120.assignment1.tests; 

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.IManagerFactory;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.Match;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.NoNextMatchException;
import uk.ac.aber.dcs.cob16.cs21120.assignment1.NoCurrentMatchException;

public class DoubleEliminationTest {
	
	IManager fullmanager;
	IManager emptyManager;

	@Before
	public void testSetPlayers() throws Exception {
		
		emptyManager = IManagerFactory.getManager("uk.ac.aber.dcs.cob16.cs21120.assignment1.DoubleElimination");
		
		fullmanager = IManagerFactory.getManager("uk.ac.aber.dcs.cob16.cs21120.assignment1.DoubleElimination");
		
		ArrayList<String> competitors = new ArrayList<String>();
		competitors.add("competitor1");
		competitors.add("competitor2");
		competitors.add("competitor3");
		competitors.add("competitor4");
		
		fullmanager.setPlayers(competitors);
	}

	@Test(expected=NoNextMatchException.class)
	public void testHasNextMatchException() {
		
		emptyManager.hasNextMatch();
	}
	
	@Test
	public void testHasNextMatch() {
		
		assertTrue(fullmanager.hasNextMatch());
	}
	
	
	@Test(expected=NoNextMatchException.class)
	public void testNextMatchException() {
		emptyManager.nextMatch();
	}
	
	@Test
	public void testNextMatch() { //test to make sure it is the same if returned twice before setting results
		assertEquals("competitor1", fullmanager.nextMatch().getPlayer1());
		assertEquals("competitor1", fullmanager.nextMatch().getPlayer1());
		
	}
	
	@Test
	public void testSetMatchWinner() {
		fullmanager.nextMatch();
		fullmanager.setMatchWinner(true);
	}

	@Test
	public void testGetPositionNull() {
		assertNull(fullmanager.getPosition(0));
		
	}
	
	@Test(expected=NoNextMatchException.class)
	public void testGetPositionException() {
		emptyManager.getPosition(0);
	}
		
	
	@Test
	public void testGetPositionEnd() {
		
		while (fullmanager.hasNextMatch()) {
			fullmanager.nextMatch();
			fullmanager.setMatchWinner(true);
		}
		
		assertEquals("competitor2" ,fullmanager.getPosition(0));
		assertEquals("competitor3" ,fullmanager.getPosition(1));
	}
	

}
