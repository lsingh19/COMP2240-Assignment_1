/*
 * Purpose: This file contains all the data about a process
 */
public class Process
{
    private int processID; 				// a variable to store the id of a process 
    private int arrivalTime; 			// a variable to store the arrival time of a proces
    private int execSize; 				// a variable to store the initial execution time for the process 
    private int turnAroundTime;			// a variable to store the turn around time for the process in an algorithm	
    private int waitingTime;			// a variable to store the waiting time for the process 
    private int prioirty ;				// a variable that store the prioirty of the job in the FB algorithm 
    
    private int burstTime; 				// a variable to store the execution time of the process in an algorithm (mutated in algorithm)
    private int qt;						// a variable to store the execution time allowed for the process in NRR algorithm
	
	/* 
		Purpose: a constructor for Process
		Pre-Condition: valid input are provided 
		Post-Condition: an instance of process is created. The arrivalTime and execSize is set to the input parameters 
	*/
    public Process(int id, int arriveTime, int executionSize)
    {
        processID = id;						// setting the processID variable to the id input 
        arrivalTime = arriveTime; 			// setting the arrivalTime variable to the arriveTime input
        execSize = executionSize;			// setting the execSize variable to the executionSize input
        burstTime = executionSize; 			// setting the burst time variable to the executionSize input
        qt = 0;								// setting the qt variable to 0
		turnAroundTime = 0;					// setting the turnAroundTime variable to 0
		waitingTime = 0; 					// setting the waitingTime variable to 0
    }
    
	/* 
		Purpose: to change the burst time variable
		Pre-Condition: an instance of Process exists and valid input is provided
		Post-Condition: the burst time variable is decreased by the input parameter 
	*/
    public void changeBurstTime(int time)
    {
        burstTime -= time; 
    }
    
	/* 
		Purpose: return the burst time of the process 
		Pre-Condition: an instance of Process exists
		Post-Condition: returns the burst time of the process
	*/
    public int getBurstTime()
    {
        return burstTime; 
    }
    
	/* 
		Purpose: return the id of the process
		Pre-Condition: an instance of Process exists
		Post-Condition: the ide of the job is returned
	*/
    public int getID()
    {
        return processID; 
    }
    
	/* 
		Purpose: return the Arrival time of the job 
		Pre-Condition: an instance of Process exists
		Post-Condition: the arrival time of the process is returned
	*/
    public int getArrivalTime()
    {
        return arrivalTime; 
    }
    
	/* 
		Purpose: to set the turn around time and waiting time of the process in an algorithm 
		Pre-Condition: an instance of Process exists and valid input is provided
		Post-Condition: the turn around time and waiting time of the process in an algorithm is set
	*/
    public void setTATWT(int completiontime)
    {
        turnAroundTime = completiontime - arrivalTime;
        waitingTime = turnAroundTime - execSize; 
    }
    
	/* 
		Purpose: return the turn around time
		Pre-Condition: an instance of Process exists
		Post-Condition: the turn around time is returned
	*/
    public int getTAT()
    {
        return turnAroundTime; 
    }
    
	/* 
		Purpose: return the waiting time of the process
		Pre-Condition: an instance of Process exists
		Post-Condition: the waiting time of the process is returned
	*/
    public int getWT()
    {
        return waitingTime; 
    }
    
	// ------------------------------------------------------------------------------------------
	//				Methods used by the FB Algorithm
	// ------------------------------------------------------------------------------------------
	/* 
		Purpose: sets the prioirty of the process
		Pre-Condition: an instance of Process exists and valid input is provided 
		Post-Condition: the prioirty variable is set to the input parameter
	*/
    public void setPriority(int x) 
    {
        prioirty = x; 
    }
    
	/* 
		Purpose: returns the prioirty of the process in the FB algorithm
		Pre-Condition: an instance of Process exists
		Post-Condition: the prioirty of the process in the FB algorithm is returned
	*/
    public int getPriority()
    {
        return prioirty; 
    }
    
	/* 
		Purpose: To change the prioirty of the job in the FB algorithm by 1
		Pre-Condition: an instance of Process exists
		Post-Condition: the prioirty of the job in the FB algorithm is increased by 1
	*/
    public void changeP()
    {
        prioirty++;
		// if the prioirty is above 5 (the lowest prioirty) then it is set to 5
        if (prioirty > 5)	
        {
            prioirty = 5; 
        }
    }
    
	// ------------------------------------------------------------------------------------------
	// 					Methods used by the NRR Algorithm 
	// ------------------------------------------------------------------------------------------
	
	/* 
		Purpose: sets the execution time allowed for the process in the NRR algorithm
		Pre-Condition: an instance of Process exists and a valid input is provided
		Post-Condition: the execution time allowed for the process in the NRR algorithm is set to the input parameter 
	*/
    public void setQT(int x)
    {
        qt = x;
    }
    
	/* 
		Purpose: return the execution time allowed for the process in the NRR algorithm
		Pre-Condition: an instance of Process exists
		Post-Condition: the execution time allowed for the process in the NRR algorithm is returned
	*/
    public int getQT()
    {
        return qt;
    }
    
	/* 
		Purpose: decrease the execution time allowed for the process in the NRR algorithm by 1 
		Pre-Condition: an instance of Process exists
		Post-Condition: the execution time allowed for the process in the NRR algorithm is decreased by 1
	*/
    public void decreaseQT()
    {
        qt--;
		// if the allowed execution time allowed for the job is lower than 2 (the lowest execution time allowed) then it is set to 2
        if (qt < 2)
        {
            qt = 2; 
        }
    }
    
	// ------------------------------------------------------------------------------------------
	//		An method to reset the variables inside process to the set values in the constructor 
	// ------------------------------------------------------------------------------------------
	/* 
		Purpose: to reset the variables inside process to the set values in the constructor
		Pre-Condition: an instance of Process exists
		Post-Condition: the variables inside process are set to values set in the constructor
	*/
    public void reset()
    {
        burstTime = execSize;
        turnAroundTime = 0;
        waitingTime = 0;
    }
}