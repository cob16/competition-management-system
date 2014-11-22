package uk.ac.aber.dcs.cob16.cs21120.assignment1.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures.myQueue;


public class TestQueue {
	
	private myQueue testQueue;
	
	@Before
   	public void test_constructor() {
   	 		
 		String[] input = {"one", "two", "three", "four"};

   		testQueue = new myQueue(input);
   	}
	
	@Test
	public void test_peek() {
		assertTrue(testQueue.peek() == "one");
		
	}
	
	@Test
	public void test_removeAll() {
	
		assertEquals("one", testQueue.Remove());
		assertEquals("two", testQueue.Remove());
		assertEquals("three", testQueue.Remove());
		assertEquals("four", testQueue.Remove());
		
		assertNull(testQueue.Remove()); //nothing in q, should return null
	}
	
	@Test
	public void test_insert() {
		testQueue.insert("inserted");
		assertEquals("one", testQueue.peek());
		
		assertEquals("one", testQueue.Remove());
		assertEquals("two", testQueue.Remove());
		assertEquals("three", testQueue.Remove());
		assertEquals("four", testQueue.Remove());
		
		testQueue.insert("insertedsecond");
		assertEquals("inserted", testQueue.Remove());
		
		testQueue.insert("insertedsecond");
		assertEquals("insertedsecond", testQueue.Remove());
	}
	
}
