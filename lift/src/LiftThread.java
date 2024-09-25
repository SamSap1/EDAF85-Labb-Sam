import lift.LiftView;

public class LiftThread extends Thread{
    private LiftMonitor monitor;
    private LiftView lv;
    private int currentFloor;
    private int nextFloor;

    public LiftThread(LiftView view, LiftMonitor monitor){
        this.lv = view;
        this.monitor = monitor;
    }


    @Override 
    public void run(){
        try {
                while (true){
                    if (!monitor.canMove()){
                        monitor.closeDoors();
                    }

                   nextFloor =  monitor.moveLift();
                    lv.moveLift(currentFloor, nextFloor);
                    currentFloor = nextFloor;

                        
                }

        } catch (Exception e) {
            
        }

    }


    
}
