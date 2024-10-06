package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import static wash.control.WashingMessage.Order.*;

import javax.sound.midi.Receiver;

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
            // Lock the hatch
            io.lock(true);
            // Instruct SpinController to rotate barrel slowly, back and forth
            // Expect an acknowledgment in response.
            spin.send(new WashingMessage(this, WATER_FILL));
            receive();

            spin.send(new WashingMessage(this, TEMP_SET_40));
            receive();

            spin.send(new WashingMessage(this, SPIN_SLOW));
            receive();

            spin.send(new WashingMessage(this, WATER_DRAIN));
            receive();

            for (int i = 0; i < 5; i++) 
            {
                
            }

            // Spin for five simulated minutes (one minute == 60000 milliseconds)
            Thread.sleep(30 * 60000 / Settings.SPEEDUP);
            // Instruct SpinController to stop spin barrel spin.
            // Expect an acknowledgment in response.
            spin.send(new WashingMessage(this, SPIN_OFF));
            receive();

            // Now that the barrel has stopped, it is safe to open the hatch.
            io.lock(false);
        } catch (InterruptedException e)
        {

            // If we end up here, it means the program was interrupt()'ed:
            // set all controllers to idle

            temp.send(new WashingMessage(this, TEMP_IDLE));
            water.send(new WashingMessage(this, WATER_IDLE));
            spin.send(new WashingMessage(this, SPIN_OFF));
            System.out.println("washing program terminated");
        }
    }
}
