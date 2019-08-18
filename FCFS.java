
/**
 * This file contains the first come first serve algorithm
 */
import java.util.LinkedList; 
import java.util.Queue; 

public class FCFS
{
    // instance variables - replace the example below with your own
    String output = "FCFS:\n"; 
    Queue<Process> jobsWaiting = new LinkedList<>();
    int DISP = 0; 
    int CPU = 0; 
    int numOfJobs;
    double averageTurnaroundTime = 0; 
    double averageWaitingTime = 0; 
    
    
    public FCFS(Process [] jobs, int dispatcher)
    {
        numOfJobs = jobs.length; 
        for (int i = 0; i < numOfJobs; i++)
        {
            jobsWaiting.add(jobs[i]);
        }
        DISP = dispatcher; 
    }
    
    public String Run()
    {
        int tasks = numOfJobs; 
        Process currentJob = null;      // the current job that is being processed
        String table = "\nProcess\tTurnaround Time\tWaitingTime\n";
        while (tasks > 0)
        {
            if (currentJob == null)
            {
                CPU += DISP; 
                currentJob = jobsWaiting.remove();
            }
            else 
            {
                output += "T" + CPU + ": p" + currentJob.getID() + "\n";
                CPU += currentJob.getExecSize();
                currentJob.setTATWT(CPU);
                table += "p" + currentJob.getID() + "\t" + currentJob.getTAT() + "\t\t" + currentJob.getWT() + "\n";
                averageTurnaroundTime += currentJob.getTAT();
                averageWaitingTime += currentJob.getWT(); 
                currentJob = null;
                tasks--; 
            }
        }
        output += table; 
        return output; 
    }
    
    public double getAvgTAT()
    {
        return averageTurnaroundTime/numOfJobs; 
    }
    
    public double getAvgWT()
    {
        return averageWaitingTime/numOfJobs; 
    }
}
