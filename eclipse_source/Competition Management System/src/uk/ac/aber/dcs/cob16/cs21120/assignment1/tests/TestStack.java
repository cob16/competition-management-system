package uk.ac.aber.dcs.cob16.cs21120.assignment1.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures.myStack;

public class TestStack {
	
	private myStack<String> testStack;
   	
   	@Before
   	public void test_constructor() {
   	 	
 		ArrayList<String> input = new ArrayList<String>();
   		input.add("one");
   		input.add("two");
   		input.add("three");
   		input.add("four");

   		testStack = new myStack<String>(input);
   	}
   	
   	@Test 
   	public void test_peek() {
   		assertNotNull(testStack.peek());
   		assertEquals("four", testStack.peek()) ;
   	}
	
	@Test
	public void test_push() {
		String five = "five";
		testStack.push(five);
		assertTrue(testStack.peek() == "five") ;
	}
	
	@Test
	public void test_pop() {
		String returnValue = testStack.pop();
		assertNotNull(returnValue);
		
		assertEquals("four", returnValue ) ;
		
		assertEquals("three", testStack.peek()) ;
	}
	
	@Test
	public void test_popPastEmpty() { //empty stack must return null
		
		assertEquals("four", testStack.pop()) ;
		assertEquals("three", testStack.pop()) ;
		assertEquals("two", testStack.pop()) ;
		assertEquals("one", testStack.pop()) ;
		
		assertNull(testStack.pop()) ;
	}

}
