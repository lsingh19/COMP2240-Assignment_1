/*
 * File Name: FB.java
 * Purpose: This file implements the 'Feedback (constant)' algorithm taught in lectures
 */
import java.util.LinkedList; 
import java.util.Queue;
import java.util.ArrayList; 
import java.util.*; 
import java.util.Scanner;  
    
public class FB
{
	private Queue<Process> waitingQ = new LinkedList();							// a queue to store all the jobs that are not ready for execution
	private ArrayList<Process> completedQ = new ArrayList<Process>();			// an Array List to store all the jobs that are completed
	private ArrayList<Queue<Process>> qs = new ArrayList<Queue<Process>>();		// an Array List of queues to store all the jobs that are ready for execution in a priority queue
	private Process currentJob;  												// a process variable that store the job that is current being processed
	private int disp; 															// a variable that stores the time taken for the dispatcher to get a job 
	private int time = 0;														// a variable to keep track of the time 
	private int execution = 4; 													// a variable to store the initial execution time allowed for a job
	private boolean notDone = true; 											// a variable that is used to check if all the jobs finsished
	private double avgTAT; 														// a variable to store the average turn around time
	private double avgWT;														// a variable to store the average waiting time 
        
    
	/* 
		Purpose: a constructor for FB 
		Pre-condition: valid inputs are provided
		Post-condition: an instance of FB is created. The jobs are added to the waiting queue and dispatcher time is set to the input parameter
	*/
	public FB(ArrayList<Process> jobs, int d)
	{
		// putting the jobs in the priority queue
		for (int i = 0; i < 5; i++)
		{
			waitingQ.add(jobs.get(i));
		}
		
		currentJob = null; 		// setting the current job variable to null 
		disp = d;				// setting the dispatcher time to the input parameter for the dispatcher time
		
		// creating the 6 priority queues 
		for (int z = 0; z < 6; z++)
		{
			Queue<Process> temp = new LinkedList<Process>();
			qs.add(temp);			// adding the queue to the priority queue
		}
		
		// setting the avgTAT and avgWT to 0 
		avgTAT = 0;
		avgWT = 0;
    }
    
	/* 
		Purpose: run the feedback constant algorithm
		Pre-condition: an instance of FB exists
		Post-condition:	the feedback constant algorithm is executed
	*/
    public void Run()
    {
        System.out.println("\nFB (constant):");
        while(notDone == true)      // while there is jobs to be done the while loop will be excuting 
        {
            if (currentJob == null)
            {
                isReady();  			// adding jobs which are ready to the top priority queue
                finished();     		// check if the everthing is finished
                if (notDone == false)   // this 'if' statement is executed when all the jobs are done. 
                {
                    break;
                }
                
                time += disp;    // adding the dispatcher time to the time variable
                // Searching through the priority queues to see what job is ready based on priority.
                for (int j = 0; j < 6; j++)
                {
                    if (qs.get(j).peek() != null)   // if the current queue is not empty
                    {
                        currentJob = qs.get(j).remove();    // the job is remove from the queue and set as the currentJob
                        break; 
                    }
                }
            }
            else 
                {
                    if (currentJob.getBurstTime() > execution)      // burst time is greater than execution time allowed
                    {
                        System.out.println("T" + time + ": p" + currentJob.getID());
                        time += execution; 										// adding the execution time allowed to teme variable
                        currentJob.changeBurstTime(execution); 					// decreasing the burst time of the current job by the execution time 
                        currentJob.changeP(); 									// decreasing the priority of the queue by 1 
                        qs.get(currentJob.getPriority()).add(currentJob);		// adding the current job to next lower priority queue
                        currentJob = null;										// setting the current job to null
                    }
                        else    // the burst time of a process is less than or equal to execution time allowed
                        {
                            System.out.println("T" + time + ": p" + currentJob.getID());
                            time += currentJob.getBurstTime(); 				// adding the remaining time of current job to the time variable
                            currentJob.setTATWT(time);			// call a method to set the Turnaround and waiting time of the current job (input for the function is the time variable)
                            completedQ.add(currentJob);			// adding the current job to the completed queue
                            currentJob = null; 					// setting the current job to null 
                        }
                    finished();			// check to see if everthing is done
                }
        }
        getTable();			// priting the turn around and waiting time for each job
        setAverages(); 		// setting the average turn around and waiting time for jobs 
    }
        
