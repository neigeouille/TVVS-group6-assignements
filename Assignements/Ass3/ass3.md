
    Which functions have you selected for testing and why.
    What is the purpose of each function.
    Step-by-step of the ‘Boundary Value Analysis’ for each function.
    Brief description of the unit tests generated for each ‘boundary’.
    Brief description of the outcome of each unit test and whether any test results in a failure (and why).


# 1 - adjusySecondsToday(int secondsToday)

- We have selected the function _public void adjustSecondsToday(int secondsToday)_ because to begin with a simple function with one parameter.

- The goal of this function is to return the same second value if it is positive or to return 0 if the entry is negative.


### STEP 1 :
entry parameter : int
condition : <0
=> not min or maximum value but there is a change around 0.

### STEP 2 :
E1 : <0
E2 : >=0

### STEP 3 :
on-point : 0
off-point : -1 and 1
in-points : ]-∞;0[ where we can take -6
out-points : [0;+∞[ where we can take 6

### CONCLUSION :
We can implement 5 different tests with the values we have just found.

- We implemented a test that create a new object _Project_ and from this object we use the function _adjustSecondsToday()_. We also use a __stream__ to send entries automatically (0, -1, 1, -6 and 6). The assertion we use is _equal to 0_ so that we can see if the condition is TRUE when the entry is negative.

- The outcome is TRUE or 0 -1 and -6 which is normal and what we could expect. Here 0 is not negative and doesn't enter the condition so it should be FALSE but as this is the __on-point__, this behavior is normal.




