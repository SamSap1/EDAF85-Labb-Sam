package wash.control;

import static wash.control.WashingMessage.Order.ACKNOWLEDGMENT;

import actor.ActorThread;
import wash.io.WashingIO;




public class TemperatureController extends ActorThread<WashingMessage> {
    //private final double margin;
    private final WashingIO io;
    private boolean msgFlag;
    private boolean tempCheck;
    //private WashingMessage wm;
    private WashingMessage.Order currentMessage;
    private WashingMessage oldMessage;

    // TODO: add attributes

    public TemperatureController(WashingIO io) {
        // TODO

        this.io = io;
        

    }

    @Override
    public void run() {
        try {
            while (true){
                WashingMessage newMessage = receiveWithTimeout(500 / Settings.SPEEDUP);

                if (newMessage != null) {
                    if (currentMessage != null && currentMessage != newMessage.order()) {
                        msgFlag = true;

                    }
                    oldMessage = newMessage;
                    currentMessage = newMessage.order();

                }

                if (currentMessage != null){

                        switch (currentMessage){

                           case TEMP_IDLE:
                            io.heat(false);
                            oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                            break;

                            case TEMP_SET_40:
                            if (io.getTemperature() < 38 + 0.0952){
                                tempCheck = false;
                                io.heat(true);
                                
                                    oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                                 
                            } else if (io.getTemperature ()  >= 40 -0.478 && !tempCheck){
                                io.heat(false);
                                tempCheck = true;
                                if (msgFlag){
                                    oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                                    msgFlag = false;
                                }
                            }
                            break;

                            case TEMP_SET_60:
                            if (io.getTemperature() < 58 + 0.0952){
                                io.heat(true);
                               tempCheck =  false;
                                    oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                                
                            } else if (io.getTemperature ()  >= 60 -0.478 && !tempCheck){
                                io.heat(false);
                                tempCheck = true;
                                if (msgFlag){
                                    oldMessage.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                                    msgFlag = false;
                                }                            }
                            break;

                            default:
                            break;

                        }
                        

                }

            }
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (Exception e) {
            throw new Error("sumting wong in watercontroller:" + e.getMessage());
        }

    }
}
