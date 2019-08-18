
/**
 * The following file will contain details about a process that are to be executed 
 */
public class Process
{
    // instance variables - replace the example below with your own
    private int processID; 
    private int arrivalTime; 
    private int execSize; 
    private int turnAroundTime = 0;
    private int waitingTime = 0;

    /**
     * Constructor for objects of class Process
     */
    public Process(int ID, int arriveTime, int executionSize)
    {
        processID = ID;
        arrivalTime = arriveTime; 
        execSize = executionSize;
    }
    
    public int getID()
    {
        return processID; 
    }
    
    public int getExecSize()
    {
        return execSize; 
    }
    
    public void setTATWT(int completiontime)
    {
        turnAroundTime = completiontime - arrivalTime;
        waitingTime = turnAroundTime - execSize; 
    }
    
    public int getTAT()
    {
        return turnAroundTime; 
    }
    
    public int getWT()
    {
        return waitingTime; 
    }
}
