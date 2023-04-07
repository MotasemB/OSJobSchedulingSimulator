//Motasem B
//OS Project

package roundrobin;

public class Job implements Comparable<Job>{
    private int arrive;
    private int jobNumber;
    private int memory;
    private int devices;
    private int burstTime;
    private int priority;
    private int finishTime;
    
    public Job(){
        
    }
    
    public Job(int a, int j, int m, int d, int r, int p){
       arrive = a;
       jobNumber = j;
       memory = m;
       devices = d;
       burstTime = r;
       priority = p;
    }

    public int getArrive() {
        return arrive;
    }

    public void setArrive(int arrive) {
        this.arrive = arrive;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }
    
    
    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getDevices() {
        return devices;
    }

    public void setDevices(int devices) {
        this.devices = devices;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
    
    
     public int compareTo(Job s)
    {
        if (memory > s.getMemory()) {
            return 1;
        }
        else if (memory == s.getMemory()) {
            return 0;
        }
        else {
            return -1;
        }
    }
    
    public String toString(){
        return "Arrived at: " + this.arrive + "\nJob number: " + this.jobNumber + "\nMemory: " + this.memory + "\nDevices: " + this.devices + "\nRuntime: " + this.burstTime + "\npriority: " + this.priority;
    }
}
