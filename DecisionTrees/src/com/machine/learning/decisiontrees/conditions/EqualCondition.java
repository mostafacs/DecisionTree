package com.machine.learning.decisiontrees.conditions;

import com.machine.learning.decisiontrees.Node;

public class EqualCondition extends Condition
{
	String conditionValue;
	
	public EqualCondition(Node nextNode,String conditionValue) {
		
		super(nextNode);
		this.conditionValue=conditionValue;
		
		
	}
	
	@Override
	public boolean test(String value) {
		return conditionValue.equals(value);
	}
	
	@Override
	public String toString() {
		String out = " \n <Equal-Condition Value= '"+conditionValue+"'>";
		if(this.getNextNode()!=null){
		out+= "\n <NextNode> \n "+this.getNextNode().toString();
		}
		out+="\n </NextNode>";
		out+="\n </Equal-Condition>";
		return out;
	}
	

}
