package com.machine.learning.decisiontrees.conditions;

import com.machine.learning.decisiontrees.Node;

public abstract class Condition {
	
	Node node;
	public Condition(Node nextNode) {
		this.node = nextNode;
	}
	
	public abstract boolean test(String value);
	
	public Node getNextNode() {
		return node;
	}
	
	public void setNextNode(Node node) {
		this.node = node;
	}

}
