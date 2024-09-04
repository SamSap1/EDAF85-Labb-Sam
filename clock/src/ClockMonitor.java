import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ClockMonitor {

    private int currentHours;
private int currentMinutes;
private int currentSeconds;
private int alarmHours;
private int alarmMinutes;
private int alarmSeconds;
private boolean alarmOn;
private final Semaphore sem = new Semaphore(1);



public void setCurrentTime(int hours, int mins, int secs) throws InterruptedException{
    

        sem.acquire();
        this.currentHours = hours;
        this.currentMinutes = mins;
        this.currentSeconds = secs;
        sem.release();
}

public void incrementTime() throws InterruptedException{
  
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
            
            

}
public void setAlarmTime(int hours, int mins, int secs) throws InterruptedException{

    sem.acquire();
    this.alarmHours = hours;
    this.alarmMinutes = mins;
    this.alarmSeconds = secs;

    sem.release();
}

public void toggleAlarm() throws InterruptedException{
    sem.acquire();
    this.alarmOn = !this.alarmOn;
    sem.release();
} 

public boolean isAlarmOn(){
    return alarmOn;
}

public boolean alarmTrigger(){
    if (alarmOn && currentHours == alarmHours && currentMinutes == alarmMinutes && currentSeconds == alarmSeconds){
        return true;

    }
    return false;
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
