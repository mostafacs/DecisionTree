package com.machine.learning.decisiontrees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.machine.learning.decisiontrees.conditions.Condition;
import com.machine.learning.decisiontrees.conditions.EqualCondition;

/**
 * 
 * @author mostafa The main class to apply train and classification test It's
 *         Contains the main Logic for decision tree learning and test
 */
public class DecisionTree {

	private int classesCount;
	/**
	 * Used to determine if current attribute already used or not
	 */
	private BitSet columns;
	/**
	 * Classes set of the training data
	 */
	private Set<String> classes;
	private int rowsCount;
	private int columnsCount;
	private Node rootNode;
	private File psvFile;

	public DecisionTree() {

		this.columns = new BitSet(columnsCount);
		classes = new HashSet<String>();
		rootNode = new Node();
	}

	/**
	 * @param row
	 * @return
	 * Classify new Patterns
	 */
	public String classify(String row) {

		String[] attrs = row.split("\\|");

		return classify(attrs, rootNode);
	}

	/**
	 * 
	 * @param attrs
	 * @param node
	 * @return
	 * Private Method to Find a Classification of new pattern
	 */
	private String classify(String[] attrs, Node node) {

		if (node.isLeaf()) {
			return node.getLabel();
		}
		String currentValue = attrs[node.getIndex()];
		for (Condition condition : node.getForks()) {
			if (condition.test(currentValue)) {
				return classify(attrs, condition.getNextNode());
			}
		}

		return "Cann't Find Class -- Please Learn Tree with more examples";

	}

