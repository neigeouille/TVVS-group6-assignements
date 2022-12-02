# Assignment \#8 Dataflow Testing

## Chosen Functions

We have chosen these functions because they have different variable types and flows from each other, they have a different number of variables and because they were relatively simple to test and modify as we saw fit:

- ```de.dominik_geyer.jtimesched.project.Project.adjustSecondsToday(int secondsToday)```
- ```de.dominik_greyer.jimesched.project.ProjectTime.parseSeconds()```
- ```de.dominik_geyer.jtimesched.project.ProjectTableModel.isCellEditable(int row, int column)```

## Dataflow  Tables and Graphs

### ```Project.adjustSecondsToday(int secondsToday)```

![Adjust Seconds Today Graph](C:\Users\hugui\OneDrive\Área de Trabalho\adjustSecond.svg)

var secondsToday

| id   | def  | use  | path          |
| ---- | ---- | ---- | ------------- |
| 1    | 1    | (2T) | 1,2,3,4,5,6,7 |
| 2    | 1    | (2F) | 1,2,3,4,5,6,7 |
| 3    | 1    | 3    | 1,2,3,4,5,6,7 |
| 4    | 1    | 4    | 1,2,3,4,5,6,7 |
| 5    | 1    | 6    | 1,2,3,4,5,6,7 |
| 6    | 1    | (2T) | 1,2,4,5,6,7   |
| 7    | 1    | (2F) | 1,2,4,5,6,7   |
| 8    | 1    | 4    | 1,2,4,5,6,7   |
| 9    | 1    | 6    | 1,2,4,5,6,7   |

var secondsDelta

| id   | def  | use  | path    |
| ---- | ---- | ---- | ------- |
| 1    | 4    | 5    | 4,5,6,7 |

var this.secondsOversall

| id   | def  | use  | path  |
| ---- | ---- | ---- | ----- |
| 1    | 5    | 5    | 5,6,7 |





### ```ProjectTime.parseSeconds(String strTime)```

var strTime

| id   | def  | use  | path               |
| ---- | ---- | ---- | ------------------ |
| 1    | 1    | 3    | 1,2,3,4,5          |
| 2    | 1    | 3    | 1,2,3,4,6,7,8,9,10 |

var m

| id   | def  | use  | path           |
| ---- | ---- | ---- | -------------- |
| 1    | 3    | (4T) | 3,4,5          |
| 2    | 3    | (4F) | 3,4,6,7,8,9,10 |
| 3    | 3    | 6    | 3,4,6,7,8,9,10 |
| 4    | 3    | 7    | 3,4,6,7,8,9,10 |
| 5    | 3    | 8    | 3,4,6,7,8,9,10 |

var p

| id   | def  | use  | path               |
| ---- | ---- | ---- | ------------------ |
| 1    | 2    | 3    | 2,3,4,5,6,7,8,9,10 |
| 2    | 2    | 3    | 2,3,4,5            |

var hour

| id   | def  | use  | path       |
| ---- | ---- | ---- | ---------- |
| 1    | 6    | 9    | 6,7,8,9,10 |

var minutes

| id   | def  | use  | path     |
| ---- | ---- | ---- | -------- |
| 1    | 7    | 9    | 7,8,9,10 |

var seconds

| id   | def  | use  | path   |
| ---- | ---- | ---- | ------ |
| 1    | 8    | 9    | 8,9,10 |

var out

| id   | def  | use  | path |
| ---- | ---- | ---- | ---- |
| 1    | 9    | 10   | 9.1  |

### ```ProjetcTableModel.isCellEditable(int row, int column)```

![Is Cell Editable Graph](C:\Users\hugui\OneDrive\Área de Trabalho\dataflow2.svg)

var column

| id   | def  | use  | path      |
| ---- | ---- | ---- | --------- |
| 1    | 1    | 3    | 1,2,3,4   |
| 2    | 1    | 5    | 1,2,3,5,6 |

var row

| id   | def  | use  | path        |
| ---- | ---- | ---- | ----------- |
| 1    | 1    | 2    | 1,2,3,5,7,8 |

var p

| id   | def  | use  | path    |
| ---- | ---- | ---- | ------- |
| 1    | 2    | 6    | 2,3,5,6 |

## Generated  Test  Cases

```adjustSecondsTodayTest(int secondsToday)```

This was the unit test created for ```adjustSecondsToday(int secondsToday)```, it tests all the variables and the tests passed successfully in the first try.

```parseSecondsTest(String strTime)```

This was the unit test created for ```parseSeconds(String srtTime)```, it tests all the variables and the tests passed successfully in the first try too.

```testIsCellEditable(int seconds)```

This was the unit test created for ```isCellEditable(int row, int column)```, it tests all the variables, some of the tests do not pass, since the method does not declare that it throws any Exception and yet isn't resistent to Indexes out of bounds.



