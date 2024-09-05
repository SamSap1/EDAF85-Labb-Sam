import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import clock.io.ClockOutput;


public class ClockMonitor {

    private int currentHours;
private int currentMinutes;
private int currentSeconds;
private int alarmHours;
private int alarmMinutes;
private int alarmSeconds;
private ClockOutput output;
private boolean alarmOn;


private int counter;
private final Semaphore sem = new Semaphore(1);


public ClockMonitor (ClockOutput output) throws InterruptedException{

    sem.acquire();
    this.output = output;
    sem.release();

}


public void setCurrentTime(int hours, int mins, int secs) throws InterruptedException{
    sem.acquire();
    this.currentHours = hours;
    this.currentMinutes = mins;
    this.currentSeconds = secs;
    sem.release();


    
}


//Should have a semaphore
public void incrementTime() throws InterruptedException{
  
    sem.acquire();

        currentSeconds++;
            if (currentSeconds >= 60){
                currentSeconds = 0;
                currentMinutes++;
                if (currentMinutes >= 60){
                    currentMinutes = 0;
                    currentHours++;
                }
                if (currentHours >= 24){
                        currentHours = 0;
                }
            }

            sem.release();
            
            

}
public void setAlarmTime(int hours, int mins, int secs) throws InterruptedException{

    sem.acquire();
    this.alarmHours = hours;
    this.alarmMinutes = mins;
    this.alarmSeconds = secs;
  //  out.setAlarmIndicator(true);


    sem.release();
}


public void toggleAlarm() throws InterruptedException{
    sem.acquire();
    this.alarmOn = !this.alarmOn;
    sem.release();
} 

public boolean isAlarmOn() throws InterruptedException{
    sem.acquire();
    boolean alarmOnHolder = alarmOn;
    
    sem.release();
    return alarmOnHolder;
}

//Should have a semaphore
public void alarmTrigger() throws InterruptedException{
   
        
    sem.acquire();
        if (alarmOn){
        
            if (currentHours == alarmHours && currentMinutes == alarmMinutes && currentSeconds == alarmSeconds){
                counter = 0;

                }
            if (counter < 20){
                output.alarm();
                counter++;
            }
        
        }

        sem.release();

}

public int getCurrentHours() throws InterruptedException{
    sem.acquire();

    int hourHolder = currentHours;
    sem.release();
    return hourHolder;


    }
    public int getCurrentMinutes() throws InterruptedException{
        
        sem.acquire();
        int minHolder = currentMinutes;
        sem.release();
        return minHolder;

        
    }public int getCurrentSeconds() throws InterruptedException{
        sem.acquire();
        int secsHolder = currentSeconds;
        sem.release();
        return secsHolder;
        
    }

public int getAlarmHours()throws InterruptedException{ 

    sem.acquire();
    int alHourHolder = alarmHours;
    sem.release();
    return alHourHolder;

}


public int getAlarmMinutes()throws InterruptedException{
    sem.acquire();
    int alMinHolder = alarmMinutes;
    sem.release();
    return alMinHolder;
    

}

public int getAlarmSeconds()throws InterruptedException{

    sem.acquire();
        int alSecHolder = alarmSeconds;
        sem.release();
        return alSecHolder;
        
   
}

}
