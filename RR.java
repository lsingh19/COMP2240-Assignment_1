
/**
 * Write a description of class RR here.
 */
import java.util.LinkedList; 
import java.util.Queue; 
public class RR
{
    Queue<Process> readyQ = new LinkedList(); 
    Queue<Process> waitingQ = new LinkedList();
    Queue<Process> completedQ = new LinkedList(); 
    
    int [] wt;
    int [] tat;
    
    int timeQ = 4; 
    int CPU = 0;
    int DISP;
    /**
     * Constructor for objects of class RR
     */
    public RR(Process [] jobs, int dispatcher)
    {
        // initialise instance variables
        int x = jobs.length; 
        wt = new int [x];
        tat = new int [x];
        for (int i=0; i < x; i++)
        {
            waitingQ.add(jobs[i]);
        }
        DISP = dispatcher; 
    }

    public String Run()
    {
        Process currentJob = null;
        String table = "\nProcess\tTurnaround Time\tWaitingTime\n";
            while (waitingQ.peek() != null || readyQ.peek() != null || currentJob != null)
            {
                if (currentJob == null)
                {
                    CPU += DISP; 
                    checkWaiting(); 
                    currentJob = readyQ.remove(); 
                    if (readyQ == null)
                    {
                        System.out.println("empty");
                    }
                }
                else 
                {
                    if (currentJob.getBurstTime() < timeQ)
                    {
                        System.out.println("T" + CPU + ": p" + currentJob.getID());
                        int p = currentJob.getBurstTime();
                        currentJob.changeBurstTime(p);
                        CPU += p;
                        completedQ.add(currentJob); 
                        checkWaiting();
                        currentJob.setTATWT(CPU);
                        inputWTTAT(currentJob);
                        currentJob = null; 
                    }
                    else 
                    {
                        if (waitingQ.peek() == null && readyQ.isEmpty() == true)
                        {
                            System.out.println("T" + CPU + ": p" + currentJob.getID());
                            int p = currentJob.getBurstTime();
                            currentJob.changeBurstTime(p);
                            CPU += p;
                            completedQ.add(currentJob);
                            currentJob.setTATWT(CPU);
                            inputWTTAT(currentJob);
                            checkWaiting();
                            currentJob = null; 
                        }
                        else
                        {
                            System.out.println("T" + CPU + ": p" + currentJob.getID());
                            currentJob.changeBurstTime(timeQ);
                            CPU += timeQ; 
                            checkWaiting();
                            if (currentJob.getBurstTime() == 0)
                            {
                                completedQ.add(currentJob);
                                currentJob.setTATWT(CPU);
                                inputWTTAT(currentJob);
                                currentJob = null;
                            }
                            else 
                            {
                                readyQ.add(currentJob);
                                currentJob = null; 
                            }
                        }
                    }
                }
            }
            table = getTableData(table);
            System.out.println(table);
        return "Hi"; 
    }
    
    public void checkWaiting()
    {
        if (waitingQ.peek() != null)
        {
            int z = waitingQ.peek().getArrivalTime();
            while (z < CPU)
            {
                readyQ.add(waitingQ.remove());
                if (waitingQ.peek() == null)
                {
                    z = CPU + 1;
                }
                else 
                {
                    z = waitingQ.peek().getArrivalTime();
                }
            }
        }
    }
    
    public void inputWTTAT(Process pID)
    {
        int location = pID.getID() - 1; 
        wt[location] = pID.getWT();
        tat[location] = pID.getTAT();
    }
    
    public String getTableData(String table)
    {
        int size = wt.length;
        for (int i = 0; i < size; i++)
        {
           table += "p" + (i+1) + "\t" + tat[i] + "\t\t" + wt[i] + "\n"; 
        }
        return table; 
    }
    
}
