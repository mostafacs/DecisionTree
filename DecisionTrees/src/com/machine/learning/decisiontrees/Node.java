package com.machine.learning.decisiontrees;

import java.util.LinkedList;

import com.machine.learning.decisiontrees.conditions.Condition;

/**
 * 
 * @author mostafa A Class for Mapping each Step in Decision Tree
 */
public class Node {

	/**
	 * Forks that lead to next Node If Condition Match we must go to next node
	 * until reach to the leaf
	 */
	private LinkedList<Condition> forks;
	/**
	 * The name of the Node if it's Leaf it will be class value if not a leaf it
	 * will be attribute name
	 */
	private String label;
	/**
	 * The index ( Attribute Index )
	 */
	private int index;
	/**
	 * Boolean Value to determine if current node is a leaf or intermediate node
	 */
	private boolean leaf;

	public Node() {
		forks = new LinkedList<Condition>();
		leaf = false;
	}

	public void addCondition(Condition condition) {
		forks.add(condition);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public LinkedList<Condition> getForks() {
		return forks;
	}

	public void setForks(LinkedList<Condition> forks) {
		this.forks = forks;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isLeaf() {
		return leaf;
	}

	@Override
	public String toString() {
		String out = "\n <Node Label = '" + label + "'  isLeaf = '" + isLeaf()
				+ "' index=" + index + " >";
		out += "\n <Conditions>";

		for (Condition condition : forks) {
			out += "\n " + condition.toString();

		}
		out += "\n </Conditions>";
		out += "\n </Node> ";
		return out;
	}

}
