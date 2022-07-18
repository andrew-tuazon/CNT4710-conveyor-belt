/*
    Name: Andrew Tuazon
    Course: CNT4714 Summer 2021
    Assignment title: Project 1–Multi-threaded programming in Java
    Date: June 6, 2021
    Class: Conveyor
*/

import java.util.concurrent.locks.*;

public class Conveyor{
    
    int conveyorNum;    // conveyor number

    //create lock on the conveyor instance - no fairness policy needed
    private Lock theLock = new ReentrantLock();

    //constructor method - give the conveyor its number
    public Conveyor(int conveyorNum){
        this.conveyorNum = conveyorNum;
    }
    
    //returns true if the lock was free and thus acquired by the invocation (req) - otherwise
    //returns false.
    public boolean getLock(){
        //if get-the-lock
        if(theLock.tryLock())
        {
            return true;
        }
        //otherwise return false
        return false;
    }// end getLock() method

    public void unlockConveyor(){
        //unlock the lock
        theLock.unlock();
    }// end unlockConveyor method
}   //end Conveyor class
