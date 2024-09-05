import clock.io.ClockOutput;

public class ClockThread extends Thread{
    private final ClockMonitor mon;
    private final ClockOutput output;
    private long t; 
    private long diff;
    private int counter = 20;

    public ClockThread(ClockMonitor monitor, ClockOutput output){
        this.mon = monitor;
        this.output = output;
        t = System.currentTimeMillis();
    }

    @Override
    public void run(){
        try {
            while (true){
                t+= 1000; 

                mon.incrementTime();

                output.displayTime(mon.getCurrentHours(), mon.getCurrentMinutes(), mon.getCurrentSeconds());

                // THIS IS WRONG

                    mon.alarmTrigger();
                      

                        diff = t - System.currentTimeMillis();

                        if (diff > 0){
                            Thread.sleep(diff);
        
                        }

                    }   

            

        } catch (Exception e) {
            e.printStackTrace();

        }



    }



}
