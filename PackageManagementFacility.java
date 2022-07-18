/*
    Name: Andrew Tuazon
    Course: CNT4714 Summer 2021
    Assignment title: Project 1–Multi-threaded programming in Java
    Date: June 6, 2021
    Class: PackageManagementFacility
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class PackageManagementFacility{

    static int MAX = 10;    //maximum number of routing stations and conveyors that can be configured
    public static void main (String args[]){
        try{
            System.out.println("\n CNT 4714 - Project 1 - Summer 2021 \n");
            System.out.println("\n * * * SIMULATION BEGINS * * * \n");
            //read in config.txt file
            Scanner file = new Scanner(new File("config.txt"));

            //array list to store the integers from config.txt
            ArrayList<Integer> config = new ArrayList<Integer>();

            //create thread pool of MAX size
            ExecutorService application = Executors.newFixedThreadPool(MAX);

            //read file into array - get the simulation scenario for this simulation run
            while(file.hasNext()){
                int number = file.nextInt();
                config.add(number);
            }
            file.close();   //close configuration file
            //save the first integer as number of routing stations
            int numberOfRoutingStations = config.get(0);

            //creates array of conveyors
            Conveyor[] conveyors = new Conveyor[numberOfRoutingStations];

            //fills the array the conveyors
            for(int i = 0; i < numberOfRoutingStations; i++){
                conveyors[i] = new Conveyor(i);

            }
            //creates the routing stations
            for(int i = 0; i < numberOfRoutingStations; i++){
                try {
                    //calls station constructor and starts the RoutingStation threads
                    application.execute(new RoutingStation(i, config.get(i + 1), conveyors[i], conveyors[(i + numberOfRoutingStations - 1) % numberOfRoutingStations]));
                }

                catch(Exception e){
                    e.printStackTrace();
                }
            }

            //application shutdown
            application.shutdown();

            while (!application.isTerminated()){

            } 

            //System.out.println("Finished all threads");
            System.out.println("\n * * * * ALL WORKLOADS COMPLETE * * * SIMULATION ENDS * * * *");
        }
        catch(FileNotFoundException e) {
            System.out.println("file not found");
        }
    }
}