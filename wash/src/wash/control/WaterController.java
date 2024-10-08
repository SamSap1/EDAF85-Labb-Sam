package wash.control;

import static wash.control.WashingMessage.Order.ACKNOWLEDGMENT;

import actor.ActorThread;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    private WashingIO io;
    private boolean filledCapacity;
    private boolean drainedComplete;
    private boolean msgFlag;
    private ActorThread<WashingMessage> send;
    private double WaterLevel;
    private WashingMessage.Order currentMessage;
    private WashingMessage oldMessage;

    public WaterController(WashingIO io) {
        // TODO
        this.io = io;

    }

    @Override
    public void run() {
        // TODO
        try {
            while (true) {
                WashingMessage newMessage = receiveWithTimeout(500 / Settings.SPEEDUP);

                if (newMessage != null) {
                    if (currentMessage != null && currentMessage != newMessage.order()) {
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
                            //System.out.println("IDLE CHECK");
                            oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));

                            break;

                        case WATER_DRAIN:
                            System.out.println("DRAIN CHECK");

                            //double waterLevel = io.getWaterLevel();

                            io.fill(false);
                    
                            if (io.getWaterLevel() > 0 && drainedComplete != true) {
                                io.drain(true);
                                drainedComplete = false;
                    
                            } else {
                                io.drain(false);
                                drainedComplete = true;
                                if(msgFlag){
                                oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                                msgFlag = false;
                            }
                            }
                            break;

                        case WATER_FILL:
                            System.out.println("FILL CHECK");

                           // double waterLevel = io.getWaterLevel();

                            io.drain(false);

                            if (io.getWaterLevel() < 10 && filledCapacity!=true) {
                                io.fill(true);
                                filledCapacity = false;
                            } else {
                                io.fill(false);
                                filledCapacity = true;

                                if (msgFlag){
                                    oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
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
