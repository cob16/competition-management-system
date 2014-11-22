package uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures;

public class myQueue <E extends Object> {

	private Object[] thisQueue;
	private int qHead;
	private int qTail;
	private int qSize;

	public myQueue() {

		thisQueue = new Object[16]; // default 16 elements if none is supplied
		qHead = 0;
		qTail = 0;
		qSize = 0;
	}

	public myQueue(int size) { // force an array size

		thisQueue = new Object[(size)];
		qHead = 0;
		qTail = 0;
		qSize = 0;
	}
	
	public myQueue(Object[] input) {

		addElemints(input);

		qHead = 0;
		qTail = input.length;
		qSize = input.length;
	}

	private void addElemints(Object[] input) {
		thisQueue = new Object[input.length];

		int i = 0;
		for (Object ob : input) {
			thisQueue[i] = ob;
			i++;
		}

	}
	

	private Object[] DoubbleArraySize(Object o[]) { // expects a full array as
													// input, puts the que in
													// order from index 0

		int numElemintsFromHeadToEndOfArray = o.length - qHead;

		Object[] newArr = new Object[(o.length * 2)];
		System.arraycopy(o, qHead, newArr, 0, numElemintsFromHeadToEndOfArray); // Copy from the head to end of array

		if (qTail < qHead) { 
			
			System.arraycopy(o, 0, newArr, numElemintsFromHeadToEndOfArray, o.length); // Copy any remaining elements have wrapped around to the begging of the array
		}
		return newArr;
	}

	public void insert(Object e) {

		//System.out.println("qSize = " + qSize);
		if ((qSize) == thisQueue.length) { // if full

			//System.out.println("Increasing size");

			qTail = qSize - 1;
			qHead = 0;

			thisQueue = DoubbleArraySize(thisQueue);
		}
		if (qTail + 1 >= thisQueue.length) {// are you at the end of the array?
			//System.out.println("end of the array?");
			qTail = 0;
		} else if (qSize > 0) {
			qTail++;
		} else {
			//System.out.println("first element");
		}
		thisQueue[qTail] = e;
		//System.out.println("added " + e);
		qSize++;
	}

	@SuppressWarnings("unchecked")
	public E peek() {
		return (E) thisQueue[qHead];
	}

	@SuppressWarnings("unchecked")
	public E Remove() {

		if (qSize == 0) {
			//System.out.println("q Empty");
			return null;
		}

		Object r = peek();
		thisQueue[qHead] = null;

		if (qHead + 1 >= thisQueue.length) {
			qHead = 0;
		} else if (qSize > 1) {
			qHead++;
		}
		qSize--;

		return (E) r;
	}

	public int size() {
		return qSize;
	}
	
//	public void status() {
//		System.out.println("tail: " + qTail);
//		System.out.println("qSize = "+ qSize);
//		System.out.println("length " + thisQueue.length);
//		System.out.println();
//		//System.out.println(Arrays.toString(thisQueue) //has arrays dependency only for dev
//	}
}
