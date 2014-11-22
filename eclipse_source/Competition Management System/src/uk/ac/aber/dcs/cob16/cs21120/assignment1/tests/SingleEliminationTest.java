/**
 * 
 */
package uk.ac.aber.dcs.cob16.cs21120.assignment1.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.aber.dcs.bpt.cs21120.assignment1.IManager;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.IManagerFactory;
import uk.ac.aber.dcs.bpt.cs21120.assignment1.NoNextMatchException;
import uk.ac.aber.dcs.cob16.cs21120.assignment1.NoCurrentMatchException;

/**
 * @author cormac
 *
 */
public class SingleEliminationTest {
	
	IManager emptyManager;
	IManager fullmanager;

	/**
	 * Test method for {@link uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination#setPlayers(java.util.ArrayList)}.
	 */
	@Before
	public void testSetPlayers() {
 
		emptyManager = IManagerFactory.getManager("uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination");
		fullmanager = IManagerFactory.getManager("uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination");
		
		ArrayList<String> competitors = new ArrayList<String>();
		competitors.add("competitor1");
		competitors.add("competitor2");
		competitors.add("competitor3");
		competitors.add("competitor4");
		
		fullmanager.setPlayers(competitors);
		
		competitors.clear();
		//competitors.add("competitor1");
		
		emptyManager.setPlayers(competitors);
	}

	/**
	 * Test method for {@link uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination#hasNextMatch()}.
	 */
	@Test
	public void testHasNextMatch() {
		assertFalse(emptyManager.hasNextMatch());
		assertTrue(fullmanager.hasNextMatch());
	}

	/**
	 * Test method for {@link uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination#nextMatch()}.
	 */
	@Test(expected=NoNextMatchException.class)
	public void testNextMatchException() {
		
		emptyManager.nextMatch();
		
		assertTrue(fullmanager.hasNextMatch());
	}
	/**
	 * Test method for {@link uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination#nextMatch()}.
	 */
	@Test
	public void testNextMatch() {

		assertEquals("competitor1", fullmanager.nextMatch().getPlayer1());
		assertEquals("competitor1", fullmanager.nextMatch().getPlayer1());
	}

	
	/**
	 * Test method for {@link uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination#setMatchWinner(boolean)}.
	 */
	@Test(expected = NoCurrentMatchException.class)
	public void testSetMatchWinner() {
		emptyManager.setMatchWinner(true);
	}

	/**
	 * Test method for {@link uk.ac.aber.dcs.cob16.cs21120.assignment1.SingleElimination#getPosition(int)}.
	 */
	@Test
	public void testGetPosition() {
		
		assertNull(emptyManager.getPosition(0));
		assertNull(fullmanager.getPosition(0));
		
		while (fullmanager.hasNextMatch()) {
			
			fullmanager.nextMatch();
			fullmanager.setMatchWinner(true);
		}
		
		assertNull(emptyManager.getPosition(-1));
		assertNull(emptyManager.getPosition(1));
		assertNull(fullmanager.getPosition(7));
		
		assertEquals("competitor1", fullmanager.getPosition(0));
	}

}
