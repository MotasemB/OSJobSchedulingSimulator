#  Operating Systems Simulator

These are **Java** programs created for a university Operating System course. The programs are made to simulate **Job and CPU scheduling** in an operating system. The programs will take an input file that gives the System Configuration and the jobs arriving at the given system. The programs will then be able to display the current status of the system at any given time, and then at the end display the final state of the system. The difference between the programs is that one uses a **Traditional Round Robin** algorithm and the other uses a **Dynamic Round Robin** algorithm for the CPU scheduling.

## Job Scheduling 
When a job arrives, there are three possible outcomes:

1.  If there is not enough total main memory or total number of devices in the system for the job, the job is rejected and never gets to one of the Hold Queues.
    
2.  If there is not enough available main memory or available devices for the job, the job is put in one of the Hold Queues, based on its priority, to wait for enough available main memory (preallocation). Hold Queue 1 is based on the requested units of main memory in ascending order, while Hold Queue 2 uses a first-in-first-out (FIFO) strategy.
    
3.  If there is enough main memory and devices for the job, then a process is created for the job, the required main memory and devices are allocated to the process, and the process is put in the Ready Queue.

![image](https://user-images.githubusercontent.com/71024036/230739520-007209af-af16-4354-bfd6-8b93a9b84338.png)

## Round Robin
The Round Robin (RR) scheduling algorithm is widely used in CPU scheduling. In this algorithm, each process is given a fixed time slice, known as the time quantum, to execute. If a process does not complete its execution within the time quantum, it is moved to the end of the ready queue. The next process in the queue is then executed for the given time quantum, and the process continues until all processes are completed. In our case the given time quantum is 15 milliseconds. 



## Input

The input to your program will be a text file containing multiple commands, each consisting of a letter followed by a set of parameters.

 - System Configuartion \(C\): Each text file contains multiple type "C" (system configuration) commands. <br>
 Example: C 9 M=45 S=12<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This example states that the system to be simulated starts at time 9, and that the  
system has a main  memory consisting of 45 units and 12 serial devices

 - Job Arrrival (A): This commands indicates that a job is coming in to the system, and gives information about the coming job<br>
 Example: A 10 J=1 M=5 S=4 R=5 P=1<br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This example states that job number 1 arrives at time 10, requires 5 units of main memory, holds no  more than 4 devices at any point during execution, runs for 5, and has priority 1.
 
 - A display of the current system status (D): This commands tells the program to output the current status of the system<br>
 Example: D 11<br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This examples states that at time 11 an external event is generated and the following should be printed:  
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1. A list of each job that has entered the system; for each job, print the state of the job, the remaining service time for unfinished jobs and the turnaround time.  
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.The contents of each queue.<br>
 
It is is assumed that at the end of each file there is a 'D ∞' command, ∞ is assumed to be a very large number and will denoted as 999999. This command will show the final state of the system.

## Output
The program will output a text file, with the states of the system given at the different display('D') commands, and then at the end display the final state of the system.



