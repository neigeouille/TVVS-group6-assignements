# Assignment 2

In this project, we were tasked with selecting five functions and preforming unit testing in each of them.

As this is black-box, we were not supposed to look at source code, but in the research phase, we found out that the available documentation was very lacking, making it impossible to select or derive the category partitions for a test from it. So we ended up having to look at the source code and trying to derive what was the original intention of the programmer.

## Selected Functions 

We selected these functions because they seemed both crucial to the app and testable.

1. jtimesched.misc.PlainTextFormatter.format()
2. jtimesched.project.ProjectTableMode.setValueAt()
3. jtimesched.project.ProjectTableMode.getValueAt()
4. jtimesched.project.ProjectTime.formatDate()
5. jtimesched.project.ProjectTime.parseSeconds()

___
## 1 format()

- in: LogRecord
- out: String

### __Purpose__

This method takes a LogRecord as an argument and converts it to a String to enable all kinds of parsing.

### __Category-Partition__

return a String for every LogRecord

### __Unit tests__

All possible scenarios were tested.

### __Outcome of the tests__

Passed all tests

## 2 setValueAt()

- in: Object, int, int

### __Purpose__

This method sets values on the Project Table of the app.

### __Category-Partition__

Valid inputs must be set accordingly

Invalid inputs must either not be set or throw an exception.

### __Unit tests__

Every column was tested, including out of bounds.

Every row was tested, including out of bounds.

### __Outcome of the tests__

Passed all tests except when the columns were out of bounds, it should throw an exception or at least log the mistake, but it doesn't. The rows threw IndexOutOfBoundsExceptions, but these weren't in the method description.

___

## 3 getValueAt()

### __Purpose__

- in: int, int
- out: Object

Return the object at a given position in the table.

### __Category-Partition__

The gotten values should match the type of the values entered (previous test function tested this)

If it is out of bounds, an IndexOutOfBoundsException should be thrown

### __Unit tests__

Every column and row was tested, including out of bounds.

### __Outcome of the tests__

Rows out of bounds behave as expected, but columns don't throw an error or let the user know it failed in any way.

___

## 4 formatDate()

### __Purpose__

_jtimesched.project.ProjectTime.formatDate()_

- in : Date object
- out : a string containing the same date but under another format

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
We test the formatDate() method by creating a good and a wrong Date objects and then format them using the method. We create assertions with the expected output values. In the first case the assertion is a assertEquals() because we know the expected value. In the second case, we use a assertNotEquesl() with the supposely correct output that, in reality, can't be rerturned due to the wrong values that are out of the edges.

### __Outcome of the tests__

Obviously, the right Date output the right format.
On the contrary, the Date that doesn't fit the edges will output a strange formatted date but with the right format. Ir doesn't match the expected value so the assertion is valid.

___

## 5 parseSeconds()

### __Purpose__

jtimesched.project.ProjectTime.parseSeconds()

- in: string
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

### __Outcome of the tests__

For the well formated time the assertion passes. 
For the other case, the test passes if the the exception in thrown.
