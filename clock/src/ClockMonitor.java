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

public void incrementTime(){
  
  while (true){
 

   
        currentSeconds++;
            if (currentSeconds >= 60){
                this.currentSeconds = 0;
                currentMinutes++;
                if (currentMinutes >= 60){
                    this.currentMinutes = 0;
                    currentHours++;
                }
                if (currentHours >= 24){
                        currentHours = 0;
                }
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


public int getCurrentHours(){
    return currentHours;
    
    }
    public int getCurrentMinutes(){
    return currentMinutes;
        
    }public int getCurrentSeconds(){
    return currentSeconds;
        
    }

public int getAlarmHours(){
    return alarmHours;
}


public int getAlarmMinutes(){
    return alarmMinutes;
}

public int getAlarmSeconds(){
    return alarmSeconds;
}




}
