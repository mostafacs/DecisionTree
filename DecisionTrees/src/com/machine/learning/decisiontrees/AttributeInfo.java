package com.machine.learning.decisiontrees;

import java.util.Map;

/**
 * 
 * @author mostafa
 * Pojo Class to wrap data of each attribute
 */
public class AttributeInfo {
	
	/**
	 * Attribute Name find get it from first row in training file
	 */
	String name;
	/**
	 * Map of each attribute value as a key and information of it as a value
	 */
	Map<String, ValueInfo> values;
	/**
	 * Number of rows without null values for this attribute
	 */
	Integer rowCount;
	/**
	 * index number for this attribute
	 */
	int index;
	
	public Map<String, ValueInfo> getValues() {
		return values;
	}
	public void setValues(Map<String, ValueInfo> values) {
		this.values = values;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	
	
	

}
