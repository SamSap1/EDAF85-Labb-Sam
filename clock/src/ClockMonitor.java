import java.util.concurrent.Semaphore;
import clock.io.ClockOutput;

public class ClockMonitor
{
    private int currentHours;
    private int currentMinutes;
    private int currentSeconds;
    private int alarmHours;
    private int alarmMinutes;
    private int alarmSeconds;
    private int counter;
    private ClockOutput output;
    private boolean alarmOn;

    private final Semaphore sem = new Semaphore(1);

    public ClockMonitor(ClockOutput output) throws InterruptedException
    {
        sem.acquire();
        this.output = output;
        sem.release();
    }


    public void setCurrentTime(int hours, int mins, int secs) throws InterruptedException
    {
        sem.acquire();
        this.currentHours = hours;
        this.currentMinutes = mins;
        this.currentSeconds = secs;
        sem.release();
    }

    public void incrementTime() throws InterruptedException
    {
        sem.acquire();
        currentSeconds++;
        if (currentSeconds >= 60)
        {
            currentSeconds = 0;
            currentMinutes++;
            if (currentMinutes >= 60)
            {
                currentMinutes = 0;
                currentHours++;
            }
            if (currentHours >= 24)
            {
                currentHours = 0;
            }
        }
        sem.release();
    }

    public void setAlarmTime(int hours, int mins, int secs) throws InterruptedException
    {
        sem.acquire();
        this.alarmHours = hours;
        this.alarmMinutes = mins;
        this.alarmSeconds = secs;
        sem.release();
    }

    public void toggleAlarm() throws InterruptedException
    {
        sem.acquire();
        try {
            this.alarmOn = !this.alarmOn;
            output.setAlarmIndicator(alarmOn);
        } finally {
            sem.release();
        }
    }

    public boolean isAlarmOn() throws InterruptedException
    {
        sem.acquire();
        try {
            return alarmOn;
        } finally
        {
            sem.release();
        }
    }
    public void alarmTrigger() throws InterruptedException
    {
        sem.acquire();
        if (alarmOn)
        {
            if (currentHours == alarmHours && currentMinutes == alarmMinutes && currentSeconds == alarmSeconds)
            {
                counter = 1;
            }
            if (counter > 0 && counter <= 20)
            {
                output.alarm();
                counter++;
            }
            else if (counter > 20)
            {
                sem.release();
                toggleAlarm();
                sem.acquire();
                counter = 0;
            }
        }
        else
        {
            counter = 0;
        }
        sem.release();
    }

    public CurrentTime getCurrTime() throws InterruptedException
    {
        sem.acquire();
        try
        {
            return new CurrentTime(currentHours, currentMinutes, currentSeconds);
        } finally
        {
            sem.release();
        }
    }

    public int getAlarmHours() throws InterruptedException
    {
        sem.acquire();
        try
        {
            return alarmHours;
        }finally
        {
            sem.release();
        }
    }

    public int getAlarmMinutes() throws InterruptedException
    {
        sem.acquire();
        try
        {
            return alarmMinutes;
        }finally
        {
            sem.release();
        }
    }

    public int getAlarmSeconds() throws InterruptedException
    {
        sem.acquire();
        try
        {
            return alarmSeconds;
        }finally
        {
            sem.release();
        }
    }


    
    public class CurrentTime
    {
        private final int currH;
        private final int currM;
        private final int currS;

        public CurrentTime(int hours, int minutes, int seconds)
        {
            this.currH = hours;
            this.currM = minutes;
            this.currS = seconds;
        }

        public int getCurrHours()
        {
            return currH;
        }

        public int getCurrMinutes()
        {
            return currM;
        }

        public int getCurrSeconds()
        {
            return currS;
        }
    }
}