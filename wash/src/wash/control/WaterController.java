package wash.control;

import static wash.control.WashingMessage.Order.ACKNOWLEDGMENT;

import actor.ActorThread;
import wash.io.WashingIO;

public class WaterController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    private WashingIO io;
    private double inputFlow = 0.1;
    private double outputFlow = 0.2;
    private boolean filledCapacity;
    private boolean drainedComplete;
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
                WashingMessage wm = receiveWithTimeout(500 / Settings.SPEEDUP);

                    if (wm != null){


                        switch (wm.order()) {
                            case WATER_IDLE:
                                    io.fill(false);
                                    io.drain(false);
                            System.out.println("IDLE CHECK");
                            wm.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));

                                break;

                             case WATER_DRAIN:
                             System.out.println("DRAIN CHECK");

                                drainCheck();
                                wm.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));

                                break;

                              case WATER_FILL:
                              System.out.println("FILL CHECK");

                              waterFillCheck();
                              wm.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));

                              break;      
               
                        
                            default:
                                break;
                        }
    
                    }

                    
                


            }


            
        }
        
        catch (InterruptedException e){
            System.out.println("WaterController");
        }
        
        catch (Exception e) {
            throw new Error("sumting wong in watercontroller:" + e.getMessage());
            // TODO: handle exception
        }



    }
    private void waterFillCheck(){
        double waterLevel = io.getWaterLevel();

        io.drain(false);


        if (waterLevel < 10){
            io.fill(true);
            filledCapacity = false;
        } else{
            io.fill(false);
            filledCapacity = true;

        }


    }

    private void drainCheck(){
        double waterLevel = io.getWaterLevel();

        io.fill(false);

        if (waterLevel > 0){
            io.drain(true);
           drainedComplete = false;

        }    else{
            io.drain(false);
            drainedComplete = true;

        }


    }



}
