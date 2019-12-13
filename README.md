# Comp2240 - Assignment_1
**_Mark_**: 99 /100

## Comments by Marker
**_Marks_**: 
- I/O (correctly reads input, displays results) : 10/10 
- Code Clarity (code structure, code comments) : 10/10 

**_Algorithms_**: 
- FCFS : 15/15 
- RR : 15/15 
- FB : 15/15 
- NRR : 20/20  
- Report: 14/15  

**_Deductions_**: 
- Late (-10 per day late) : /0 
- Coversheet (-5 if missing) : /0 
- Other (see Feedback) : /0 
 
**_Total_**:        99/100 
 
**_Notes_**: Results: Good. I/O: Good. Code: Good. Report: OK… 
 

## Assignment Specifications
Write a program that simulates First Come First Serve (FCFS), Round Robin (RR), Feedback constant (FB) and Narrow Round Robin (NRR) scheduling algorithms. For each algorithm, the program should list the order and time of the jobs being processed and compute waiting time and turnaround time for every job as well as the average waiting time and average turnaround time.  

The average values should be consolidated in a table for easy comparison (sample outputs are available through Blackboard).  
 
Two sample input data sets and the corresponding outputs have been supplied. Additional datasets will be used to test your program. The format of the input data will be the same as in the supplied sample files. 
 
Each input data set contains the following information (check the sample input files for exact format):  

1. Time for running the dispatcher (DISP) 

2. For each process: process id (ID) , arrival time (Arrive), service time (ExecSize) 
  a. It can be assumed that process P_i will always arrive before or at the same time of process P_(i+1) 
 
**Dispatcher**: It is assumed that the dispatcher runs to select the next process to run. The dispatcher should behave as follows:
1. The time to run the dispatcher is fixed and taken as input (DISP) from the input file. No other time is wasted in switching between processes other than this. 
2. If there is only one process running in the processor and no other process is waiting in the ready queue then there is no need to switch the process and the dispatcher will NOT run.  For example, in RR scheduling if process P1 is running in the CPU and no other process is waiting in the ready queue then P1 will continue even after its time quantum expires – no need to interrupt P1 to send it to ready queue after its time quantum expires and then run the dispatcher to reload P1 from the ready queue.  
3. If the dispatcher starts at t1 and finishes at t2 (i.e. time to run the dispatcher is t2-t1) then in that run it will choose from the processes that have arrived at or before t1. It will not consider any process that arrives after t1 for dispatching in that run. 
4. If a process P1 is interrupted at t1 and another process P2 arrives at the same time t1 then the newly arrived process P2 is added in the ready queue first and the interrupted process P1 is added after that.  
5. If two processes Px and Py have all other properties same (e.g. arrival time, priority etc.) then the tie between them is broken using their ID i.e. Px will be chosen before Py if x<y. 

Some details about the scheduling algorithms are as follows: 
- **FCFS**:  Standard FCFS scheduling algorithm.  
- **RR**:  Standard RR scheduling algorithm with time quantum of 4 milliseconds.  
- **FB**: Standard FB constant scheduling algorithm with time quantum of 4 milliseconds and a 6 level priority (0 is highest priority and 5 is lowest priority) NRR:  NRR Scheduling is a variant of the standard 
- **Round Robin (RR)** scheduling in which each process has its own time quantum q. Each process starts with q = 4ms and q is decreases by 1ms each time the process goes through the round robin queue, until it reaches a minimum of 2 ms. Thus, long jobs get decreasingly shorter time slices. 

## Programming Language
The programming language is Java and you may only use standard Java libraries as part of your submission. 

## Input/Output
Your program will accept data from an input file of name specified as a command line argument. The sample files datafile1.txt and datafile2.txt (containing the set1 and set2 data) are provided to demonstrate the required input file format.  
 
Your submission will be tested with the above data and will also be tested with other input files. 
 
Your program should output to standard output (this means output to the Console). Output should be strictly in the order FCFS, RR, FB, NRR, Summary.  
The sample files datafile1_output.txt and datafile2_output.txt (containing output for datafile1.txt and datafile2.txt respectively) are provided to demonstrate the required output (and input) format which must be strictly maintained. If output is not generated in the required format then your program will be considered incorrect. 

## Submission
1.. Your main class should be A1.java you program will compile with the command line javac A1.java and your program will be executed by running java A1 input.txt.  
2. Brief 1 page (A4) report, reviewing the results from your program and any interesting observations. Specifically, write a note about the relative performance of the algorithms based on your implemented versions of the algorithms. 
