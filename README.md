# TaskSorter

This program takes in a list of tasks. Each line is a different task, where the task comes first, followed by all tasks that
need to be completed in order to begin the new task. For instance, the line " A B C" says that in order to complete task A, tasks
B and C must already have been completed.

The program constructs a graph from the list of tasks. If the graph is cyclic, the program determines there is no order in which to complete the tasks.
If the graph is acyclic, the program returns the order in which to complete the tasks.

There are three files to run this on: cyclic.task.txt which contains a cyclic list of tasks, acyclic.task.txt, which contains an acyclic list of tasks, and cscourses.txt, which contains an acyclic list of classes along with their prerequisites and returns the order in which to take the classes.
