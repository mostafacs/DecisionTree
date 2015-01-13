
'''
This is python script used to generate Bar Separated Data Files
Change Connection Parameters Below and your Sql Query

'''

import MySQLdb 
import traceback
from MySQLdb.constants import FIELD_TYPE


# Edit with your connection params
host = "localhost"
user = "root"
password = "root"
database = "myinfo"


#Adding support for bit data type
conv = { FIELD_TYPE.BIT: bool }

connection = MySQLdb.connect(conv=conv,host=host , user=user, passwd=password, db=database)

cursor = connection.cursor()

outputFile = open('myData.psv', 'w+')

try:
	       # Edit with your sql query
	       sql = 'select e.id as "Employee Number" , e.first_name as "First Name" , e.last_name as "Last Name", e.haschildren as "Has Children" , d.department_name as "Department Name" from employees e,department d where e.department_no = d.id;'


	       cursor.execute(sql) 
	       col_names = [i[0] for i in cursor.description]
	       for index in range(len(col_names)):
		 if index == len(col_names)-1 :
		  outputFile.write(col_names[index])
		 else:
		   outputFile.write(col_names[index]+'|')
	       results = cursor.fetchall()
	       for idx,rowsql in enumerate(results):
		 outputFile.write('\n')
		 for index in range(len(col_names)):
		  
		  text = rowsql[index]
		  if rowsql[index] is None:
			    text = ''
		  if index == len(col_names)-1 :       
		        outputFile.write(str(text))
		  
		  else:
		     outputFile.write(str(text)+'|')
		
	      
except Exception:
		print traceback.format_exc()
		# Rollback in case there is any error
		print '-------------------------------------------------------------- ERROR --------------------------------------------------------------------'
		connection.rollback()
		print sql
		print '-----------------------------------------------------------------------------------------------------------------------------------------'               
                  
connection.close()

 
