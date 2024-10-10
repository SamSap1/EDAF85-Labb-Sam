package wash.control;

import static wash.control.WashingMessage.Order.ACKNOWLEDGMENT;

import actor.ActorThread;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

   
    private WashingIO io;
   
    private boolean msgFlag;
    private ActorThread<WashingMessage> send;
    private WashingMessage.Order currentMessage;
    private WashingMessage oldMessage;

    public WaterController(WashingIO io) {
        
        this.io = io;

    }
    @Override
    public void run() {
        
        try {
            while (true) {
                WashingMessage newMessage = receiveWithTimeout(500 / Settings.SPEEDUP);


                //Koden funkar inte annars. Särskiljer nya/gamla meddelanden.
                if (newMessage != null) {

                    if (currentMessage != newMessage.order()) {
                        msgFlag = true;

                    }
                    oldMessage = newMessage;
                    currentMessage = newMessage.order();

                }
                if (currentMessage != null) {

                    switch (currentMessage) {
                        case WATER_IDLE:
                            io.fill(false);
                            io.drain(false);
                           // System.out.println("IDLE CHECK");
                           oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));

                            break;

                        case WATER_DRAIN:
                       
    
                            if (io.getWaterLevel() > 0) {
                                io.fill(false);
                                io.drain(true);
                                //drainedComplete = false;
                    
                            } else {
                              //  io.drain(false);
                               
                                if(msgFlag){
                                    //System.out.println("Drain check1");
                                oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                                msgFlag = false;
                            }
                            }
                            break;
                        
                        case WATER_FILL:
                    

                            if (io.getWaterLevel() < 10) {
                                io.drain(false);

                                io.fill(true);
                            } else {
                                io.fill(false);

                                if (msgFlag){

                                    oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                                    //System.out.println("DO A ");

                                    msgFlag = false;
                                }

                            }

                            break;

                        default:
                            break;
                    }

                }

            }

        }

        catch (InterruptedException e) {
            System.out.println("WaterController");
        }

        catch (Exception e) {
            throw new Error("sumting wong in watercontroller:" + e.getMessage());
        }

    }




}
