import clock.AlarmClockEmulator;
import clock.io.Choice;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

public class ClockMain {
    public static void main(String[] args) throws InterruptedException {
        AlarmClockEmulator emulator = new AlarmClockEmulator();
        ClockMonitor mon = new ClockMonitor();
        ClockInput  in  = emulator.getInput();
        ClockOutput out = emulator.getOutput();
        ClockThread t1 = new ClockThread(mon, out);

        out.displayTime(15, 2, 37);   // arbitrary time: just an example

        t1.start();

        while (true) {
            UserInput userInput = in.getUserInput();
            Choice c = userInput.choice();
            int h = userInput.hours();
            int m = userInput.minutes();
            int s = userInput.seconds();



            switch (c) {
                case SET_TIME:
                    mon.setCurrentTime(h, m, s);    

                    break;
            
                   case SET_ALARM:
                   mon.setAlarmTime(h, m, s);

                    break;

                    case TOGGLE_ALARM:
                     mon.toggleAlarm();

                    break;

                default:
                    break;
            }



            System.out.println("choice=" + c + " h=" + h + " m=" + m + " s=" + s);
        }
    }
}
