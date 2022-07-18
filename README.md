# CNT4710-conveyor-belt

Objective: To  practice  programming  an  application  with  multiple  threads  of  execution  and 
synchronizing their access to necessary shared objects.

Your program must initially read from a text file (config.txt) to gather configuration information 
for the simulator.  The first line of the text file will be the number of routing stations to use during 
the simulation.  Afterwards, there will be one line for each station.  These lines will hold the amount 
of work each station needs to process (i.e, the number of times it needs to move packages down the 
conveyor system).  Only use integers in your configuration file, decimals will not be needed.  You 
can assume that the maximum number of stations will be 10.
