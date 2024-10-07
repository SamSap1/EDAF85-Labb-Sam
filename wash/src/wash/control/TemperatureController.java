package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;




public class TemperatureController extends ActorThread<WashingMessage> {
    private final double margin;
    private final WashingIO io;
    private WashingMessage wm;


    // TODO: add attributes

    public TemperatureController(WashingIO io) {
        // TODO
        margin = 0.2;
        this.io = io;
        

    }

    @Override
    public void run() {
        try {
            while (true){
                WashingMessage wm = receiveWithTimeout(60000 / Settings.SPEEDUP);

                


            }
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

    }
}
