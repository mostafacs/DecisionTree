# Decision Trees

Decision Tree Classifier, which is a simple yet widely used classification technique.
[More Information](http://en.wikipedia.org/wiki/Decision_tree)

# What's Classification ?

Classification, which is the task of assigning objects to one of several predefined
categories

# Implementation

This Implementation is Optimized for very large DataSets .
First , Learning File must be in PSV ( Pipe Separated Values ). There is example file already exist in resources/vertebrate.psv
First Column not used as attribute just work as entity label and Last Column work as entity Class Type of data
also first row work as attributes names .

There is python script to generate psv file from MySQL database "generatePSVFromTables.py"
You need install dependencies to use script
```python
pip install MySQLdb
then run :
python generatePSVFromTables.py
```
you need to edit this script to define your connection parameters :

```python
host = "localhost"
user = "root"
password = "root"
database = "myinfo"
```

also you must change the sql Query with your Sql Statement :

```python
sql = 'select e.id as "Employee Number" , e.first_name as "First Name" , e.last_name as "Last Name", e.haschildren as "Has Children" , d.department_name as "Department Name" from employees e,department d where e.department_no = d.id;'
```

Note - You Must add Target Class for each row after this or Edit Script to add your class based on data

 
# Usage


```java
	DecisionTree tree = new DecisionTree();
	// Train your Decision Tree
	tree.train(new File("resources/vertebrate.psv"));
	// Print RootNode display xml structure from your decision tree learning
	System.out.println(tree.getRootNode());
	// Classify your new data
	System.out.println(tree.classify("gila monster|cold-blooded|scales|no|no|no|yes|yes"));
```

# Requirements
Java 8

# Next Features

1- Maximum Split Feature . <br>
2- Support Splitting of Continuous Attributes . <br>
3- Create Script to make .psv file from BigData (Hadoop ,...) . <br>
4- Parallel Files Processing "Support learning from many files on different machine" . <br>

