package wash.control;

import static wash.control.WashingMessage.Order.ACKNOWLEDGMENT;
import static wash.control.WashingMessage.Order.SPIN_FAST;
import static wash.control.WashingMessage.Order.SPIN_OFF;

import org.junit.jupiter.api.Order;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.io.WashingIO.Spin;

public class SpinController extends ActorThread<WashingMessage> {

    // TODO: add attributes
    private WashingIO io;
    private boolean spinDirection;
    private boolean isInSlow;
    
    public SpinController(WashingIO io) {
        this.io = io;
        this.spinDirection = false;
        this.isInSlow = false;


    }

    @Override
    public void run() {

        // this is to demonstrate how to control the barrel spin:
        // io.setSpinMode(Spin.IDLE);
        
        try {
            // ... TODO ...
            while (true) {
                // wait for up to a (simulated) minute for a WashingMessage
                WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);
                // if m is null, it means a minute passed and no message was received
                if (m != null) {
                    System.out.println("got " + m);
                    switch(m.order()){

                        case SPIN_SLOW:
                        isInSlow = true;
                            io.setSpinMode( spinDirection ? Spin.LEFT : Spin.RIGHT);
                            m.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                        break;

                        case SPIN_FAST:
                        isInSlow = false;
                            io.setSpinMode(Spin.FAST);
                            m.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                        break;

                        case SPIN_OFF:
                        isInSlow = false;
                            io.setSpinMode(Spin.IDLE);
                            m.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                        break; 

                        default:
                            break;

                    } 
    
                } if(m == null && isInSlow){
                    spinDirection = !spinDirection;
                    io.setSpinMode(spinDirection ? Spin.LEFT : Spin.RIGHT);
                }
                
            }
        } catch (InterruptedException unexpected) {
            // we don't expect this thread to be interrupted,
            // so throw an error if it happens anyway
            throw new Error(unexpected);
        }
    }
}