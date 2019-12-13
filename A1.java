/*
 * Comp2240 - Assignment 1 
 * File name: A1.java 
 * Purpose: This assignement is simulates scheduling algorithms taught in the lectures 
 * This program simulates the following algorithms
 *  	- First Come First Serve (FCFS)
 *  	- Round Robin (RR)
 *  	- Feedback Constant FB (FB)
 *  	- Narrow Round Robin (NRR)
 */

import java.util.*; 
import java.io.*; 
import java.nio.*; 
import java.io.BufferedReader; 
import java.io.FileReader;   
import java.io.IOException;  
import java.util.ArrayList;  

public class A1
{
    public static void main (String args [])
    {
        BufferedReader br = null; 
        int DISP = 0; 
        int ID = 0;
        int arrivalTime = 0; 
        int execSize = 0;
        ArrayList<Process> jobslist = new ArrayList<Process>();     // implemented as an array list as we don't know how many process we want to complete. 

        try 
        {
			br = new BufferedReader(new FileReader("."+File.separator+args[0]));		// reading the file
            String line = br.readLine(); 
            while (!line.equals("EOF"))
            {
                if (!line.equals(""))
                {
                    String x = line.substring(0,2);
                    if (x.equals("DI"))
                    {
                        DISP = Integer.parseInt(line.substring(6));		// setting dispatcher time to the dispatcher variable
                    }
                    
                    if (x.equals("ID"))
                    {
                        ID = Integer.parseInt(line.substring(5));		// setting the 'id' of the process to the 'id' variable
					}
                    
                    if (x.equals("Ar"))
                    {
                        arrivalTime = Integer.parseInt(line.substring(8));		// setting the 'arrival time' of the process to the 'arrivalTime' variable		
                    }
                    
                    if (x.equals("Ex"))
                    {
                        int leng = line.length();
                        execSize = Integer.parseInt(line.substring(10,leng));		// setting the 'service time' of the process to the 'execSize' variable
                        Process p = new Process(ID,arrivalTime, execSize);			// creating a Process variable to be added to the jobs list
                        jobslist.add(p); 											// adding the process to jobslist 
                    }
                    
                }
                line = br.readLine();
            }
        } catch (IOException e)
            {
                e.printStackTrace();    // prints exception details
            } 
                finally 
                {
                    try 
                    {
                        br.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();    // prints exception details
                    }
                }
		
		
		// -----------------------------------------------------
		// 				First Come First Serve
		// -----------------------------------------------------
        FCFS alogrithmOne = new FCFS(jobslist, DISP); 
        alogrithmOne.Run();
        resetProcess(jobslist);
		
		// -----------------------------------------------------
		// 					Round Robin
		// -----------------------------------------------------
        RR alogrithmTwo = new RR(jobslist, DISP);
        alogrithmTwo.Run();
        resetProcess(jobslist);
		
		// -----------------------------------------------------
		// 				Feedback (constant)
		// -----------------------------------------------------
        FB alogrithmThree = new FB(jobslist, DISP); 
        alogrithmThree.Run(); 
        resetProcess(jobslist);
		
		// -----------------------------------------------------
		// 				Narrow Round Robin
		// -----------------------------------------------------
        NRR alogrithmFour = new NRR(jobslist, DISP); 
        alogrithmFour.Run(); 
        resetProcess(jobslist);
        
		// -----------------------------------------------------
		// Printing average turn around and waiting time 
		
        System.out.println("\nSummary\nAlgorithm\tAverage Turnaround Time\tAverage Waiting Time");
        System.out.println("FCFS\t\t" + String.format("%.2f\t \t \t",alogrithmOne.getAvgTAT()) + String.format("%.2f",alogrithmOne.getAvgWT()));
        System.out.println("RR\t\t" + String.format("%.2f\t \t \t",alogrithmTwo.getAvgTAT()) + String.format("%.2f",alogrithmTwo.getAvgWT()));
        System.out.println("FB (constant)\t" + String.format("%.2f\t \t \t",alogrithmThree.getAvgTAT()) + String.format("%.2f",alogrithmThree.getAvgWT()));
        System.out.println("NRR\t\t" + String.format("%.2f\t \t \t",alogrithmFour.getAvgTAT()) + String.format("%.2f",alogrithmFour.getAvgWT()));
    }
    
    // a method to reset the process details to the values set in the constructor
    private static void resetProcess(ArrayList<Process> jobslist)
    {
        int x = jobslist.size();				// a local integer variable to store the amount of process in the jobslist arraylist
        for (int z =0; z < x; z++)
        {
            jobslist.get(z).reset();		// call a reset method inside the instance of the process to reset values to defualt values
        }
    }
}
