/**
 * 
 */
package uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures;

import java.util.ArrayList;


/**
 * @author Cormac Brady
 *
 */
public class myStack <E extends Object> {
	
	private ArrayList<E> stack = new ArrayList<E>();
	
	public myStack() {
		
	}

	/**
	 *  feeds input into stack if given
	 */
	public myStack(ArrayList<E> input) {
		
		for(int i = 0; i < input.size() ; i++) 
		{
			stack.add(input.get(i));
		}
		System.out.println(stack.size());
	}
	
	/**
	 * Tests if this stack is empty.
	 * @return true if empty else false
	 */
	public boolean isEmpty() {
		if (stack.size() == 0) {return true;} 
		else return false;
	}
	/*
	 * @return top element of stack without removing it.
	 */
	public E peek() {
		
		if (isEmpty()) {return null;} 
		else {
			return stack.get(stack.size() -1);
		}
	}
	/*
	 * Removes the object at the top of this stack and returns that object as the value of this function.
	 */
	public E pop() {
		
		E topVar = peek();
		if (topVar != null) {
			stack.remove(stack.size() -1);
			return topVar;
		}
		else return null;
	}
	
	// Pushes an item onto the "top" of this stack.
	public void push(E item) {
		stack.add(item);
	}
}
