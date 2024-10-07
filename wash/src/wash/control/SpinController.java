package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.io.WashingIO.Spin;

public class SpinController extends ActorThread<WashingMessage>
{
    WashingIO io;
    boolean spinLeft = false;

    public SpinController(WashingIO io)
    {
        this.io = io;
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                // wait for up to a (simulated) minute for a WashingMessage
                WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);
                // if m is null, it means a minute passed and no message was received

                if (m == null)
                {
                    if (spinLeft)
                    {
                        io.setSpinMode(Spin.LEFT);
                    }
                    else
                    {
                        io.setSpinMode(Spin.RIGHT);
                    }
                    spinLeft = !spinLeft;
                }
                else
                {
                    switch (m.order())
                    {
                        case SPIN_SLOW:
                            io.setSpinMode(Spin.LEFT);
                            send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
                            break;
                    
                        case SPIN_FAST:
                            io.setSpinMode(Spin.FAST);
                            send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
                            break;

                        case SPIN_OFF:
                            io.setSpinMode(Spin.IDLE);
                            send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
                            break;

                        default:
                            break;
                    }
                }
            }
        } catch (InterruptedException unexpected)
        {
            // we don't expect this thread to be interrupted,
            // so throw an error if it happens anyway
            throw new Error(unexpected);
        }
    }
}