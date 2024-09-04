import clock.io.ClockOutput;

public class ClockThread extends Thread{
    private final ClockMonitor mon;
    private final ClockOutput output;
    private long t; 
    private long diff;

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
                diff = t - System.currentTimeMillis();

                if (diff > 0){
                    Thread.sleep(diff);

                }
                else {
                    continue;
                }

                mon.incrementTime();

                output.displayTime(mon.getCurrentHours(), mon.getCurrentMinutes(), mon.getAlarmSeconds());




                // THIS IS WRONG
                    if (mon.alarmTrigger()){
                        for(int i = 0; i < 20; i++){
                            output.alarm();
                        }
                      

                    }   

            }

        } catch (Exception e) {
            e.printStackTrace();

        }



    }



}
