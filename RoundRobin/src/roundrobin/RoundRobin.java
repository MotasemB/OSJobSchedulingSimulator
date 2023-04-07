//Motasem B
//OS Project
package roundrobin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;

public class RoundRobin {

    //number of jobs total
    public static int JobsNum = 0;

    //i and e for external and internal events
    public static int i;
    public static int e;

    //number of resoruces used
    public static int memoryUsed = 0;
    public static int devicesUsed = 0;

    //number of resources avaible in the system
    public static int systemMemory = 0;
    public static int systemDevices = 0;

    //current time of the system
    public static int currentTime = 0;
    public static int timeRan = 0;

    //varaibles for the Dynamic Round Robin
    public static int SR = 0;
    public static int AR = 0;
    public static int TQ = 0;

    //creating a master queue, that will hold all the jobs and the system status
    public static LinkedList<Job> master = new LinkedList<Job>();
    //creating Hold Queues
    public static LinkedList<Job> Hold1 = new LinkedList<Job>();
    public static LinkedList<Job> Hold2 = new LinkedList<Job>();

    //creating Ready Queue
    public static LinkedList<Job> ready = new LinkedList<Job>();

    //creating Compelete Queue
    public static LinkedList<Job> compelete = new LinkedList<Job>();
    

    public static void main(String[] args) throws FileNotFoundException {
        //Making input and output files
        File in = new File("input.txt");
        File output = new File("output.txt");

        //checks if input file exists
        if (!in.exists()) {
            System.out.println("input.txt file does not exist!");
            System.exit(0);

        }
        //creates Scanner to read input File
        Scanner input = new Scanner(in);

        //creates PrintWriter
        PrintWriter out = new PrintWriter(output);

        while (input.hasNext()) {
            char type = input.next().charAt(0);

            if (type == 'C') {
                //clear all the queues
                Hold1.clear();
                Hold2.clear();
                ready.clear();
                compelete.clear();

                //reset all the Dyanmic Round Robin numbers
                SR = 0;
                AR = 0;
                TQ = 0;
                JobsNum = 0;

                //changed this from arrivalTime to CurrentTime to know when the system time starts at
                currentTime = input.nextInt();
                String M = input.next();
                systemMemory = Integer.parseInt(M.substring(2));
                String S = input.next();
                systemDevices = Integer.parseInt(S.substring(2));
            }
            int num = 0;
            while (input.hasNext()) {
                type = input.next().charAt(0);
                //take all info for job
                if (type == 'A') {
                    int jobTime = input.nextInt();
                    String J = input.next();
                    int jobNumber = Integer.parseInt(J.substring(2));
                    String M = input.next();
                    int jobMemory = Integer.parseInt(M.substring(2));
                    String S = input.next();
                    int jobDevices = Integer.parseInt(S.substring(2));
                    String R = input.next();
                    int runTime = Integer.parseInt(R.substring(2));
                    String P = input.next();
                    int priority = Integer.parseInt(P.substring(2));

                    Job add = new Job(jobTime, jobNumber, jobMemory, jobDevices, runTime, priority);
                    
                    //add to master queue all jobs that can be done in system
                    if (jobMemory > systemMemory || jobDevices > systemDevices) {
                        continue;
                    } else {
                        master.add(add);
                        JobsNum++;
                    }
                } 
                //add displays as jobs to in master queue
                else if (type == 'D') {
                    int dTime = input.nextInt();
                    if (dTime < 999999) {
                        Job add = new Job(dTime, 999999, 999999, 999999, 999999, 999999);
                        master.add(add);
                    } else {
                        num = 999999;
                        break;
                    }
                }
            }
            
            //intilaize first job
            Job one = master.get(0);
            currentTime = one.getArrive();

            memoryUsed += one.getMemory();
            devicesUsed += one.getDevices();
            ready.add(one);
            master.remove(0);
            RR();
            i = 0;
            e = 0;
            
            //while all the jobs have not been completed, calculate i and e and do external or interl events
            while (compelete.size() != JobsNum) {
                if (!master.isEmpty()) {
                    i = master.get(0).getArrive();
                } else {
                    i = 999999;
                }

                if (!ready.isEmpty()) {
                    e = timeRan;
                } else {
                    e = 999999;
                }

                currentTime = Math.min(i, e);

                if (i < e) {
                    external(out);
                } else if (e < i) {
                    RR(); //internal
                    
                    
                } else {
                    RR(); //internal
                    external(out);
                }
            }
            if(num == 999999 && compelete.size() == JobsNum){
                displayFinal(out);
            }
            
        }
        out.flush();
        out.close();
    }
    
    //RR algorthim, also the interal events
    public static void RR() {
        Job current = ready.get(0);
        int burst = current.getBurstTime();
        TQ = 15;
        currentTime += TQ;
        timeRan = currentTime + TQ;
        current.setBurstTime(burst - TQ);

        int burstAfter = current.getBurstTime();
        if (burstAfter <= 0) {
            current.setFinishTime(currentTime);
            SR -= burst;
            compelete.add(current);
            memoryUsed -= current.getMemory();
            devicesUsed -= current.getDevices();
            ready.remove(0);
            if (ready.size() == 0) {
                AR = 0;
            } else {
                AR = SR / ready.size();
            }

            holdCheck();
        }

        if (burstAfter > 0) {
            SR -= burst;
            SR += ready.get(0).getBurstTime();
            AR = SR / ready.size();
            ready.remove(0);
            ready.add(current);
        }

    }
    
