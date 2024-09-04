import clock.io.ClockOutput;

public class ClockThread extends Thread{
    private final ClockMonitor mon;
    private final ClockOutput output;


    private ClockThread(ClockMonitor monitor, ClockOutput output){
        this.mon = monitor;
        this.output = output;
    }



    @Override
    public void run(){
        try {
            while (true){
                Thread.sleep(1000);

                mon.incrementTime();

                output.displayTime(mon.getCurrentHours(), mon.getCurrentMinutes(), mon.getAlarmSeconds());



                if (mon.alarmTrigger()){
                    

                }

            }



        } catch (Exception e) {
            e.printStackTrace();

        }



    }



}
