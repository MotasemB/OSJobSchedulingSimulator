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

## Dynamic Round Robin
In the Dynamic Round Robin algorithm the operating system adjusts the time quantum according to the burst time of the existed set of processes in the ready queue. For the first process, it  begins with time quantum equals to the burst time of first process, which is subject to change after the end  of the first time quantum. When a new process is loaded into the ready queue in order to be executed, the operating system calculates the average of sum of the burst times of processes found in the ready queue. This method needs two global variables:  
-  SR:  to store the sum of the remaining burst times in the ready queue.  
-  AR: to store the average of the burst times by dividing the value found in the SR by the count of processes found in the ready queue

Algorithm: 

*TQ = time quantum, BT = burst time, AVG = average*
```
New process P arrivees
	P Enters ready queue
	Update SR and AR
	Process p is loaded from ready queue into the CPU to be exectued
		IF (Ready Queue is Empty)
			TQ=BT (p)
			Update SR and AR
		End if
		IF (Ready Queue is not empty)
			TQ=AVG (Sum BT of processes in ready queue)
			Update SR and AR
		End if
		CPU exectutes P by TQ time
		IF (P is terminated)
			Update SR and AR
		End if
		IF (P is not terminated)
		Return p to the ready queue with its updated burst time
		Update SR and AR
		End if
		
```


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
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2. The contents of each queue.<br>
 
It is is assumed that at the end of each file there is a 'D ∞' command, ∞ is assumed to be a very large number and will denoted as 999999. This command will show the final state of the system.

Sample Input:
```
C 9 M=45 S=12
A 1 J=1 M=30 S=0 R=10 P=1
A 2 J=2 M=40 S=0 R=15 P=2
A 3 J=3 M=20 S=0 R=6 P=2
A 5 J=4 M=30 S=0 R=16 P=1
A 7 J=5 M=50 S=0 R=10 P=1
A 9 J=6 M=30 S=0 R=12 P=1
A 10 J=7 M=30 S=0 R=5 P=2
A 12 J=8 M=50 S=0 R=15 P=1
A 15 J=9 M=130 S=0 R=20 P=2
A 20 J=10 M=40 S=0 R=10 P=2
A 26 J=11 M=20 S=0 R=9 P=1
A 45 J=12 M=10 S=0 R=3 P=2
A 58 J=13 M=15 S=0 R=5 P=2
D 999999
C 10 M=40 S=0
A 2 J=1 M=18 S=0 R=15 P=1
A 3 J=2 M=10 S=0 R=7 P=1
A 4 J=3 M=15 S=0 R=10 P=1
D 999999
```


## Output
The program will output a text file, with the states of the system given at the different display('D') commands, and then at the end display the final state of the system.

Sample Output for Dynamic Round Robin:
```
Final State of system:
Current Available Main Memory: 45
Current Avaiblable Devices: 12

Competed Jobs:
-----------------
Job ID   Arrival Time    Finish Time  Turnaround Time
------------------------------------------------------------
 1         1              11              10 
 2         2              36              34 
12        45              59              14 
 4         5              83              78 
13        58             115              57 
 6         9             143             134 
 3         3             167             164 
11        26             188             162 
 7        10             202             192 
10        20             217             197 

System turnaround time: 1042

******************************************************************


Final State of system:
Current Available Main Memory: 40
Current Avaiblable Devices: 0

Competed Jobs:
-----------------
Job ID   Arrival Time    Finish Time  Turnaround Time
------------------------------------------------------------
 1         2              17              15 
 2         3              45              42 
 3         4              59              55 

System turnaround time: 112

******************************************************************
```