	/**
	 * Train the tree from the psv File
	 * 
	 * @param psvFile
	 */
	public void train(File psvFile) {
		try {
			this.psvFile = psvFile;
			findClasses(psvFile);
			BitSet rows = new BitSet(rowsCount);
			for (int i = 0; i < rowsCount; i++) {
				rows.set(i);
			}
			buildTree(rootNode, rows);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param currentNode
	 * @param rows
	 * @throws IOException
	 * 
	 * Building the tree based on decision tree algorithm
	 * 1-Finding Best Attribute to split data 
	 * 2-if entropy value equal to zero mark this node as leaf
	 * 3-repeat until all data is separated or all attributes is processed
	 */
	private void buildTree(Node currentNode, BitSet rows) throws IOException {

		AttributeInfo bestAttribute = findBestSplit(psvFile, rows);
		if (bestAttribute == null) {
			currentNode.setLeaf(true);
		}
		currentNode.setLabel(bestAttribute.getName());
		currentNode.setIndex(bestAttribute.getIndex());
		/**
		 * Mark This Attribute as processed so not use it again
		 */
		columns.set(bestAttribute.getIndex());

		Map<String, ValueInfo> infoValues = bestAttribute.getValues();

		Iterator<Entry<String, ValueInfo>> valuesItr = infoValues.entrySet()
				.iterator();
		while (valuesItr.hasNext()) {
			Entry<String, ValueInfo> entry = (Entry<String, ValueInfo>) valuesItr
					.next();

			ValueInfo currentValue = entry.getValue();
			String valueName = entry.getKey();
			Map<String, Integer> classes = currentValue.getAttributeClasses();

			if (currentValue.getEntropy() == 0.0) {
				String classLabel = findClassLabel(classes);
				Node leafNode = new Node();
				leafNode.setLabel(classLabel);
				leafNode.setLeaf(true);
				EqualCondition equalCondition = new EqualCondition(leafNode,
						valueName);
				currentNode.addCondition(equalCondition);

			} else {
				Node newNode = new Node();
				EqualCondition equalCondition = new EqualCondition(newNode,
						valueName);
				currentNode.addCondition(equalCondition);

				buildTree(newNode, currentValue.getRows());

			}

		}

	}

	/**
	 * 
	 * @param classes
	 * @return
	 * Assign the Class for current value
	 * By Get the class with maximum rows 
	 */
	private String findClassLabel(Map<String, Integer> classes) {

		int max = -1;
		String classLabel = "";
		Iterator<Entry<String, Integer>> classIterator = classes.entrySet()
				.iterator();
		while (classIterator.hasNext()) {
			Entry<String, Integer> classEntry = (Entry<String, Integer>) classIterator
					.next();
			if (classEntry.getValue() > max) {
				max = classEntry.getValue();
				classLabel = classEntry.getKey();
			}
		}

		return classLabel;

	}

	/**
	 * 
	 * @param csvFile
	 * @throws IOException
	 * Find Classes by process last column on the data
	 */
	private void findClasses(File csvFile) throws IOException {

		FileReader fileReader = new FileReader(csvFile);
		BufferedReader breader = new BufferedReader(fileReader);
		int counter = 0;
		int classCounter = 0;
		String line;
		while ((line = breader.readLine()) != null) {
			if (counter != 0) {
				String[] cols = line.split("\\|");
				columnsCount = cols.length;
				if (cols.length > 2) {
					String targetValue = cols[cols.length - 1];
					if (!classes.contains(targetValue)) {
						classes.add(cols[cols.length - 1]);
						classCounter++;
					}
				}
			}
			counter++;
		}
		rowsCount = counter;
		classesCount = classCounter;
		breader.close();
		fileReader.close();
	}

	/**
	 * 
	 * @param csvFile
	 * @param rows
	 * @return
	 * @throws IOException
	 * 
	 * Find Best Attribute to split
	 */
	private AttributeInfo findBestSplit(File csvFile, BitSet rows)
			throws IOException {

		AttributeInfo bestAttribute = null;
		double bestEntropy = Double.MAX_VALUE;
		for (int h = 1; h < columnsCount - 1; h++) {

			if (columns.get(h))
				continue;
			AttributeInfo data = singleAttributeInfo(csvFile, h, rows);
			Map<String, ValueInfo> attributes = data.getValues();

			double entropy = calculateSubTreeEntropy(attributes,
					data.getRowCount());
			if (entropy < bestEntropy) {
				bestAttribute = data;
				bestEntropy = entropy;
				bestAttribute.setIndex(h);
			}

		}
		columns.set(bestAttribute.getIndex());
		return bestAttribute;

	}

	/**
	 * 
	 * @param file
	 * @param index
	 * @param rows
	 * @return
	 * @throws IOException
	 * Get Single Attribute Information 
	 */
	private AttributeInfo singleAttributeInfo(File file, int index, BitSet rows)
			throws IOException {

		AttributeInfo attributeInfo = new AttributeInfo();
		attributeInfo.setIndex(index);
		Map<String, ValueInfo> attributes = new HashMap<String, ValueInfo>();
		FileReader fileReader = new FileReader(file);
		BufferedReader breader = new BufferedReader(fileReader);
		String line;
		int counter = 0;
		while ((line = breader.readLine()) != null) {

			if (counter == 0) {
				String[] cols = line.split("\\|");
				for (int i = 0; i < cols.length; i++) {
					if (i == index) {
						attributeInfo.setName(cols[i]);
					}
				}
			} else {

				if (!rows.get(counter))
					continue;

				String[] cols = line.split("\\|");

				for (int i = 0; i < cols.length; i++) {
					if (i == index) {

						String className = cols[cols.length - 1];
						String value = cols[i];
						if (!attributes.containsKey(value)) {

							attributes.put(value, new ValueInfo(classes,
									new BitSet(rowsCount)));
						}

						ValueInfo info = attributes.get(value);
						info.increaseClass(className);
						info.setRowAt(counter);
						info.increaseRowCount();

					}
				}

			}
			counter++;
		}
		breader.close();
		fileReader.close();
		attributeInfo.setRowCount(counter);
		attributeInfo.setValues(attributes);
		return attributeInfo;
	}

	/**
	 * 
	 * @param subTree
	 * @param count
	 * @return
	 * Calculate Subtree Entropy to find best split ( List of  Values)
	 */
	private double calculateSubTreeEntropy(Map<String, ValueInfo> subTree,
			int count) {
		double totalEntropy = 0;

		Iterator<Entry<String, ValueInfo>> iterator = subTree.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, ValueInfo> entry = (Entry<String, ValueInfo>) iterator
					.next();
			ValueInfo info = entry.getValue();

			double entropy = calculateEntropy(new ArrayList<Integer>(info
					.getAttributeClasses().values()), info.getRowsCount());
			info.setEntropy(entropy);
			totalEntropy += ((double) info.getRowsCount() / count) * entropy;

		}

		return totalEntropy;
	}

	/**
	 * 
	 * @param classRecords
	 * @param total
	 * @return
	 * Calculate Entropy for single value
	 */
	public double calculateEntropy(List<Integer> classRecords, int total) {

		double entropy = 0;

		for (int i = 0; i < classRecords.size(); i++) {
			double probability = (double) classRecords.get(i) / total;

			entropy -= probability * logb(probability, 2);

		}
		return entropy;
	}

	/**
	 * 
	 * @param classRecords
	 * @param total
	 * @return
	 * Overload for above method
	 */
	public double calculateEntropy(Integer[] classRecords, int total) {

		double entropy = 0;
		// System.out.println("II TOTAL = "+total);
		for (int i = 0; i < classRecords.length; i++) {
			double probability = (double) classRecords[i] / total;

			entropy -= probability * logb(probability, 2);

		}
		return entropy;
	}

	/**
	 * 
	 * @param classRecords
	 * @param total
	 * @return
	 * Calculate Gini another measure of unpredictability of information content
	 */
	public double calculateGini(Integer[] classRecords, int total) {
		double gini = 0;

		for (int i = 0; i < classesCount; i++) {
			double probability = (double) classRecords[i] / total;
			gini += Math.pow(probability, 2);
		}
		gini = 1 - gini;
		return gini;
	}

	public static double logb(double a, double b) {
		if (a == 0)
			return 0;
		return Math.log(a) / Math.log(b);
	}

	public Node getRootNode() {
		return rootNode;
	}

}