    //external events, takes input from the master queue, and adds to the ready, hold1, or hold2 queues
    public static void external(PrintWriter out) {
        if (!master.isEmpty()) {
            if (master.get(0).getBurstTime() == 999999 && master.get(0).getArrive() < 999999) {
                master.remove(0);
                display(currentTime,out);
            } else {
                Job add = master.get(0);
                master.remove(0);
                if (memoryUsed + add.getMemory() > systemMemory) {
                    if (add.getPriority() == 1) {
                        Hold1.add(add);
                    } else {
                        Hold2.add(add);
                        Collections.sort(Hold2);
                    }
                } else {
                    memoryUsed += add.getMemory();
                    devicesUsed += add.getDevices();
                    ready.add(add);
                }
            }
        }
    }
    
    //checks both hold queues in order to see which jobs can be added to the ready queue
    public static void holdCheck() {
        for(int i = 0; i < Hold1.size(); i++){
            Job hold = Hold1.remove();
            
            if(hold.getMemory() + memoryUsed <= systemMemory && hold.getDevices() + devicesUsed <= systemDevices){
                memoryUsed += hold.getMemory();
                devicesUsed += hold.getDevices();
                ready.add(hold);
            }
            else{
                Hold1.add(hold);
            }
        }
        
         for(int i = 0; i < Hold2.size(); i++){
            Job hold = Hold2.remove();
            
            if(hold.getMemory() + memoryUsed <= systemMemory && hold.getDevices() + devicesUsed <= systemDevices){
                memoryUsed += hold.getMemory();
                devicesUsed += hold.getDevices();
                ready.add(hold);
            }
            else{
                Hold2.add(hold);
                Collections.sort(Hold2);
            }
        }
    }
    
    //displas the final system information
    public static void displayFinal(PrintWriter out) {
        out.println("Final State of system:");
        out.println("Current Available Main Memory: " + (systemMemory - memoryUsed));
        out.println("Current Avaiblable Devices: " + (systemDevices - devicesUsed));
        out.println("");
        out.println("Competed Jobs:");
        out.println("-----------------");
        out.println("Job ID   Arrival Time    Finish Time  Turnaround Time");
        out.println("------------------------------------------------------------");

        int total = 0;

        for (int i = 0; i < compelete.size(); i++) {
            Job current = compelete.get(i);
            total += current.getFinishTime() - current.getArrive();
            out.printf("%2s %9s %15s %15s %n", current.getJobNumber(), current.getArrive(), current.getFinishTime(), (current.getFinishTime() - current.getArrive()));
        }
        out.println("");
        out.println("System turnaround time: " + total);
        out.println("");
        out.println("******************************************************************");
        out.println("");
        out.println("");

    }
    
    //displays the current system information
    public static void display(int time,PrintWriter out) {
        out.println("State of system at " + time);
        out.println("Current Available Main Memory: " + (systemMemory - memoryUsed));
        out.println("Current Avaiblable Devices: " + (systemDevices - devicesUsed));
        out.println("Competed Jobs:");
        out.println("-----------------");
        out.println("Job ID   Arrival Time    Finish Time  Turnaround Time");
        out.println("------------------------------------------------------------");

        for (int i = 0; i < compelete.size(); i++) {
            Job current = compelete.get(i);
            out.printf("%2s %9s %15s %15s %n", current.getJobNumber(), current.getArrive(), current.getFinishTime(), (current.getFinishTime() - current.getArrive()));
        }

        out.println("");

        out.println("Jobs in Hold queue 1:");
        out.println("-----------------");
        out.println("Job ID   Arrival Time");
        out.println("------------------------------------------------------------");

        for (int i = 0; i < Hold1.size(); i++) {
            Job current = Hold1.get(i);
            out.printf("%2s %9s %n", current.getJobNumber(), current.getArrive());
        }

        out.println("");

        out.println("Jobs in Hold queue 2:");
        out.println("-----------------");
        out.println("Job ID   Arrival Time");
        out.println("------------------------------------------------------------");

        for (int i = 0; i < Hold2.size(); i++) {
            Job current = Hold2.get(i);
            out.printf("%2s %9s %n", current.getJobNumber(), current.getArrive());
        }

        out.println("");

        out.println("Jobs in Ready Queue:");
        out.println("-----------------");
        out.println("Job ID   Arrival Time    Remaining time");
        out.println("------------------------------------------------------------");

        for (int i = 0; i < ready.size(); i++) {
            Job current = ready.get(i);
            out.printf("%2s %9s %15s %n", current.getJobNumber(), current.getArrive(), current.getBurstTime());
        }

        out.println("");

        out.println("Jobs in CPU:");
        out.println("-----------------");
        out.println("Job ID   Arrival Time");
        out.println("------------------------------------------------------------");

        /*for(int i = 0; i < ready.size(); i++){
            Job current = ready.get(i);
            out.printf("%2s %9s %15s", current.getJobNumber(), current.getArrive(), current.getBurstTime());
        }*/
        out.println("");
        out.println("******************************************************************");
        out.println("");
        out.println("");

    }

}
