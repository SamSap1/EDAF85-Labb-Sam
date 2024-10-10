package wash.control;
import static wash.control.WashingMessage.Order.ACKNOWLEDGMENT;
import actor.ActorThread;
import wash.io.WashingIO;
import wash.io.WashingIO.Spin;

public class SpinController extends ActorThread<WashingMessage> 
{
    private WashingIO io;
    private boolean spinLeft;
    private boolean spinSlow;
    
    public SpinController(WashingIO io) 
    {
        this.io = io;
        this.spinLeft = false;
        this.spinSlow = false;
    }

    @Override
    public void run() 
    {
        try
        {
            while (true)
            {
                WashingMessage m = receiveWithTimeout(60000 / Settings.SPEEDUP);

                if (m == null && spinSlow)
                {
                    io.setSpinMode(spinLeft ? Spin.LEFT : Spin.RIGHT);
                    spinLeft = !spinLeft;
                }

                if (m != null)
                {
                    switch (m.order())
                    {

                    case SPIN_SLOW:
                        spinSlow = true;
                        io.setSpinMode(Spin.LEFT);
                        m.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                        break;

                    case SPIN_FAST:
                        spinSlow = false;
                        io.setSpinMode(Spin.FAST);
                        m.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                        break;

                    case SPIN_OFF:
                        spinSlow = false;
                        io.setSpinMode(Spin.IDLE);
                        m.sender().send(new WashingMessage(this, ACKNOWLEDGMENT));
                        break;

                    default:
                        break;

                    }
                }
            }
        } catch (InterruptedException unexpected)
        {
            throw new Error(unexpected);
        }
    }
}