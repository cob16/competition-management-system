package uk.ac.aber.dcs.cob16.cs21120.assignment1.dataStructures;

public class Player {
	
	public Player(String name) {
		Name = name;
	}
	
	public Player(String name, String leftChild, String rightChild) {
		Name = name;
	}
	
	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public String getLeftChild() {
		return LeftChild;
	}

	public void setLeftChild(String leftChild) {
		LeftChild = leftChild;
	}

	public String getRightChild() {
		return rightChild;
	}

	public void setRightChild(String rightChild) {
		this.rightChild = rightChild;
	}

	private String Name, LeftChild, rightChild;
}
