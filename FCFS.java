/*
 * File Name: FCFS.java
 * Purpose: This file implements the 'first come first serve' algorithm taught in lectures
 */
import java.util.LinkedList; 
import java.util.Queue;
import java.util.ArrayList; 

public class FCFS
{
    private Queue<Process> waitingQ = new LinkedList<>();               // a queue to store the job that are not ready to be executed 
    private Queue<Process> readyQ = new LinkedList<>();                 // a queue to store the jobs which are ready to be executed 
    private ArrayList<Process> completeQ = new ArrayList<Process>();    // an Array List to store all the comepleted jobs    
    private int disp;       											// a variable to store the time taken for a dispatcher to get a job from the ready queue
    private int time = 0;    											// a variable to store the time 
    private Process currentJob;                    						// a 'Process' variable to store the current job that is being processed
    private double averageTurnaroundTime;           					// a variable to store the average turnaround time  
    private double averageWaitingTime;              					// a variable to store the average waiting time 
    
    /*
     * Purpose: a contructor for the class
     * Pre-Condition: valid input parameters are given to the constructor
     * Post-Condition: a instance of FCFS is made and the dispatcher time is set to the input parameter provided to the function.
     */ 
    public FCFS(ArrayList<Process> jobs, int dispatcher)
    {
        int numOfJobs = jobs.size(); 
        for (int i = 0; i < numOfJobs; i++)
        {
            waitingQ.add(jobs.get(i));
        }
        disp = dispatcher; 
        currentJob = null;              // setting the current job to null
        averageTurnaroundTime = 0;      // setting the average Turn Around Time to 0
        averageWaitingTime = 0;         // setting the average waiting time to 0
    }
    
    /*
     * Purpose: Run the first come first serve algorithm 
     * Pre-Condition: an instance of FCFS exists within the program
     * Post-Condition: The algorithm is executed. The details on what time a job is executed is displayed to the user as well as the waiting time and turn around time for the job. 
     */
     public void Run()
    {
        System.out.println("FCFS:");
        // while ther is a job to done or there is a job waiting then this 'while' loop will keep executing until the condition is not met. 
        while (waitingQ.peek() != null || readyQ.peek() != null || currentJob != null)      
        {
            if (currentJob == null)     // there is no current job to be executed
            {
                isReady();      // check to see which jobs from the waiting queue are ready to be executed
                time += disp;    // the dispacther time is added to the time variable
                if (waitingQ.peek() == null && readyQ.peek() == null)  // all the jobs are done 
                {
                    break;  // breaking the while loop 
                }
                
                if (readyQ.peek() != null)      // check to see if there is anything in the ready queue
                {
                    currentJob = readyQ.remove();   // if there is something in the ready queue then it is remove from the ready queue and set to current job 
                }
            }
            else 
            {
                System.out.println("T" + time + ": p" + currentJob.getID());     // printing the time that the job started 
                time += currentJob.getBurstTime();                                // adding the execution time to the  time Variable 
                currentJob.setTATWT(time);                               // setting turn around time and waiting time for current job
                averageTurnaroundTime += currentJob.getTAT();           // adding the turnaround time for the current job to the average turnaround time variable
                averageWaitingTime += currentJob.getWT();               // adding the waiting time for the current job to the average waiting time variable
                completeQ.add(currentJob);                              // the current job is added to the completeQ
                currentJob = null;                                      // setting the current Job variable to null
            }
        }
        getTable();
		averageTurnaroundTime /= completeQ.size(); 
		averageWaitingTime /= completeQ.size();
    }
    
    /*
     * Purpose: check if any jobs on the waiting queue are ready to executed/processed 
     * Pre-Condition: an instance of FCFS exists on the program
     * Post-Condition: if the job or jobs at front of the waiting queue are ready to processed then that job is added to the 
	 * 				   ready queue
     */
    private void isReady()
    {
        if (waitingQ.peek() != null)		// check to see if the queue is empty 
        {
            int z = waitingQ.peek().getArrivalTime();	// a temporary variable to store the arrival time of the job at the front of the queue
            while (z <= time)							// checking if the job is ready, by comparing the arrival time to the time time
            {
                readyQ.add(waitingQ.remove());			// removing the job at the front of the waiting queue to be added to the ready queue
                if (waitingQ.peek() == null)
                {
                    break; 			// if the waiting queue is empty (no jobs are on the waiting queue) then the while loop is broken
                }
                else 	
                {
					// the queue is not empty so the arrival time of the next job is stored in the local variable 'z' 
                    z = waitingQ.peek().getArrivalTime();
                }
            }
        }
    }
    
    /*
     * Purpose: prints the waiting time and turn around time of each job 
     * Pre-Condition: an instance of FCFS exists. the algorithm has been executed and the turn around time and waiting is set for each job 
     * Post-Condition: the waiting time and turn around time for each job is printed in order of process id. 
     */
    private void getTable()
    {
        System.out.println("\nProcess\tTurnaround Time\tWaiting Time");
        for (int z = 1; z < completeQ.size() + 1; z++)	
        {
            for (int y = 0; y < completeQ.size(); y++)
            {
                if (z == completeQ.get(y).getID())
                {
					// the waiting time and turn around time for the job is printed
                    System.out.println("p" + completeQ.get(y).getID() + "\t" + completeQ.get(y).getTAT() + "\t\t" + completeQ.get(y).getWT()); 
                }
            }
        }
    }
    
    /*
     * Purpose: return the average turn around time for jobs in the algorithm
     * Pre-Condition: an instance of FCFS exists 
     * Post-Condition: teh average TurnaroundTime is returned when the function is called. 
     */
    public double getAvgTAT()
    {
        return averageTurnaroundTime; 
    }
    
    /*
     * Purpose: return the average waiting time for jobs in the algorithm
     * Pre-Condition: an instance of FCFS exists
     * Post-Condition: the average turn waiting is returned when the function is called
     */
    public double getAvgWT()
    {
        return averageWaitingTime; 
    }
}
