/*
    Name: Andrew Tuazon
    Course: CNT4714 Summer 2021
    Assignment title: Project 1–Multi-threaded programming in Java
    Date: June 6, 2021
    Class: RoutingStation
*/

import java.util.Random;

public class RoutingStation implements Runnable{
    protected static int MAXSLEEP = 500;

    protected Random sleeptime = new Random();
    protected int stationNum;
    protected int workload;
    protected Conveyor inconveyor;
    protected Conveyor outconveyor;
    protected boolean bothLocks = false;
    protected int workLoadCounter;
    
    public RoutingStation(int stationNum, int workload, Conveyor inconveyor, Conveyor outconveyor){
        this.stationNum = stationNum;
        this.workload = workload;
        this.inconveyor = inconveyor;
        this.outconveyor = outconveyor;
        workLoadCounter = workload;
    }

    //method for routing stations thread to go to sleep - simulates random time behavior
    public void goToSleep(){
        try{
            Thread.sleep(sleeptime.nextInt(MAXSLEEP - 1 + 1) + 1);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void doWork(){
        //print out messages that station is moving packages in a package group in and out of the station
        System.out.println("Routing Station " + stationNum + ": ...Active... moving packages into station on input conveyor C" + inconveyor.conveyorNum + ".");
        System.out.println("Routing Station " + stationNum + ": ...Active... moving packages out of station on input conveyor C" + outconveyor.conveyorNum + ".");

        //decrement the station's workload
        workload--;

        //print message about workload remaining
        System.out.println("Routing Station " + stationNum + ": Number of package groups left to move is: " + workload + ". \n");
        //sleep for random time to simulate holding the conveyor locks and moving packages
        goToSleep();
    }

    public void run(){

        //print out messages for input and output conveyor assignments
        System.out.println("Routing Station " + stationNum + ": Input connection is set to conveyor number C" + stationNum + ".");
        System.out.println("Routing Station " + stationNum + ": Output connection is set to conveyor number C" + outconveyor.conveyorNum  + ".");
        //print out message showing station's initial workload
        System.out.println("Routing Station " + stationNum + ": Workload set. Station " + stationNum + " has a total of " + workload + " package groups to move.");

        //loop that runs for routing stations workload (workLoadCounter)
        for(int i = 0; i < workLoadCounter; i++)
        {
            //initially set variable bothLock to indicate station holding both needed conveyor locks to false - it doesn't hold any yet
            //loop as long as both locks not acquired
            while(!bothLocks){
                //try to get input lock - must get input lock first to prevent deadlock
                if(inconveyor.getLock()){
                    System.out.println("Routing Station " + stationNum + ": LOCK ACQUIRED! Now holding lock on input conveyor C" + stationNum + ".");
                    //try to get output lock
                    if(outconveyor.getLock()){
                        System.out.println("Routing Station " + stationNum + ": LOCK ACQUIRED! Now holding lock on output conveyor C" + outconveyor.conveyorNum + ".");
                        //doWork()
                        doWork();
                        //release both locks
                        inconveyor.unlockConveyor();
                        outconveyor.unlockConveyor();
                        //set bothLocks false
                        bothLocks = false;
                        //print message about lock release
                        System.out.println("Routing Station " + stationNum + ": Unlocks input conveyor C" + stationNum + ".");
                        System.out.println("Routing Station " + stationNum + ": Unlocks output conveyor C" + outconveyor.conveyorNum  + ".");
                        //if workload is 0, then set station to idle
                        if(workload == 0){
                            System.out.println("\n* * Station " + stationNum + ": Workload successfully completed. * * Station Going Idle!\n");
                            bothLocks = true;
                        }
                    }
                    //unlock input lock if output lock unavailable
                    else{
                        System.out.println("Routing Station " + stationNum + ": Unable  to  lock  output  conveyor C" + outconveyor.conveyorNum + " – releasing  lock  on input conveyor C" + stationNum + ".");
                        inconveyor.unlockConveyor();
                        goToSleep();
                    }
                    goToSleep();
                }
            }
        }
    }   //end run() method
}//end RoutingStation class