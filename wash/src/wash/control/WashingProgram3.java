package wash.control;
import actor.ActorThread;
import wash.io.WashingIO;
import static wash.control.WashingMessage.Order.*;

public class WashingProgram3 extends ActorThread<WashingMessage>
{

    private WashingIO io;
    private ActorThread<WashingMessage> temp;
    private ActorThread<WashingMessage> water;
    private ActorThread<WashingMessage> spin;

    public WashingProgram3(WashingIO io, ActorThread<WashingMessage> temp, ActorThread<WashingMessage> water,
            ActorThread<WashingMessage> spin)
    {
        this.io = io;
        this.temp = temp;
        this.water = water;
        this.spin = spin;
    }

    @Override
    public void run()
    {
        try
        {
            temp.send(new WashingMessage(this, TEMP_IDLE));
            receive();

            spin.send(new WashingMessage(this, SPIN_OFF));
            receive();

            water.send(new WashingMessage(this, WATER_DRAIN));
            receive();
            
            water.send(new WashingMessage(this, WATER_IDLE));

            io.lock(false);
        } catch (InterruptedException e)
        {
            temp.send(new WashingMessage(this, TEMP_IDLE));
            water.send(new WashingMessage(this, WATER_IDLE));
            spin.send(new WashingMessage(this, SPIN_OFF));
            System.out.println("washing program terminated");
        }
    }
}
