package com.machine.learning.decisiontrees;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author mostafa
 * Pojo class to wrap data of each unique value on an attribute
 */
public class ValueInfo {

	/**
	 * the name of the value
	 */
	String name;
	/**
	 * Class Name as key and number of rows this value repeated
	 */
	Map<String,Integer> attributeClasses;
	/**
	 * Rows associated with current value
	 */
	BitSet rows;
	/**
	 * Total Number of rows associated with this value
	 */
	int rowsCount;
	/**
	 * Entropy of this value
	 */
	double entropy;
	
	public ValueInfo(Set<String> classes,BitSet rows) {
		this.attributeClasses = new HashMap<String, Integer>();
		for (String currentClass : classes) {
			attributeClasses.put(currentClass,0 );
		}
		this.rows = rows;
		this.rowsCount = 0;
		this.entropy = 0;
	}
	
	public ValueInfo(Map<String, Integer> attributeClasses,
			BitSet rows, int rowsCount) {
		super();
		this.attributeClasses = attributeClasses;
		this.rows = rows;
		this.rowsCount = rowsCount;
	}
	
	
	/**
	 * 
	 * @param className
	 * Add entry to attributeClasses Map 
	 */
	public void addClass(String className){
		attributeClasses.put(className, 0);
	}
	
	/**
	 * Increase The current Class Count
	 * @param className
	 */
	public void increaseClass(String className){
		attributeClasses.put(className, attributeClasses.get(className)+1);
		
	}
	
	/**
	 * Increase total rows Count
	 */
	public void increaseRowCount(){
		rowsCount++;
	}
	
	/**
	 * Mark Row at index as 
	 * @param index
	 */
	public void setRowAt(int index){
		rows.set(index);
	}
	
	public Map<String, Integer> getAttributeClasses() {
		return attributeClasses;
	}
	public void setAttributeClasses(Map<String, Integer> attributeClasses) {
		this.attributeClasses = attributeClasses;
	}
	public BitSet getRows() {
		return rows;
	}
	public void setRows(BitSet rows) {
		this.rows = rows;
	}
	public int getRowsCount() {
		return rowsCount;
	}
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}
	
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
	
	public double getEntropy() {
		return entropy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("\n--- Classes ---");
		sb.append("\n");
		attributeClasses.forEach((k,v)-> sb.append(k+" (count) = "+v+" |"));
		sb.append("\n");
		sb.append(" --- Rows ---");
		sb.append(rows);
		sb.append("\n");
		sb.append("--- Count ----");
		sb.append(rowsCount);
		return sb.toString();
	}
	
}
