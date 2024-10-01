
import lift.LiftView;

public class LiftMain {
    public static void main(String[] args) throws InterruptedException {

        final int NBR_FLOORS = 7, MAX_PASSENGERS = 4;

        LiftView  view = new LiftView(NBR_FLOORS, MAX_PASSENGERS);
        LiftMonitor mon = new LiftMonitor(NBR_FLOORS, MAX_PASSENGERS, view);

       

        for (int i = 0; i < 20; i++){
            PassengerThread pt = new PassengerThread(mon, view);
            pt.start();
        }
        LiftThread lt = new LiftThread(view, mon);
        lt.start();

}
}
