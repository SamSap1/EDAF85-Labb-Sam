package wash.control;
import actor.ActorThread;
import wash.io.WashingIO;
import static wash.control.WashingMessage.Order.*;

public class WashingProgram1 extends ActorThread<WashingMessage>
{

    private WashingIO io;
    private ActorThread<WashingMessage> temp;
    private ActorThread<WashingMessage> water;
    private ActorThread<WashingMessage> spin;

    public WashingProgram1(WashingIO io, ActorThread<WashingMessage> temp, ActorThread<WashingMessage> water,
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
            io.lock(true);

            water.send(new WashingMessage(this, WATER_FILL));
            receive();

            temp.send(new WashingMessage(this, TEMP_SET_40));
            receive();

            spin.send(new WashingMessage(this, SPIN_SLOW));
            receive();

            Thread.sleep(30 * 60000 / Settings.SPEEDUP);

            temp.send(new WashingMessage(this, TEMP_IDLE));
            receive();

            spin.send(new WashingMessage(this, SPIN_OFF));
            receive();

            water.send(new WashingMessage(this, WATER_DRAIN));
            receive();

            for (int i = 0; i < 5; i++) 
            {
                water.send(new WashingMessage(this, WATER_FILL));
                receive();

                Thread.sleep(2 * 60000 / Settings.SPEEDUP);

                water.send(new WashingMessage(this, WATER_DRAIN));
                receive();
            }

            spin.send(new WashingMessage(this, SPIN_FAST));
            receive();

            Thread.sleep(5 * 60000 / Settings.SPEEDUP);

            spin.send(new WashingMessage(this, SPIN_OFF));
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
