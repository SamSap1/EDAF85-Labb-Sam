package wash.control;

import static wash.control.WashingMessage.Order.ACKNOWLEDGMENT;

import actor.ActorThread;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    private WashingIO io;
    private double inputFlow = 0.1;
    private double outputFlow = 0.2;
    private ActorThread<WashingMessage> send;
    private double WaterLevel;

    




    public WaterController(WashingIO io) {
        // TODO
        this.io = io;


    }

    @Override
    public void run() {
        // TODO
        try {
            while (true){
                WashingMessage wm = receiveWithTimeout(60000 / Settings.SPEEDUP);

                    wm.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));

                    if (wm != null){

                        



                    }
                


            }

            
        } catch (Exception e) {
            // TODO: handle exception
        }


    }
}
