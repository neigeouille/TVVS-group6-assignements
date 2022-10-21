
# 1 - adjusySecondsToday(int secondsToday)

- We have selected the function _public void adjustSecondsToday(int secondsToday)_ because to begin with a simple function with one parameter.

- The goal of this function is to return the same second value if it is positive or to return 0 if the entry is negative.


### STEP 1 :
entry parameter : int<br>
condition : <0 <br>
=> not min or maximum value but there is a change around 0

### STEP 2 :
E1 : <0 <br>
E2 : >=0

### STEP 3 :
on-point : 0 <br>
off-point : -1 and 1 <br>
in-points : ]-∞;0[ where we can take -6 <br>
out-points : [0;+∞[ where we can take 6

### CONCLUSION :
We can implement 5 different tests with the values we have just found.

- We implemented a test that create a new object _Project_ and from this object we use the function _adjustSecondsToday()_. We also use a __stream__ to send entries automatically (0, -1, 1, -6 and 6). The assertion we use is _equal to 0_ so that we can see if the condition is TRUE when the entry is negative.

- The outcome is TRUE or 0 -1 and -6 which is normal and what we could expect. Here 0 is not negative and doesn't enter the condition so it should be FALSE but as this is the __on-point__, this behavior is normal.






# 3 - removeProject(int row)

- We have selected the function _public void removeProject(int row)_ because its parameter has two limits. So, we can find more classes through the Boundary Value Analysis.

- The goal of this function is to remove one of the object of the table by knowing its row. When its done the row of each object is updated.


### STEP 1 :
entry parameter : int <br>
condition : >=0 and <=9 <br>
=> for the tests we create a table that can contain only 10 rows, so until row=9.

### STEP 2 :
E1 : <0 <br>
E2 : >9 <br>
E3 : 0<= x <=9<br>

### STEP 3 :
on-point : 0 and 9 <br>
off-point : -1, 1, 8 and 10 <br>
in-points : [0;9] where we can take 5 <br>
out-points :  
- ]-∞;-1] where we can take -6
- [10;+∞[ where we can take 16

### CONCLUSION :
We can implement 9 different tests with the values we have just found. That is a lot of tests so we can reduce the number at the ON and OFF points : 0, 9, -1, 1, 8 and 10

- We implemented a test to check the behavior if the value of row is out of range. First, we use the object _ProjectTableModel_ created in the @Before. From this object we use the function _removeProject()_. We also use a __stream__ to send entries automatically (0, 9, -1, 1, 8 and 10). To verify that the points out of range are well handle by the exception that is supposed to be thrown, we use the _assertThrows()_ assertion with the exception _IndexOutOfBoundsException_.

- The test is validate for -1 and 10 because the exception is thrown when the values are out of range.




