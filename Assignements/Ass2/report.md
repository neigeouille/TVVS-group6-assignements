# Assignement 2



## Select Functions 

__*TODO : WHY THESE FUNCTIONS*__

1. jtimesched.misc.PlainTextFormatter.format()
2. jtimesched.project.ProjectTableMode.setValueAt()
3. jtimesched.project.ProjectTableMode.getValueAt()
4. jtimesched.project.ProjectTime.formatDate()
5. jtimesched.project.ProjectTime.parseSeconds()

___
## 1 format()

### __Purpose__

### __Category-Partition__


### __Unit tests__


### __Ourcome of the tests__

___

## 2 setValueAt()

### __Purpose__

### __Category-Partition__


### __Unit tests__


### __Ourcome of the tests__

___

## 3 getValueAt()

### __Purpose__

### __Category-Partition__


### __Unit tests__


### __Ourcome of the tests__

___

## 4 formatDate()

### __Purpose__

_jtimesched.project.ProjectTime.formatDate()_

- in : Date object
- put : a string containing the same date but under another format

function = to convert a date into another date format

### __Category-Partition__

We can find easily the edges of the parameters of a date :
- year ]0;++[
- month [0;11] -> because it uses an enum
- day ]0;32[

|Y|M|D|Out|
|--|:--:|:--:|--:|
|<=0|x|x|No|
|<=0|x|<=0|No|
|<=0|x|>=32|No|
|<=0|<0|x|No|
|<=0|<0|<=0|No|
|<=0|<0|>=32|No|
|<=0|>11|x|No|
|<=0|>11|<=0|No|
|<=0|>11|>=32|No|
|x|x|x|__YES__|
|x|x|<=0|No|
|x|x|>=32|No|
|x|<0|x|No|
|x|<0|<=0|No|
|x|<0|>=32|No|
|x|>11|x|No|
|x|>11|<=0|No|
|x|>11|>=32|No|

### __Unit tests__

We can see that the format that is returned is like "YYYY-MM-DD". So, we create a @Test in the ProjectTimeTest file that will verify if the output is formated like it should.
We test the formatDate() method by creating a good and a wrong Date objects and then format them using the method. We create assertions with the expected output values. In the first case the assertion is a assertEquals() because we know the expected value. In the second case, we use a assertNotEquesl() with the supposely correct output that, in reality, can't be rerturns due to the wrong values that are out of the edges.

### __Ourcome of the tests__

Obviously, the right Date output the right format.
On the contrary, the Date that doesn't fit the edges will output a strange formatted date but with the right format. Ir doesn't match the expected value so the assertion is valid.

___

## 5 parseSeconds()

### __Purpose__

jtimesched.project.ProjectTime.parseSeconds()

- int : string
- out : an int which is the equivalent in seconds of the time passed in parameter 
- exception : throw a ParseException

function = to convert the time into seconds

### __Category-Partition__

Once again, we need to find the edges of the parameter. As a string that should contain an hour:min:sec format we find that the edges are :
- hour [0;++[
- minutes [0;59]
- seconds [0;59]

This give us the following table.


|hour|min|sec|Out|
|--|:--:|:--:|--:|
|<0|x|x|throw ex|
|<0|x|<0|throw ex|
|<0|x|>59|throw ex|
|<0|<0|x|throw ex|
|<0|<0|<0|throw ex|
|<0|<0|>59|throw ex|
|<0|>59|x|throw ex|
|<0|>59|<0|throw ex|
|<0|>59|>59|throw ex|
|x|x|x|__YES__|
|x|x|<0|throw ex|
|x|x|>59|throw ex|
|x|<0|x|throw ex|
|x|<0|<0|throw ex|
|x|<0|>59|throw ex|
|x|>59|x|throw ex|
|x|>59|<0|throw ex|
|x|>59|>59|throw ex|


### __Unit tests__

We tested 2 cases.
The first case is where this the entry parameter is well formated (inside edges). The assertion compares this output of the tested function to the excepted value that we set.

The second case is where the string is anything else than well formated. In this case, the function is suppoded to throw a specific exception so we test it with the assertThrows(). It allows us to verify if the exception is thrown and if it is the right one.

### __Ourcome of the tests__

For the well formated time the assertion passes. 
For the other case, the test passes if the the exception in thrown.
