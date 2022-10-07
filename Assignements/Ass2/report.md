# Assignement 2


_Which functions have you selected for testing and why._

1. jtimesched.misc.PlainTextFormatter.format()
2. jtimesched.project.ProjectTableMode.setValueAt()
3. jtimesched.project.ProjectTableMode.getValueAt()
4. jtimesched.project.ProjectTime.formatDate()
5. jtimesched.gui.JTimeSchrdFrame.backupProjects()

_What is the purpose of each function._

1. jtimesched.project.ProjectTime.formatDate()

- it takes a Date object in parameter
- returns a string under another format
=> so to converts a date into another format

We can see that the fomart that is returned is like "YYYY-MM-DD".
So, we create a @Test in the ProjectTimeTest file. We test the formatDate() method by creating a Date object

_Step-by-step of the 'Category-Partition' algorithm for each function._


_Brief description of the unit tests generated for each category._


_Brief description of the outcome of each unit test and whether any test results in a failure (and why)._
