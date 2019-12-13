/*
 * Purpose: This file implements the 'Round Robin' algorithm taught in lectures
 */
import java.util.LinkedList; 
import java.util.Queue; 
import java.util.ArrayList;     // allows the use of arraylist for the program. 
public class RR
{
    private Queue<Process> readyQ = new LinkedList(); 					// a queue to store all the jobs that are ready to be processsed
    private Queue<Process> waitingQ = new LinkedList();					// a queue to srore all the jobs that are need time to be ready for execution
    private ArrayList<Process> completedQ = new ArrayList<Process>(); 	// an Array List to store all the completed jobs. 
    private Process currentJob; 
    
    private int timeQ = 4; 		// a variable to store the execution time
    private int time = 0;		// a variable to keep track of the time
    private int disp;			// a variable to store the taken for the dispatcher to get a job
    
    private double avgTAT; 		// a variable to store the average turn around time
    private double avgWT;		// a variable to store the average waiting time
	
	/* 
		Purpose: Constructor for the round robin class
		Pre-condition: valid inputs are provided 
		Post-condition: an instance of RR is created. The lis tof jobs are added to the waiting queue and the dispatcher time value is set to the disp variable
	*/
    public RR(ArrayList<Process> jobs, int dispatcher)
    {
        int x = jobs.size(); 
        for (int i=0; i < x; i++)
        {
            waitingQ.add(jobs.get(i));
        }
        disp = dispatcher; 
        currentJob = null;
        avgTAT = 0; 
        avgWT = 0;
    }

	/* 
		Purpose: run the round robin algorithm
		Pre-condition: an instance of RR exists
		Post-condition:	the round robin algorithm is executed
	*/
    public void Run()
    {
        System.out.println("\nRR:");
		// the following while loop will keep execting until there are jobs to be done by the algorithm
        while (waitingQ.peek() != null || readyQ.peek() != null || currentJob != null)
        {
            if (currentJob == null)
            {
                isReady();  	// check to see if there is any jobs ready for processing 
                time += disp;	// adding the dispatcher time to time variable
                if (readyQ.peek() != null)
                {
					// if there is a job in the ready queue then it is removed and set as the current job.
                    currentJob = readyQ.remove(); 
                }
            }
            else 
            {
				// if the current job is the last remaining job then it is allowed to run until it finishes 
                if (readyQ.size() == 0 && waitingQ.size() == 0) 
                {
                    System.out.println("T" + time + ": p" + currentJob.getID());
                    time += currentJob.getBurstTime(); 			// adding the remaining burst time of the process to the time variable
                    currentJob.setTATWT(time); 					// calling a function in the process class to set turn around time and waiting time for the current process
                    completedQ.add(currentJob);					// the current job is added to the completed queue
                    currentJob = null; 							// the current job is set to null 
                }
                // the current will not be completed after the execution time allowed for a process
                else if (currentJob.getBurstTime() > timeQ)
                {
                    System.out.println("T" + time + ": p" + currentJob.getID());
                    time += timeQ; 									// adding time quantum to the time variable 
                    currentJob.changeBurstTime(timeQ);				// decreasing the burst time by the time quantum 
                    isReady();										// checking if any jobs are ready for execution
                    readyQ.add(currentJob);							// the current job is added back onto the ready queue
                    currentJob = null; 								// setting thec current job to null 
                }
				// the current job will be completed within the time quantum allowed
                else 
                {
                    System.out.println("T" + time + ": p" + currentJob.getID());
                    time += currentJob.getBurstTime(); 		// adding the remaining burst time of the process to the time variable
                    isReady(); 								// checking to see if any jobs are ready for processing
                    currentJob.setTATWT(time); 				// calling a function in the process class to set turn around time and waiting time for the current process
                    completedQ.add(currentJob);				// adding the current job to the completed queue
                    currentJob = null; 						// setting the current job to null
                }
            }
        }
        getTable();			// printing the waiting time and turn around time of each process
        setAverages();		// setting average waiting time and turn around time for each process
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
            int z = waitingQ.peek().getArrivalTime();		// a local variable to store the arrival time of the process at the front of the queue. 
            // check if the arrival time of the job at the front of the queue is less than or equal to the time variable
			while (z <= time)
            {
                readyQ.add(waitingQ.remove());		// the job is removed from the waiting queue and added to the ready queue
                if (waitingQ.peek() == null)		// check to see if the waiting queue still has jobs inside it 
                {
                    break; 		// if not then the while loop is broken
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
		// adding the turn around time and waiting of each job to the avgTAT and avgWT variable
        for (int i = 0;  i < completedQ.size(); i++)
        {
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