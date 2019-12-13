/*
 * File Name: NRR.java
 * Purpose: This file implements the 'Narrow Round Robin' algorithm taught in lectures
 */
import java.util.LinkedList; 
import java.util.Queue; 
import java.util.ArrayList;     // allows the use of arraylist for the program. 

public class NRR
{
    private Queue<Process> readyQ = new LinkedList();						// a queue to store all the jobs that are ready for processing
    private Queue<Process> waitingQ = new LinkedList();						// a queue to store all the jobs that are NOT ready for processing
    private ArrayList<Process> completedQ = new ArrayList<Process>();		// an Array List to store all the jobs that are completed
    private Process currentJob;												// a Process variable to store the current job being processed 
    private int disp; 														// a variable to store the time taken for the dispatcher to get a job 
    private int time;														// a variable to keep track of the time passed
    private double avgTAT; 													// a variable to store the average turn around time
    private double avgWT;													// a variable to store the average waiting time
    
	/* 
		Purpose: Constructor for the narrow round robin class
		Pre-condition: valid inputs are provided 
		Post-condition: an instance of NRR is created. The list of jobs are added to the waiting queue and the dispatcher time value is set to the disp variable
	*/
    public NRR(ArrayList<Process> jobs, int Dispatcher)
    {
        disp = Dispatcher;						// setting the 'disp' class member variable to the input dispatcher time 'Dispatcher'
        int totaljobs = jobs.size();			// a local variable the number of jobs that need to processed
        for (int z = 0; z < totaljobs; z++)
        {
            waitingQ.add(jobs.get(z));		// adding the job to the waiting queue
        }
        time = 0;				// setting the time variable to 0 
        currentJob = null; 		// setting the current job to null
		// setting the avgTAT and avgWT to 0. 
        avgTAT = 0;
        avgWT = 0; 
    }
    
	/* 
		Purpose: run the narrow round robin algorithm
		Pre-condition: an instance of NRR exists
		Post-condition:	the narrow round robin algorithm is executed
	*/
    public void Run()
    {
        System.out.println("\nNRR:");
		// the following while loop will keep execting until there are jobs to be done by the algorithm
        while (waitingQ.peek() != null || readyQ.peek() != null || currentJob != null)
        {
            if (currentJob == null)  	// check to see if there is any job being processed
            {
                isReady(); 						// checking if there is any job ready for processing 
                time += disp; 					// adding the dispatcher time to the time variable
                if (readyQ.peek() != null)
                {
					// if the ready queue is not empty then the job at the front of the queue is removed. the removed job is assigned to the currentJob variable
                    currentJob = readyQ.remove();
                }
            }
            else 
            {
                // check to see if the current job is the last job if so it will run until it's done
                if (waitingQ.peek() == null && readyQ.peek() == null)
                {
                    System.out.println("T" + time + ": p" + currentJob.getID());
                    time += currentJob.getBurstTime();			// adding the remaining time to the time variable
                    currentJob.setTATWT(time);					// calling a function in the process class to set turn around time and waiting time for the current process
                    completedQ.add(currentJob);					// adding the job to the completed jobs queue
                    currentJob = null; 							// seting the currentJob variable to null
                }
				
                // burst time is greater than the quantum time allowed for the job
                else if (currentJob.getBurstTime() > currentJob.getQT())
                {
                    System.out.println("T" + time + ": p" + currentJob.getID());
                    time += currentJob.getQT();								// adding the execution time allowed for the job to the time variable
                    currentJob.changeBurstTime(currentJob.getQT());			// decreasing the burst time of the job by the execution time allowed for the job
                    currentJob.decreaseQT();								// decreasing the execution time allowed for the job by one  
                    isReady();												// checking to see if there is any jobs ready for processing
                    readyQ.add(currentJob);									// adding the job back onto the ready queue
                    currentJob = null;										// setting the currentJob to null
                }
                else    // the burst time is smaller than the quantum time allowed meaning that it almost done
                {
                    System.out.println("T" + time + ": p" + currentJob.getID());
                    time += currentJob.getBurstTime();		// adding the reamining time of the current job to the time variable
                    currentJob.setTATWT(time);				// calling a function in the process class to set turn around time and waiting time for the current process
                    completedQ.add(currentJob);				// adding the current job to the completed queue
                    currentJob = null; 						// setting the current job to null
                }
            }
        }
        getTable();			// printing the waiting and turn around time for each job 
        setAverages(); 		// setting the avgTAT and avgWT for the algorithm
    }
    
	/* 
		Purpose: to check if there is any jobs ready for processing
		Pre-condition: an instance of RR exists
		Post-condition:	if there are jobs at the head of the queue that are ready for processing then it is added to the ready queue
	*/
    private void isReady()
    {
		// checking if there is any jobs in the waiting queue
        if (waitingQ.peek() != null)
        {
            int z = waitingQ.peek().getArrivalTime(); 		// a local variable to store the arrival time of the process at the front of the queue.
			// check if the arrival time of the job at the front of the queue is less than or equal to the time variable
            while (z <= time)
            {
                waitingQ.peek().setQT(4);			// setting the execution time of the job to 4
                readyQ.add(waitingQ.remove());		// removing the job from the waiting queue and adding it to the ready queue
                // check to see if the waiting queue still has jobs inside it  
				if (waitingQ.peek() == null)
                {
                    break; 	// if not then the while loop is broken 
                }
                else 
                {
					// if the waiting queue is not empty then the local variable 'z' is assigned the arrival time of the next job in the waiting queue
                    z = waitingQ.peek().getArrivalTime();
                }
            }
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