    /* 
		Purpose: to check if there is any jobs ready for processing
		Pre-condition: an instance of RR exists
		Post-condition:	if there are jobs at the head of the queue that are ready for processing then it is added to the top priority queue
	*/
    private void isReady()
    {
		// checking if the waiting size has jobs in it
        if (waitingQ.size() > 0)
        {
			// checking if the job at the front of the queue has an arrival time that is less than or equal to the time variable
            while (waitingQ.peek().getArrivalTime() <= time)
            {
                waitingQ.peek().setPriority(0); 		// setting the priority of the job to 0 (highest priority)
                qs.get(0).add(waitingQ.remove());		// adding the job at the front of the queue to the top priority queue
                if (waitingQ.size() == 0)           
                {
					// the waiting queue is empty (no jobs are remaining) then the while loop is broken
                    break; 
                }
            }
        }
    }
    
    
	/* 
		Purpose: to check if there is any jobs remaining in waiting queue or the priority queues
		Pre-condition: an instance of FB exists 
		Post-condition: the value of 'notDone' is set depending on whether there are any jobs remaining
	*/	
    private void finished()
    {
        boolean done = true;	// a local boolean variable to check if the priority queues are empty
        for (int j = 0; j < 6; j++)
            {
                if (qs.get(j).peek() != null)   // check to see if the priority queue is empty 
                    {
                        done = false;	// if not empty then the value of 'done' is set to false and the 'for' loop is broken
                        break; 
                }
            }
		// checking to see if there is any jobs remaining 
        if (currentJob == null && waitingQ.size() == 0 && done == true)
        {
            notDone = false;	// setting the value of 'notDone' to false
        }
    }
    
	/* 
		Purpose: To print the waiting time and turn around time for each job based on order id
		Pre-condition: an instance of RR exists
		Post-condition:	the waiting time and turn around time for each job based on order id is outputted/printed 
	*/
    private void getTable()
    {
        System.out.println("\nProcess\tTurnaround Time\tWaiting Time");
        for (int z = 1; z < completedQ.size() + 1; z++)
        {
            for (int y = 0; y < completedQ.size(); y++)
            {
                if (z == completedQ.get(y).getID())
                {
					// the waiting time and turn around time for the job is printed
                    System.out.println("p" + completedQ.get(y).getID() + "\t" + completedQ.get(y).getTAT() + "\t\t" + completedQ.get(y).getWT()); 
                }
            }
        }
    }
    
	/* 
		Purpose: to set the average turn around time and waiting time for the alogrithm
		Pre-condition: an instance of RR exists 
		Post-condition: setting the average turn around time and waiting time for the alogrithm
	*/
    private void setAverages()
    {
        for (int i = 0;  i < completedQ.size(); i++)
        {
			// adding the waiting time and turn around time for the job is added to the avgTAT and avgWT
            avgTAT += completedQ.get(i).getTAT(); 
            avgWT += completedQ.get(i).getWT();
        }
		// dividing the avgTAT and avgWT by the number of jobs processed by the algorithm
        avgTAT /= completedQ.size();
        avgWT /= completedQ.size();
    }
    
	/*
     * Purpose: return the average turn around time for jobs in the algorithm
     * Pre-Condition: an instance of FCFS exists 
     * Post-Condition: the average TurnaroundTime is returned when the function is called. 
     */
    public double getAvgTAT()
    {
        return avgTAT; 
    }
    
	/*
     * Purpose: return the average waiting time for jobs in the algorithm
     * Pre-Condition: an instance of FCFS exists
     * Post-Condition: the average turn waiting is returned when the function is called
     */
    public double getAvgWT()
    {
        return avgWT; 
    }
}
