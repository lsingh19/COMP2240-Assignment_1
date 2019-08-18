        
        /**
         * Comp2240 - Assignment 1 
         * File name: A1.java 
         * Purpose: This assignement is simulates scheduling algorithms taught in the lectures 
         * This program simulates the following algorithms
         *  - First Come First Serve (FCFS)
         *  - Round Robin (RR)
         *  - Feedback Constant FB (FB)
         *  - Narrow Round Robin (NRR)
         */
        
        import java.io.BufferedReader; // to read data
        import java.io.FileReader;     // read the file
        import java.io.IOException;    // to handle execptions
    
    public class A1
    {
        public static void main (String args [])
        {
            BufferedReader br = null; 
            int DISP = 0; 
            int ID = 0;
            int arrivalTime = 1; 
            int execSize = 0;
            Process jobslist[] = new Process[5];    // declaring a array to contain the josb that need to be done
            int z = 0; 
            try 
            {
                br = new BufferedReader(new FileReader("datafile2.txt"));
                String line = br.readLine(); 
                while (!line.equals("EOF"))
                {
                    if (!line.equals(""))
                    {
                        String x = line.substring(0,2);
                        // System.out.println(x);
                        if (x.equals("DI"))
                        {
                            DISP = Integer.parseInt(line.substring(6));
                            System.out.println(DISP);
                            System.out.println("----------");
                        }
                        
                        if (x.equals("ID"))
                        {
                            ID = Integer.parseInt(line.substring(5));
                            System.out.println(ID);
                        }
                        
                        if (x.equals("Ar"))
                        {
                            arrivalTime = Integer.parseInt(line.substring(8));
                            System.out.println(arrivalTime);
                        }
                        
                        if (x.equals("Ex"))
                        {
                            int leng = line.length();
                            execSize = Integer.parseInt(line.substring(10,leng));
                            System.out.println(execSize);
                            System.out.println("--------------------");
                            Process add = new Process(ID,arrivalTime, execSize);
                            jobslist[z] = add; 
                            System.out.println("DONE with " + z);
                            z++;
                        }
                        
                    }
                    line = br.readLine();
                }
            } catch (IOException e)
            {
                e.printStackTrace();    // prints exception details
            } finally 
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
           
               double [] summaryTimes = new double[8];
               int counter = 0; 
               String result; 
               
               FCFS alogrithmOne = new FCFS(jobslist, DISP); 
               result = alogrithmOne.Run();
               summaryTimes[counter] = alogrithmOne.getAvgTAT();
               counter++;
               summaryTimes[counter] = alogrithmOne.getAvgWT();
               counter++; 
               
               RR alogrithmTwo = new RR(jobslist, DISP);
               alogrithmTwo.Run();
               
               result += summaryTimes[0] + " " + summaryTimes[1];
               System.out.println(result);
           
    }
}
