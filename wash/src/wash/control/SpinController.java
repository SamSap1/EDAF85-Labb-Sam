package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.io.WashingIO.Spin;

public class SpinController extends ActorThread<WashingMessage>
{
    private WashingIO io;
    private boolean spinLeft = false;
    private boolean canSpin = true;

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
                WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);

                if (m == null)
                {
                    if (canSpin)
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
                }
                else
                {
                    switch (m.order())
                    {
                        case SPIN_SLOW:
                            canSpin = true;
                            io.setSpinMode(Spin.LEFT);
                            send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
                            break;
                    
                        case SPIN_FAST:
                            canSpin = true;
                            io.setSpinMode(Spin.FAST);
                            send(new WashingMessage(this, WashingMessage.Order.ACKNOWLEDGMENT));
                            break;

                        case SPIN_OFF:
                            canSpin = false;
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