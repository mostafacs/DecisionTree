package test;

import java.io.File;

import com.machine.learning.decisiontrees.DecisionTree;
/**
 * 
 * @author mostafa
 * An Example of the using of decision tree
 */
public class Test {


	public static void main(String[] args) {
		
		DecisionTree tree = new DecisionTree();
		
		// Train your Decision Tree
		tree.train(new File("resources/vertebrate.psv"));
		
		// Print RootNode display xml structure from your decision tree learning
		System.out.println(tree.getRootNode());

		// Classify your new data
		System.out.println(tree
				.classify("gila monster|cold-blooded|scales|no|no|no|yes|yes"));

		
	}
	
	
}
